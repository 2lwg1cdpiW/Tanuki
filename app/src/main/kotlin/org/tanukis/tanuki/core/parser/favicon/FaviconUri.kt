package org.tanukis.tanuki.core.parser.favicon

import android.net.Uri
import org.tanukis.tanuki.parsers.model.MangaSource

const val URI_SCHEME_FAVICON = "favicon"

fun MangaSource.faviconUri(): Uri = Uri.fromParts(URI_SCHEME_FAVICON, name, null)