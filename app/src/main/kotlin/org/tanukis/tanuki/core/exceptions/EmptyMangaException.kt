package org.tanukis.tanuki.core.exceptions

import org.tanukis.tanuki.details.ui.pager.EmptyMangaReason
import org.tanukis.tanuki.parsers.model.Manga

class EmptyMangaException(
    val reason: EmptyMangaReason?,
    val manga: Manga,
    cause: Throwable?
) : IllegalStateException(cause)
