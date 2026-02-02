package org.tanukis.tanuki.favourites.ui.categories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.tanukis.tanuki.core.model.FavouriteCategory
import org.tanukis.tanuki.core.ui.list.OnListItemClickListener

interface FavouriteCategoriesListListener : OnListItemClickListener<FavouriteCategory?> {

	fun onDragHandleTouch(holder: RecyclerView.ViewHolder): Boolean

	fun onEditClick(item: FavouriteCategory, view: View)

	fun onShowAllClick(isChecked: Boolean)
}
