package org.tanukis.tanuki.core.ui.model

import org.tanukis.tanuki.parsers.model.ContentRating

data class MangaOverride(
	val coverUrl: String?,
	val title: String?,
	val contentRating: ContentRating?,
)
