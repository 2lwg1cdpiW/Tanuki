package org.tanukis.tanuki.details.ui.scrobbling

import org.tanukis.tanuki.core.nav.AppRouter
import org.tanukis.tanuki.core.ui.BaseListAdapter
import org.tanukis.tanuki.list.ui.model.ListModel

class ScrollingInfoAdapter(
	router: AppRouter,
) : BaseListAdapter<ListModel>() {

	init {
		delegatesManager.addDelegate(scrobblingInfoAD(router))
	}
}
