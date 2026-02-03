package org.tanukis.tanuki.scrobbling.common.domain

import okio.IOException
import org.tanukis.tanuki.scrobbling.common.domain.model.ScrobblerService

class ScrobblerAuthRequiredException(
	val scrobbler: ScrobblerService,
) : IOException()
