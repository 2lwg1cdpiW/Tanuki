package org.tanukis.tanuki.list.ui.model

import org.tanukis.tanuki.core.ui.model.MangaOverride
import org.tanukis.tanuki.parsers.model.Manga

data class MangaCompactListModel(
	override val manga: Manga,
	override val override: MangaOverride?,
	val subtitle: String,
	override val counter: Int,
) : MangaListModel()
