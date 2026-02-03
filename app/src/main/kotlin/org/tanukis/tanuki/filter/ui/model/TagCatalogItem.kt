package org.tanukis.tanuki.filter.ui.model

import org.tanukis.tanuki.list.ui.ListModelDiffCallback
import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.parsers.model.MangaTag

data class TagCatalogItem(
	val tag: MangaTag,
	val isChecked: Boolean,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is TagCatalogItem && other.tag == tag
	}

	override fun getChangePayload(previousState: ListModel): Any? {
		return if (previousState is TagCatalogItem && previousState.isChecked != isChecked) {
			ListModelDiffCallback.PAYLOAD_CHECKED_CHANGED
		} else {
			super.getChangePayload(previousState)
		}
	}
}
