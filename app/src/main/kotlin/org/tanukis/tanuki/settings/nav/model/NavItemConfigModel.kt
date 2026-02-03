package org.tanukis.tanuki.settings.nav.model

import androidx.annotation.StringRes
import org.tanukis.tanuki.core.prefs.NavItem
import org.tanukis.tanuki.list.ui.model.ListModel

data class NavItemConfigModel(
	val item: NavItem,
	@StringRes val disabledHintResId: Int,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is NavItemConfigModel && other.item == item
	}
}
