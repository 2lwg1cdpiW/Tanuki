package org.tanukis.tanuki.reader.ui

import org.tanukis.tanuki.bookmarks.domain.Bookmark
import org.tanukis.tanuki.parsers.model.MangaChapter
import org.tanukis.tanuki.reader.ui.pager.ReaderPage

interface ReaderNavigationCallback {

	fun onPageSelected(page: ReaderPage): Boolean

	fun onChapterSelected(chapter: MangaChapter): Boolean

	fun onBookmarkSelected(bookmark: Bookmark): Boolean = onPageSelected(
		ReaderPage(bookmark.toMangaPage(), bookmark.page, bookmark.chapterId),
	)
}
