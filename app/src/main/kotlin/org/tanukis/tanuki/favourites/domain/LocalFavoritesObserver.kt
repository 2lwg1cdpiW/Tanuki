package org.tanukis.tanuki.favourites.domain

import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import org.tanukis.tanuki.core.db.MangaDatabase
import org.tanukis.tanuki.core.db.entity.toManga
import org.tanukis.tanuki.core.db.entity.toMangaTags
import org.tanukis.tanuki.favourites.data.FavouriteManga
import org.tanukis.tanuki.list.domain.ListFilterOption
import org.tanukis.tanuki.list.domain.ListSortOrder
import org.tanukis.tanuki.local.data.index.LocalMangaIndex
import org.tanukis.tanuki.local.domain.LocalObserveMapper
import org.tanukis.tanuki.parsers.model.Manga
import javax.inject.Inject

@Reusable
class LocalFavoritesObserver @Inject constructor(
	localMangaIndex: LocalMangaIndex,
	private val db: MangaDatabase,
) : LocalObserveMapper<FavouriteManga, Manga>(localMangaIndex) {

	fun observeAll(
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	): Flow<List<Manga>> = db.getFavouritesDao().observeAll(order, filterOptions, limit).mapToLocal()

	fun observeAll(
		categoryId: Long,
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	): Flow<List<Manga>> = db.getFavouritesDao().observeAll(categoryId, order, filterOptions, limit).mapToLocal()

	override fun toManga(e: FavouriteManga) = e.manga.toManga(e.tags.toMangaTags(), null)

	override fun toResult(e: FavouriteManga, manga: Manga) = manga
}
