package org.tanukis.tanuki.core.exceptions

class SyncApiException(
	message: String,
	val code: Int,
) : RuntimeException(message)
