package org.tanukis.tanuki.reader.ui

import org.tanukis.tanuki.reader.ui.pager.ReaderPage

data class ReaderContent(
	val pages: List<ReaderPage>,
	val state: ReaderState?
)