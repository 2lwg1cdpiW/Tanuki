package org.tanukis.tanuki.search.domain

import org.tanukis.tanuki.parsers.model.Manga
import org.tanukis.tanuki.parsers.model.MangaListFilter
import org.tanukis.tanuki.parsers.model.SortOrder

data class SearchResults(
	val listFilter: MangaListFilter,
	val sortOrder: SortOrder,
	val manga: List<Manga>,
)
