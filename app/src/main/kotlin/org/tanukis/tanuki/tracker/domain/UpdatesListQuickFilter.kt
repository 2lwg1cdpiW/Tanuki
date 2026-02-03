package org.tanukis.tanuki.tracker.domain

import org.tanukis.tanuki.core.prefs.AppSettings
import org.tanukis.tanuki.favourites.domain.FavouritesRepository
import org.tanukis.tanuki.list.domain.ListFilterOption
import org.tanukis.tanuki.list.domain.MangaListQuickFilter
import javax.inject.Inject

class UpdatesListQuickFilter @Inject constructor(
	private val favouritesRepository: FavouritesRepository,
	settings: AppSettings,
) : MangaListQuickFilter(settings) {

	override suspend fun getAvailableFilterOptions(): List<ListFilterOption> =
		favouritesRepository.getMostUpdatedCategories(
			limit = 4,
		).map {
			ListFilterOption.Favorite(it)
		}
}
