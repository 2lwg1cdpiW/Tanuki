package org.tanukis.tanuki.settings.sources.catalog

import org.tanukis.tanuki.parsers.model.ContentType

data class SourcesCatalogFilter(
	val types: Set<ContentType>,
	val locale: String?,
	val isNewOnly: Boolean,
)
