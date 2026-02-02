package org.tanukis.tanuki.core.util

import android.os.Build
import android.webkit.MimeTypeMap
import org.jetbrains.annotations.Blocking
import org.tanukis.tanuki.core.util.ext.MimeType
import org.tanukis.tanuki.core.util.ext.toMimeTypeOrNull
import org.tanukis.tanuki.parsers.util.nullIfEmpty
import org.tanukis.tanuki.parsers.util.removeSuffix
import org.tanukis.tanuki.parsers.util.runCatchingCancellable
import java.io.File
import java.nio.file.Files
import coil3.util.MimeTypeMap as CoilMimeTypeMap

object MimeTypes {

	fun getMimeTypeFromExtension(fileName: String): MimeType? {
		return CoilMimeTypeMap.getMimeTypeFromExtension(getNormalizedExtension(fileName) ?: return null)
			?.toMimeTypeOrNull()
	}

	fun getMimeTypeFromUrl(url: String): MimeType? {
		return CoilMimeTypeMap.getMimeTypeFromUrl(url)?.toMimeTypeOrNull()
	}

	fun getExtension(mimeType: MimeType?): String? {
		return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType?.toString() ?: return null)?.nullIfEmpty()
	}

	@Blocking
	fun probeMimeType(file: File): MimeType? {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			runCatchingCancellable {
				Files.probeContentType(file.toPath())?.toMimeTypeOrNull()
			}.getOrNull()?.let { return it }
		}
		return getMimeTypeFromExtension(file.name)
	}

	fun getNormalizedExtension(name: String): String? = name
		.lowercase()
		.removeSuffix('~')
		.removeSuffix(".tmp")
		.substringAfterLast('.', "")
		.takeIf { it.length in 2..5 }
}
