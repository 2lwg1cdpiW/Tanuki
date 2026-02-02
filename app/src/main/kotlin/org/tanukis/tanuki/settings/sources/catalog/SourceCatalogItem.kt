package org.tanukis.tanuki.settings.sources.catalog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.parsers.model.MangaParserSource

sealed interface SourceCatalogItem : ListModel {

	data class Source(
		val source: MangaParserSource,
	) : SourceCatalogItem {

		override fun areItemsTheSame(other: ListModel): Boolean {
			return other is Source && other.source == source
		}
	}

	data class Hint(
		@DrawableRes val icon: Int,
		@StringRes val title: Int,
		@StringRes val text: Int,
	) : SourceCatalogItem {

		override fun areItemsTheSame(other: ListModel): Boolean {
			return other is Hint && other.title == title
		}
	}
}
