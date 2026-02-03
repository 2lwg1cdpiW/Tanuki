package org.tanukis.tanuki.scrobbling.common.ui.selector.adapter

import org.tanukis.tanuki.core.ui.BaseListAdapter
import org.tanukis.tanuki.core.ui.list.OnListItemClickListener
import org.tanukis.tanuki.list.ui.adapter.ListItemType
import org.tanukis.tanuki.list.ui.adapter.ListStateHolderListener
import org.tanukis.tanuki.list.ui.adapter.loadingFooterAD
import org.tanukis.tanuki.list.ui.adapter.loadingStateAD
import org.tanukis.tanuki.list.ui.model.ListModel
import org.tanukis.tanuki.scrobbling.common.domain.model.ScrobblerManga

class ScrobblerSelectorAdapter(
	clickListener: OnListItemClickListener<ScrobblerManga>,
	stateHolderListener: ListStateHolderListener,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.MANGA_SCROBBLING, scrobblingMangaAD(clickListener))
		addDelegate(ListItemType.FOOTER_LOADING, loadingFooterAD())
		addDelegate(ListItemType.HINT_EMPTY, scrobblerHintAD(stateHolderListener))
	}
}
