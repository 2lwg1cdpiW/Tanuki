package org.tanukis.tanuki.core.parser

import org.tanukis.tanuki.core.cache.MemoryContentCache
import org.tanukis.tanuki.core.model.TestMangaSource
import org.tanukis.tanuki.parsers.MangaLoaderContext

@Suppress("unused")
class TestMangaRepository(
	private val loaderContext: MangaLoaderContext,
	cache: MemoryContentCache
) : EmptyMangaRepository(TestMangaSource)
