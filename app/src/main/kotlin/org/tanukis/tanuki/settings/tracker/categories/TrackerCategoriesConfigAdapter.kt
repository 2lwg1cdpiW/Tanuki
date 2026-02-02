package org.tanukis.tanuki.settings.tracker.categories

import org.tanukis.tanuki.core.model.FavouriteCategory
import org.tanukis.tanuki.core.ui.BaseListAdapter
import org.tanukis.tanuki.core.ui.list.OnListItemClickListener

class TrackerCategoriesConfigAdapter(
	listener: OnListItemClickListener<FavouriteCategory>,
) : BaseListAdapter<FavouriteCategory>() {

	init {
		delegatesManager.addDelegate(trackerCategoryAD(listener))
	}
}
