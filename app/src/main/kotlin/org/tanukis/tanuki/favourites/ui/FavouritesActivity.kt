package org.tanukis.tanuki.favourites.ui

import android.os.Bundle
import org.tanukis.tanuki.core.nav.AppRouter
import org.tanukis.tanuki.core.ui.FragmentContainerActivity
import org.tanukis.tanuki.favourites.ui.list.FavouritesListFragment

class FavouritesActivity : FragmentContainerActivity(FavouritesListFragment::class.java) {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val categoryTitle = intent.getStringExtra(AppRouter.KEY_TITLE)
		if (categoryTitle != null) {
			title = categoryTitle
		}
	}
}
