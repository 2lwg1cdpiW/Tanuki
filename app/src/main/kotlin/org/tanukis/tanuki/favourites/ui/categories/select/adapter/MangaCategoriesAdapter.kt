package org.tanukis.tanuki.favourites.ui.categories.select.adapter

import org.tanukis.tanuki.core.ui.BaseListAdapter
import org.tanukis.tanuki.core.ui.list.OnListItemClickListener
import org.tanukis.tanuki.favourites.ui.categories.select.model.MangaCategoryItem
import org.tanukis.tanuki.list.ui.adapter.ListItemType
import org.tanukis.tanuki.list.ui.adapter.emptyStateListAD
import org.tanukis.tanuki.list.ui.adapter.loadingStateAD
import org.tanukis.tanuki.list.ui.model.ListModel

class MangaCategoriesAdapter(
	clickListener: OnListItemClickListener<MangaCategoryItem>,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.NAV_ITEM, mangaCategoryAD(clickListener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(null))
	}
}
