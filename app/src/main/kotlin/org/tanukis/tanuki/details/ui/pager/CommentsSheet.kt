package org.tanukis.tanuki.details.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.tanukis.tanuki.core.image.CoilImageView
import org.tanukis.tanuki.core.ui.sheet.BaseAdaptiveSheet
import org.tanukis.tanuki.databinding.SheetCommentsBinding
import org.tanukis.tanuki.details.ui.pager.ChaptersPagesViewModel
import org.tanukis.tanuki.parsers.model.MangaChapter

@AndroidEntryPoint
class CommentsSheet : BaseAdaptiveSheet<SheetCommentsBinding>() {

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): SheetCommentsBinding {
        return SheetCommentsBinding.inflate(inflater, container, false)
    }

    override fun onViewBindingCreated(binding: SheetCommentsBinding, savedInstanceState: Bundle?) {
        super.onViewBindingCreated(binding, savedInstanceState)
        binding.toolbar.title = binding.root.context.getString(org.tanukis.tanuki.R.string.comments)
        val adapter = CommentsAdapter(emptyList())
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        val activityVm = ChaptersPagesViewModel.ActivityVMLazy(this).value
        val chapter = findCurrentChapter(activityVm) ?: run {
            adapter.update(emptyList())
            return
        }

        // fetch comments asynchronously
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) { fetchComments(chapter) }
            adapter.update(list)
        }
    }

    private fun findCurrentChapter(vm: ChaptersPagesViewModel): MangaChapter? {
        val chapId = vm.readingState.value?.chapterId ?: return null
        val manga = vm.manga.value ?: return null
        return manga.allChapters.find { it.id == chapId }
    }

    private data class CommentItem(
        val id: Long,
        val author: String?,
        val content: String,
        val timestamp: Long,
        val avatarUrl: String?,
    )

    private fun fetchComments(chapter: MangaChapter): List<CommentItem> {
        try {
            val client = OkHttpClient()
            val req = Request.Builder().url(chapter.url).get().build()
            val resp = client.newCall(req).execute()
            val body = resp.body?.string().orEmpty()
            if (body.isEmpty()) return emptyList()
            val doc = Jsoup.parse(body)

            // Try to find JSON arrays in scripts
            val scripts = doc.select("script")
            fun findCommentsArray(obj: JSONObject): JSONArray? {
                val keys = obj.keys()
                while (keys.hasNext()) {
                    val k = keys.next()
                    try {
                        val v = obj.get(k)
                        if (k.contains("comment", ignoreCase = true) && v is JSONArray) return v
                        if (v is JSONArray) {
                            if (v.length() > 0 && (v.optJSONObject(0)?.has("content") == true || v.optJSONObject(0)?.has("author") == true)) return v
                        }
                        if (v is JSONObject) {
                            findCommentsArray(v)?.let { return it }
                        }
                    } catch (_: Exception) {
                    }
                }
                return null
            }

            for (script in scripts) {
                val data = script.data()
                val raw = data.substringAfter("self.__next_f.push(", "").substringBefore(")", "").trim()
                val candidates = mutableListOf<String>()
                if (raw.isNotEmpty()) candidates.add(raw)
                val firstBrace = data.indexOf('{')
                val lastBrace = data.lastIndexOf('}')
                if (firstBrace >= 0 && lastBrace > firstBrace) {
                    candidates.add(data.substring(firstBrace, lastBrace + 1))
                }
                for (c in candidates) {
                    val jo = try { JSONObject(c) } catch (_: Exception) { null } ?: continue
                    val ja = findCommentsArray(jo) ?: continue
                    val list = mutableListOf<CommentItem>()
                    for (i in 0 until ja.length()) {
                        val el = ja.optJSONObject(i) ?: continue
                        val id = when {
                            el.has("id") -> el.optLong("id")
                            el.has("comment_id") -> el.optLong("comment_id")
                            else -> generateUid("${chapter.id}:comment:$i")
                        }
                        val author = when {
                            el.has("author") -> el.optString("author")
                            el.has("username") -> el.optString("username")
                            el.has("name") -> el.optString("name")
                            else -> el.optJSONObject("user")?.optString("name")
                        }
                        val content = el.optString("content", el.optString("body", "")).orEmpty()
                        val timestamp = when {
                            el.has("created_at") -> el.optLong("created_at", 0L)
                            el.has("timestamp") -> el.optLong("timestamp", 0L)
                            else -> 0L
                        }
                        val avatar = el.optString("avatar", el.optJSONObject("user")?.optString("avatar"))
                        if (content.isNotEmpty()) list += CommentItem(id = id, author = author, content = content, timestamp = timestamp, avatarUrl = avatar)
                    }
                    if (list.isNotEmpty()) return list
                }
            }

            // Fallback: parse HTML comment blocks
            val htmlComments = doc.select(".comment, .comment-item, .comment-card, .comment-list li")
            if (htmlComments.isNotEmpty()) {
                return htmlComments.mapIndexed { idx, el ->
                    val id = el.attr("data-id").toLongOrNull() ?: generateUid("${chapter.id}:htmlcomment:$idx")
                    val author = el.selectFirst(".author, .username, .name")?.text()
                    val content = el.selectFirst(".content, .comment-body, p")?.html() ?: el.text()
                    val timeAttr = el.selectFirst("time")?.attr("datetime")
                    val timestamp = try { timeAttr?.let { java.time.Instant.parse(it).toEpochMilli() } ?: 0L } catch (_: Exception) { 0L }
                    val avatar = el.selectFirst("img, .avatar")?.attr("src")
                    CommentItem(id = id, author = author, content = content, timestamp = timestamp, avatarUrl = avatar)
                }
            }

            return emptyList()
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private class CommentsAdapter(private var items: List<CommentItem>) : RecyclerView.Adapter<CommentsViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(org.tanukis.tanuki.R.layout.item_comment, parent, false)
            return CommentsViewHolder(v)
        }

        override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        fun update(newItems: List<CommentItem>) {
            items = newItems
            notifyDataSetChanged()
        }
    }

    private class CommentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val avatar = view.findViewById<CoilImageView?>(org.tanukis.tanuki.R.id.comment_avatar)
        private val author = view.findViewById<android.widget.TextView>(org.tanukis.tanuki.R.id.comment_author)
        private val time = view.findViewById<android.widget.TextView>(org.tanukis.tanuki.R.id.comment_time)
        private val tv = view.findViewById<android.widget.TextView>(org.tanukis.tanuki.R.id.comment_text)

        fun bind(item: CommentItem) {
            author.text = item.author ?: ""
            tv.text = item.content
            time.text = if (item.timestamp > 0L) android.text.format.DateUtils.getRelativeTimeSpanString(item.timestamp) else ""
            try {
                avatar?.setImageAsync(item.avatarUrl)
            } catch (_: Exception) {
            }
        }
    }

    private fun generateUid(s: String): Long = s.hashCode().toLong() and 0xffffffffL

    companion object {
        const val KEY_COMMENTS = "comments_list"
    }
}
