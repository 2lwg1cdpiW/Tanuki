package org.tanukis.tanuki.history.ui

import android.content.Context
import org.tanukis.tanuki.core.ui.list.fastscroll.FastScroller
import org.tanukis.tanuki.list.ui.adapter.MangaListAdapter
import org.tanukis.tanuki.list.ui.adapter.MangaListListener
import org.tanukis.tanuki.list.ui.size.ItemSizeResolver

class HistoryListAdapter(
	listener: MangaListListener,
	sizeResolver: ItemSizeResolver,
) : MangaListAdapter(listener, sizeResolver), FastScroller.SectionIndexer {

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return findHeader(position)?.getText(context)
	}
}
