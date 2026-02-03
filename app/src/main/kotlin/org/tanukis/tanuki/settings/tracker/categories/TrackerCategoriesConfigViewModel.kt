package org.tanukis.tanuki.settings.tracker.categories

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import org.tanukis.tanuki.core.model.FavouriteCategory
import org.tanukis.tanuki.core.ui.BaseViewModel
import org.tanukis.tanuki.favourites.domain.FavouritesRepository
import javax.inject.Inject

@HiltViewModel
class TrackerCategoriesConfigViewModel @Inject constructor(
	private val favouritesRepository: FavouritesRepository,
) : BaseViewModel() {

	val content = favouritesRepository.observeCategories()
		.stateIn(viewModelScope + Dispatchers.Default, SharingStarted.Eagerly, emptyList())

	private var updateJob: Job? = null

	fun toggleItem(category: FavouriteCategory) {
		val prevJob = updateJob
		updateJob = launchJob(Dispatchers.Default) {
			prevJob?.join()
			favouritesRepository.updateCategoryTracking(category.id, !category.isTrackingEnabled)
		}
	}
}
