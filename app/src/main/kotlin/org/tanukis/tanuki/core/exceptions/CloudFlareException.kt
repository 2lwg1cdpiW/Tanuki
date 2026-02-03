package org.tanukis.tanuki.core.exceptions

import okio.IOException
import org.tanukis.tanuki.parsers.model.MangaSource

abstract class CloudFlareException(
	message: String,
	val state: Int,
) : IOException(message) {

	abstract val url: String

	abstract val source: MangaSource
}
