package com.project.giniatovia.presentation.models

import android.os.Parcel
import android.os.Parcelable

data class GroupsListViewData(val groupsList: List<GroupViewData>) : Parcelable {
    constructor(parcel: Parcel) : this(
        listOf<GroupViewData>().apply {
            parcel.readList(this, GroupViewData::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupsListViewData> {
        override fun createFromParcel(parcel: Parcel): GroupsListViewData {
            return GroupsListViewData(parcel)
        }

        override fun newArray(size: Int): Array<GroupsListViewData?> {
            return arrayOfNulls(size)
        }
    }
}
