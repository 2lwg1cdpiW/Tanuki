package org.tanukis.tanuki.suggestions.domain

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import org.tanukis.tanuki.core.db.MangaDatabase
import org.tanukis.tanuki.core.db.entity.toEntities
import org.tanukis.tanuki.core.db.entity.toEntity
import org.tanukis.tanuki.core.db.entity.toManga
import org.tanukis.tanuki.core.db.entity.toMangaTagsList
import org.tanukis.tanuki.core.model.toMangaSources
import org.tanukis.tanuki.core.util.ext.mapItems
import org.tanukis.tanuki.list.domain.ListFilterOption
import org.tanukis.tanuki.parsers.model.Manga
import org.tanukis.tanuki.parsers.model.MangaSource
import org.tanukis.tanuki.parsers.model.MangaTag
import org.tanukis.tanuki.suggestions.data.SuggestionEntity
import org.tanukis.tanuki.suggestions.data.SuggestionWithManga
import javax.inject.Inject

class SuggestionRepository @Inject constructor(
	private val db: MangaDatabase,
) {

	fun observeAll(): Flow<List<Manga>> {
		return db.getSuggestionDao().observeAll().mapItems {
			it.toManga()
		}
	}

	fun observeAll(limit: Int, filterOptions: Set<ListFilterOption>): Flow<List<Manga>> {
		return db.getSuggestionDao().observeAll(limit, filterOptions).mapItems {
			it.toManga()
		}
	}

	suspend fun getRandomList(limit: Int): List<Manga> {
		return db.getSuggestionDao().getRandom(limit).map {
			it.toManga()
		}
	}

	suspend fun clear() {
		db.getSuggestionDao().deleteAll()
	}

	suspend fun isEmpty(): Boolean {
		return db.getSuggestionDao().count() == 0
	}

	suspend fun getTopTags(limit: Int): List<MangaTag> {
		return db.getSuggestionDao().getTopTags(limit)
			.toMangaTagsList()
	}

	suspend fun getTopSources(limit: Int): List<MangaSource> {
		return db.getSuggestionDao().getTopSources(limit)
			.toMangaSources()
	}

	suspend fun replace(suggestions: Iterable<MangaSuggestion>) {
		db.withTransaction {
			db.getSuggestionDao().deleteAll()
			suggestions.forEach { (manga, relevance) ->
				val tags = manga.tags.toEntities()
				db.getTagsDao().upsert(tags)
				db.getMangaDao().upsert(manga.toEntity(), tags)
				db.getSuggestionDao().upsert(
					SuggestionEntity(
						mangaId = manga.id,
						relevance = relevance,
						createdAt = System.currentTimeMillis(),
					),
				)
			}
		}
	}

	private fun SuggestionWithManga.toManga() = manga.toManga(emptySet(), null)
}
