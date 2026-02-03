package org.tanukis.tanuki.details.domain

import org.tanukis.tanuki.core.parser.MangaRepository
import org.tanukis.tanuki.core.util.ext.printStackTraceDebug
import org.tanukis.tanuki.parsers.model.Manga
import org.tanukis.tanuki.parsers.util.runCatchingCancellable
import javax.inject.Inject

class RelatedMangaUseCase @Inject constructor(
	private val mangaRepositoryFactory: MangaRepository.Factory,
) {

	suspend operator fun invoke(seed: Manga) = runCatchingCancellable {
		mangaRepositoryFactory.create(seed.source).getRelated(seed)
	}.onFailure {
		it.printStackTraceDebug()
	}.getOrNull()
}
