package com.project.giniatovia.presentation.models

import android.os.Parcel
import android.os.Parcelable

data class GroupViewData(
    val id: Long,
    val name: String,
    val photo_100: String,
    val members_count: Int,
    val description: String,
    val checked: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(photo_100)
        parcel.writeInt(members_count)
        parcel.writeString(description)
        parcel.writeByte(if (checked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupViewData> {
        override fun createFromParcel(parcel: Parcel): GroupViewData {
            return GroupViewData(parcel)
        }

        override fun newArray(size: Int): Array<GroupViewData?> {
            return arrayOfNulls(size)
        }
    }
}
