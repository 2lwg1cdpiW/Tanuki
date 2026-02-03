package org.tanukis.tanuki.history.domain.model

import org.tanukis.tanuki.core.model.MangaHistory
import org.tanukis.tanuki.parsers.model.Manga

data class MangaWithHistory(
	val manga: Manga,
	val history: MangaHistory
)
