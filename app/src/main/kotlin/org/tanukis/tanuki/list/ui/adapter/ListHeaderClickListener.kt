package org.tanukis.tanuki.list.ui.adapter

import android.view.View
import org.tanukis.tanuki.list.ui.model.ListHeader

interface ListHeaderClickListener {

	fun onListHeaderClick(item: ListHeader, view: View)
}
