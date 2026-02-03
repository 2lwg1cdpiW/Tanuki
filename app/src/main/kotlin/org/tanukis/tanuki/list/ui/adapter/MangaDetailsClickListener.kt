package org.tanukis.tanuki.list.ui.adapter

import android.view.View
import org.tanukis.tanuki.core.ui.list.OnListItemClickListener
import org.tanukis.tanuki.list.ui.model.MangaListModel
import org.tanukis.tanuki.parsers.model.Manga
import org.tanukis.tanuki.parsers.model.MangaTag

interface MangaDetailsClickListener : OnListItemClickListener<MangaListModel> {

	fun onReadClick(manga: Manga, view: View)

	fun onTagClick(manga: Manga, tag: MangaTag, view: View)
}
