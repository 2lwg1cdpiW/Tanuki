package org.tanukis.tanuki.history.data

import dagger.Reusable
import org.tanukis.tanuki.core.db.MangaDatabase
import org.tanukis.tanuki.core.db.entity.toManga
import org.tanukis.tanuki.core.db.entity.toMangaTags
import org.tanukis.tanuki.history.domain.model.MangaWithHistory
import org.tanukis.tanuki.list.domain.ListFilterOption
import org.tanukis.tanuki.list.domain.ListSortOrder
import org.tanukis.tanuki.local.data.index.LocalMangaIndex
import org.tanukis.tanuki.local.domain.LocalObserveMapper
import org.tanukis.tanuki.parsers.model.Manga
import javax.inject.Inject

@Reusable
class HistoryLocalObserver @Inject constructor(
	localMangaIndex: LocalMangaIndex,
	private val db: MangaDatabase,
) : LocalObserveMapper<HistoryWithManga, MangaWithHistory>(localMangaIndex) {

	fun observeAll(
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	) = db.getHistoryDao().observeAll(order, filterOptions, limit).mapToLocal()

	override fun toManga(e: HistoryWithManga) = e.manga.toManga(e.tags.toMangaTags(), null)

	override fun toResult(e: HistoryWithManga, manga: Manga) = MangaWithHistory(
		manga = manga,
		history = e.history.toMangaHistory(),
	)
}
