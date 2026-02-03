package org.tanukis.tanuki.explore.ui.model

import org.tanukis.tanuki.core.model.MangaSourceInfo
import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.parsers.util.longHashCode

data class MangaSourceItem(
	val source: MangaSourceInfo,
	val isGrid: Boolean,
) : ListModel {

	val id: Long = source.name.longHashCode()

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is MangaSourceItem && other.source == source
	}
}
