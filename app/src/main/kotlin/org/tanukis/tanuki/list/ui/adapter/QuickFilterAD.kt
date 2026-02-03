package org.tanukis.tanuki.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.tanukis.tanuki.core.ui.widgets.ChipsView
import org.tanukis.tanuki.databinding.ItemQuickFilterBinding
import org.tanukis.tanuki.list.domain.ListFilterOption
import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.list.ui.model.QuickFilter

fun quickFilterAD(
	listener: QuickFilterClickListener,
) = adapterDelegateViewBinding<QuickFilter, ListModel, ItemQuickFilterBinding>(
	{ layoutInflater, parent -> ItemQuickFilterBinding.inflate(layoutInflater, parent, false) }
) {

	binding.chipsTags.onChipClickListener = ChipsView.OnChipClickListener { chip, data ->
		if (data is ListFilterOption) {
			listener.onFilterOptionClick(data)
		}
	}

	bind {
		binding.chipsTags.setChips(item.items)
	}
}
