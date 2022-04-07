package com.project.giniatovia.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.project.giniatovia.presentation.models.GroupsListViewData

class GroupsDiffUtilCallback(
    private val oldList: GroupsListViewData,
    private val newList: GroupsListViewData
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.groupsList.size

    override fun getNewListSize() = newList.groupsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldGroup = oldList.groupsList[oldItemPosition]
        val newGroup = newList.groupsList[newItemPosition]
        return oldGroup.id == newGroup.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldGroup = oldList.groupsList[oldItemPosition]
        val newGroup = newList.groupsList[newItemPosition]
        return (
            oldGroup.name == newGroup.name &&
                oldGroup.photo_100 == newGroup.photo_100 &&
                oldGroup.members_count == newGroup.members_count &&
                oldGroup.description == newGroup.description &&
                oldGroup.checked == newGroup.checked
            )
    }
}
