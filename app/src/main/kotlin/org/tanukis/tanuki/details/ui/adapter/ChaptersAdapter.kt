package org.tanukis.tanuki.details.ui.adapter

import android.content.Context
import org.tanukis.tanuki.core.ui.BaseListAdapter
import org.tanukis.tanuki.core.ui.list.OnListItemClickListener
import org.tanukis.tanuki.core.ui.list.fastscroll.FastScroller
import org.tanukis.tanuki.details.ui.model.ChapterListItem
import org.tanukis.tanuki.list.ui.adapter.ListItemType
import org.tanukis.tanuki.list.ui.adapter.listHeaderAD
import org.tanukis.tanuki.list.ui.model.ListHeader
import org.tanukis.tanuki.list.ui.model.ListModel

class ChaptersAdapter(
	onItemClickListener: OnListItemClickListener<ChapterListItem>,
) : BaseListAdapter<ListModel>(), FastScroller.SectionIndexer {

	private var hasVolumes = false

	init {
		addDelegate(ListItemType.HEADER, listHeaderAD(null))
		addDelegate(ListItemType.CHAPTER_LIST, chapterListItemAD(onItemClickListener))
		addDelegate(ListItemType.CHAPTER_GRID, chapterGridItemAD(onItemClickListener))
	}

	override suspend fun emit(value: List<ListModel>?) {
		super.emit(value)
		hasVolumes = value != null && value.any { it is ListHeader }
	}

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return if (hasVolumes) {
			findHeader(position)?.getText(context)
		} else {
			val chapter = (items.getOrNull(position) as? ChapterListItem)?.chapter ?: return null
			chapter.numberString()
		}
	}
}
