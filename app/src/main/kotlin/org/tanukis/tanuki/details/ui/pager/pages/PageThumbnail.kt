package org.tanukis.tanuki.details.ui.pager.pages

import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.reader.ui.pager.ReaderPage

data class PageThumbnail(
	val isCurrent: Boolean,
	val page: ReaderPage,
) : ListModel {

	val number
		get() = page.index + 1

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is PageThumbnail && page == other.page
	}
}
