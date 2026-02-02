package org.tanukis.tanuki.core.exceptions

import okhttp3.Headers
import org.tanukis.tanuki.core.model.UnknownMangaSource
import org.tanukis.tanuki.parsers.model.MangaSource
import org.tanukis.tanuki.parsers.network.CloudFlareHelper

class CloudFlareProtectedException(
	override val url: String,
	source: MangaSource?,
	@Transient val headers: Headers,
) : CloudFlareException("Protected by CloudFlare", CloudFlareHelper.PROTECTION_CAPTCHA) {

	override val source: MangaSource = source ?: UnknownMangaSource
}
