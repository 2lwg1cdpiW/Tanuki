package org.tanukis.tanuki.core.model.parcelable

import android.os.Parcel
import kotlinx.parcelize.Parceler
import org.tanukis.tanuki.core.model.MangaSource
import org.tanukis.tanuki.parsers.model.MangaSource

class MangaSourceParceler : Parceler<MangaSource> {

	override fun create(parcel: Parcel): MangaSource = MangaSource(parcel.readString())

	override fun MangaSource.write(parcel: Parcel, flags: Int) {
		parcel.writeString(name)
	}
}
