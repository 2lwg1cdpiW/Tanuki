package org.tanukis.tanuki.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import org.tanukis.tanuki.R
import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.list.ui.model.LoadingFooter

fun loadingFooterAD() = adapterDelegate<LoadingFooter, ListModel>(R.layout.item_loading_footer) {
}