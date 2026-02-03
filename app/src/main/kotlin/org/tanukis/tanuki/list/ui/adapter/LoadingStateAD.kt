package org.tanukis.tanuki.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import org.tanukis.tanuki.R
import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.list.ui.model.LoadingState

fun loadingStateAD() = adapterDelegate<LoadingState, ListModel>(R.layout.item_loading_state) {
}