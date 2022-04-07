package com.project.giniatovia.data.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class Group(
    val id: Long,
    val name: String,
    val screenName: String,
    val isClosed: Int,
    val type: String,
    val is_admin: Int,
    val is_member: Int,
    val is_advertiser: Int,
    val photo_50: String,
    val photo_100: String,
    val photo_200: String,
    val members_count: Int,
    val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(screenName)
        parcel.writeInt(isClosed)
        parcel.writeString(type)
        parcel.writeInt(is_admin)
        parcel.writeInt(is_member)
        parcel.writeInt(is_advertiser)
        parcel.writeString(photo_50)
        parcel.writeString(photo_100)
        parcel.writeString(photo_200)
        parcel.writeInt(members_count)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Group> {
        override fun createFromParcel(parcel: Parcel): Group {
            return Group(parcel)
        }

        override fun newArray(size: Int): Array<Group?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject) = Group(
            id = json.optLong("id", 0),
            name = json.optString("name", ""),
            screenName = json.optString("screenName", ""),
            isClosed = json.optInt("isClosed", 0),
            type = json.optString("type", ""),
            is_admin = json.optInt("is_admin", 0),
            is_member = json.optInt("is_member", 0),
            is_advertiser = json.optInt("is_advertiser", 0),
            photo_50 = json.optString("photo_50", ""),
            photo_100 = json.optString("photo_100", ""),
            photo_200 = json.optString("photo_200", ""),
            members_count = json.optInt("members_count", 0),
            description = json.optString("description", "")
        )
    }
}

fun Group.toGroupInfo(): GroupInfo {
    return GroupInfo(
        id = id,
        name = name,
        photo_100 = photo_100,
        members_count = members_count,
        description = description
    )
}
