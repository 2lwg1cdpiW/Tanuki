package org.tanukis.tanuki.bookmarks.ui.adapter

import android.content.Context
import org.tanukis.tanuki.bookmarks.domain.Bookmark
import org.tanukis.tanuki.core.ui.BaseListAdapter
import org.tanukis.tanuki.core.ui.list.OnListItemClickListener
import org.tanukis.tanuki.core.ui.list.fastscroll.FastScroller
import org.tanukis.tanuki.list.ui.adapter.ListHeaderClickListener
import org.tanukis.tanuki.list.ui.adapter.ListItemType
import org.tanukis.tanuki.list.ui.adapter.emptyStateListAD
import org.tanukis.tanuki.list.ui.adapter.errorStateListAD
import org.tanukis.tanuki.list.ui.adapter.listHeaderAD
import org.tanukis.tanuki.list.ui.adapter.loadingFooterAD
import org.tanukis.tanuki.list.ui.adapter.loadingStateAD
import org.tanukis.tanuki.list.ui.model.ListModel

class BookmarksAdapter(
	clickListener: OnListItemClickListener<Bookmark>,
	headerClickListener: ListHeaderClickListener?,
) : BaseListAdapter<ListModel>(), FastScroller.SectionIndexer {

	init {
		addDelegate(ListItemType.PAGE_THUMB, bookmarkLargeAD(clickListener))
		addDelegate(ListItemType.HEADER, listHeaderAD(headerClickListener))
		addDelegate(ListItemType.STATE_ERROR, errorStateListAD(null))
		addDelegate(ListItemType.FOOTER_LOADING, loadingFooterAD())
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(null))
	}

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return findHeader(position)?.getText(context)
	}
}
