package org.tanukis.tanuki.settings.utils

import androidx.preference.Preference
import org.tanukis.tanuki.R

class PercentSummaryProvider : Preference.SummaryProvider<SliderPreference> {

	private var percentPattern: String? = null

	override fun provideSummary(preference: SliderPreference): CharSequence {
		val pattern = percentPattern ?: preference.context.getString(R.string.percent_string_pattern).also {
			percentPattern = it
		}
		return pattern.format(preference.value.toString())
	}
}
