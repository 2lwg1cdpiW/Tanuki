package org.tanukis.tanuki.details.domain

import org.tanukis.tanuki.core.util.LocaleStringComparator
import org.tanukis.tanuki.details.ui.model.MangaBranch

class BranchComparator : Comparator<MangaBranch> {

	private val delegate = LocaleStringComparator()

	override fun compare(o1: MangaBranch, o2: MangaBranch): Int = delegate.compare(o1.name, o2.name)
}
