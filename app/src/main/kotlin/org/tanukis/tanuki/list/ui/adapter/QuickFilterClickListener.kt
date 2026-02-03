package org.tanukis.tanuki.list.ui.adapter

import org.tanukis.tanuki.list.domain.ListFilterOption

interface QuickFilterClickListener {

	fun onFilterOptionClick(option: ListFilterOption)
}
