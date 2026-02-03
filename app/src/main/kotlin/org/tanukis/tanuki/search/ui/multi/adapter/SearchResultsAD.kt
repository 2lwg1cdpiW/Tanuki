package org.tanukis.tanuki.search.ui.multi.adapter

import android.annotation.SuppressLint
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.tanukis.tanuki.R
import org.tanukis.tanuki.core.model.UnknownMangaSource
import org.tanukis.tanuki.core.ui.list.AdapterDelegateClickListenerAdapter
import org.tanukis.tanuki.core.ui.list.OnListItemClickListener
import org.tanukis.tanuki.core.ui.list.decor.SpacingItemDecoration
import org.tanukis.tanuki.core.util.ext.getDisplayMessage
import org.tanukis.tanuki.core.util.ext.textAndVisible
import org.tanukis.tanuki.databinding.ItemListGroupBinding
import org.tanukis.tanuki.list.ui.MangaSelectionDecoration
import org.tanukis.tanuki.list.ui.adapter.mangaGridItemAD
import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.list.ui.model.MangaListModel
import org.tanukis.tanuki.list.ui.size.ItemSizeResolver
import org.tanukis.tanuki.search.ui.multi.SearchResultsListModel

@SuppressLint("NotifyDataSetChanged")
fun searchResultsAD(
	sharedPool: RecycledViewPool,
	sizeResolver: ItemSizeResolver,
	selectionDecoration: MangaSelectionDecoration,
	listener: OnListItemClickListener<MangaListModel>,
	itemClickListener: OnListItemClickListener<SearchResultsListModel>,
) = adapterDelegateViewBinding<SearchResultsListModel, ListModel, ItemListGroupBinding>(
	{ layoutInflater, parent -> ItemListGroupBinding.inflate(layoutInflater, parent, false) },
) {

	binding.recyclerView.setRecycledViewPool(sharedPool)
	val adapter = ListDelegationAdapter(mangaGridItemAD(sizeResolver, listener))
	binding.recyclerView.addItemDecoration(selectionDecoration)
	binding.recyclerView.adapter = adapter
	val spacing = context.resources.getDimensionPixelOffset(R.dimen.grid_spacing_outer)
	binding.recyclerView.addItemDecoration(SpacingItemDecoration(spacing, withBottomPadding = true))
	val eventListener = AdapterDelegateClickListenerAdapter(this, itemClickListener)
	binding.buttonMore.setOnClickListener(eventListener)

	bind {
		binding.textViewTitle.text = item.getTitle(context)
		binding.buttonMore.isVisible = item.source !== UnknownMangaSource
		adapter.items = item.list
		adapter.notifyDataSetChanged()
		binding.recyclerView.isGone = item.list.isEmpty()
		binding.textViewError.textAndVisible = item.error?.getDisplayMessage(context.resources)
	}
}
