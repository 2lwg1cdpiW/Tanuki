package org.tanukis.tanuki.core.exceptions

import org.tanukis.tanuki.parsers.model.Manga

class UnsupportedSourceException(
	message: String?,
	val manga: Manga?,
) : IllegalArgumentException(message)
