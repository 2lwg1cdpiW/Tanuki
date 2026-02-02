package org.tanukis.tanuki.explore.ui.adapter

import android.view.View
import org.tanukis.tanuki.list.ui.adapter.ListHeaderClickListener
import org.tanukis.tanuki.list.ui.adapter.ListStateHolderListener

interface ExploreListEventListener : ListStateHolderListener, View.OnClickListener, ListHeaderClickListener
