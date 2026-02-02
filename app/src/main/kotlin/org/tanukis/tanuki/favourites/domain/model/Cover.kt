package org.tanukis.tanuki.favourites.domain.model

import org.tanukis.tanuki.core.model.MangaSource

data class Cover(
	val url: String?,
	val source: String,
) {
	val mangaSource by lazy { MangaSource(source) }
}
