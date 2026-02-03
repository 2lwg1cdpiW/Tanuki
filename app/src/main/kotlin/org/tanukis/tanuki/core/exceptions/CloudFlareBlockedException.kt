package org.tanukis.tanuki.core.exceptions

import org.tanukis.tanuki.core.model.UnknownMangaSource
import org.tanukis.tanuki.parsers.model.MangaSource
import org.tanukis.tanuki.parsers.network.CloudFlareHelper

class CloudFlareBlockedException(
	override val url: String,
	source: MangaSource?,
) : CloudFlareException("Blocked by CloudFlare", CloudFlareHelper.PROTECTION_BLOCKED) {

	override val source: MangaSource = source ?: UnknownMangaSource
}
