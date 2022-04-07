package com.project.giniatovia.presentation.presenter

import android.util.Log
import com.project.giniatovia.domain.common.Result
import com.project.giniatovia.domain.interfaces.ResultCallback
import com.project.giniatovia.domain.repositories.GroupsRepository
import com.project.giniatovia.domain.repositories.ResultList
import com.project.giniatovia.presentation.base.GroupsContract
import com.project.giniatovia.presentation.models.GroupViewData
import com.project.giniatovia.presentation.models.GroupsListViewData

class GroupsPresenter(
    private val userId: Long,
    private val view: GroupsContract.View,
    private val repository: GroupsRepository
) : GroupsContract.Presenter {

    override var unsubCollection = mutableSetOf<Long>()
    override var resultList: ResultList? = null

    private val map = mutableMapOf<Long, Pair<Int?, String?>>()

    private fun mapToGroupViewData() {
        val viewData = GroupsListViewData(
            groupsList = resultList!!.map {
                GroupViewData(
                    id = it.id,
                    name = it.name,
                    photo_100 = it.photo_100,
                    members_count = it.members_count,
                    description = it.description,
                    checked = unsubCollection.contains(it.id)
                )
            }
        )
        view.updateList(viewData)
        view.hideProgressBar()
        view.lightenBackground()
        if (unsubCollection.isEmpty()) {
            view.hideButton()
        } else {
            view.showButton()
            view.setCounterValue(unsubCollection.size)
        }
    }

    private fun getVKGroups() {
        if (resultList != null) {
            return
        }
        repository.getGroups(
            userId,
            object : ResultCallback<ResultList> {

                override fun onResult(result: Result.Success<ResultList>) {
                    resultList = result.result
                    mapToGroupViewData()
                }

                override fun onError(result: Result.Error) {
                    view.showMessage("Ошибка")
                }
            }
        )
    }

    override fun onUnsubscribe() {
        if (unsubCollection.isEmpty()) {
            return
        } else {
            unsubCollection.forEachIndexed { index, id ->
                repository.leaveGroup(
                    id,
                    object : ResultCallback<Int> {
                        override fun onResult(result: Result.Success<Int>) {
                            view.darkenBackground()
                            view.showProgressBar()
                            if (result.result != 1) view.showMessage(
                                "Произошла ошибка у группы $id"
                            )
                            if (index == unsubCollection.size - 1) {
                                resultList = null
                                unsubCollection.clear()
                                this@GroupsPresenter.getVKGroups()
                            }
                        }

                        override fun onError(result: Result.Error) {
                            view.showMessage("Ошибка")
                        }
                    }
                )
            }
        }
    }

    override fun onRefresh() {
        resultList = null
        unsubCollection.clear()
        this@GroupsPresenter.getVKGroups()
    }

    override fun saveGroups() {
        repository.saveGroupsCollection(resultList!!)
    }

    override fun restoreGroups() {
        resultList = repository.restoreGroupsCollection()
    }

    private fun onDataIsReady(group: GroupViewData) {
        val friendsCount = map[group.id]?.first
        val dateString = map[group.id]?.second
        if ((friendsCount != null) && (dateString != null)) {
            view.setGroupInfo(
                group,
                friendsCount,
                dateString
            )
        }
    }

    override fun setUserId() {
        this.getVKGroups()
    }

    override fun restoreResultList() {
        restoreGroups()
        mapToGroupViewData()
    }

    override fun setUnsubscribeSet(set: MutableSet<Long>) {
        unsubCollection = set
    }

    override fun onGroupClick(group: GroupViewData) {
        if (unsubCollection.contains(group.id)) {
            unsubCollection.remove(group.id)
        } else {
            unsubCollection.add(group.id)
        }
        mapToGroupViewData()
    }

    override fun onGroupLongClick(group: GroupViewData) {
        if ((map[group.id]?.first != null) && (map[group.id]?.second != null)) {
            view.onShowPopupWindow(group)
            onDataIsReady(group)
            return
        }
        view.onShowPopupWindow(group)
        repository.getFriendCount(
            group.id,
            object : ResultCallback<Int> {
                override fun onResult(result: Result.Success<Int>) {
                    val groupAdditionalData = map[group.id]
                    if (map[group.id] == null) {
                        map[group.id] = result.result to null
                    } else {
                        map[group.id] = result.result to groupAdditionalData?.second
                    }
                    onDataIsReady(group)
                }

                override fun onError(result: Result.Error) {
                    Log.d("TAG", "Ошибка в друзьях ${result.exception}")
                }
            }
        )
        repository.getLastPostDate(
            -group.id,
            object : ResultCallback<String> {
                override fun onResult(result: Result.Success<String>) {
                    val groupAdditionalData = map[group.id]
                    if (map[group.id] == null) {
                        map[group.id] = null to result.result
                    } else {
                        map[group.id] = groupAdditionalData?.first to result.result
                    }
                    onDataIsReady(group)
                }

                override fun onError(result: Result.Error) {
                    Log.d("TAG", "Ошибка в дате ${result.exception}")
                }
            }
        )
    }
}
