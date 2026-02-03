package org.tanukis.tanuki.favourites.ui.categories.adapter

import org.tanukis.tanuki.core.ui.ReorderableListAdapter
import org.tanukis.tanuki.favourites.ui.categories.FavouriteCategoriesListListener
import org.tanukis.tanuki.list.ui.adapter.ListItemType
import org.tanukis.tanuki.list.ui.adapter.ListStateHolderListener
import org.tanukis.tanuki.list.ui.adapter.emptyStateListAD
import org.tanukis.tanuki.list.ui.adapter.loadingStateAD
import org.tanukis.tanuki.list.ui.model.ListModel

class CategoriesAdapter(
	onItemClickListener: FavouriteCategoriesListListener,
	listListener: ListStateHolderListener,
) : ReorderableListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.CATEGORY_LARGE, categoryAD(onItemClickListener))
		addDelegate(ListItemType.NAV_ITEM, allCategoriesAD(onItemClickListener))
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(listListener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
	}
}
