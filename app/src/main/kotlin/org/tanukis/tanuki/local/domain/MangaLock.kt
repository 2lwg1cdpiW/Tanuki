package org.tanukis.tanuki.local.domain

import org.tanukis.tanuki.core.util.MultiMutex
import org.tanukis.tanuki.parsers.model.Manga
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaLock @Inject constructor() : MultiMutex<Manga>()
