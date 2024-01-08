package com.dshovhenia.vimeo.paging.compose.sample.core.utils.parceler

import android.os.Parcel
import kotlinx.parcelize.Parceler
import java.util.*

inline fun <T> Parcel.readNullable(reader: () -> T) =
  if (readInt() != 0) reader() else null

inline fun <T> Parcel.writeNullable(value: T?, writer: T.() -> Unit) {
  if (value != null) {
    writeInt(1)
    value.writer()
  } else {
    writeInt(0)
  }
}

object DateParceler : Parceler<Date?> {

  override fun create(parcel: Parcel) = parcel.readNullable { Date(parcel.readLong()) }

  override fun Date?.write(parcel: Parcel, flags: Int) = parcel.writeNullable(this) { parcel.writeLong(time) }
}
