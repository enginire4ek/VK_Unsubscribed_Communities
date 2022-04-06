package com.project.giniatovia.presentation.base

import com.project.giniatovia.domain.repositories.ResultList
import com.project.giniatovia.presentation.models.GroupViewData
import com.project.giniatovia.presentation.models.GroupsListViewData

interface GroupsContract {
    // no view android
    interface Presenter : BasePresenter {
        // связывание с domain-логикой
        val unsubCollection: MutableSet<Long>
        val resultList: ResultList?

        fun setUserId()
        fun setResultList(groupsList: GroupsListViewData)
        fun setUnsubscribeSet(set: MutableSet<Long>)
        fun onGroupLongClick(group: GroupViewData)
        fun onGroupClick(group: GroupViewData)
        fun onUnsubscribe()
        fun onRefresh()
    }

    interface View : BaseView<Presenter> {
        // set text, onclick
        fun initRecyclerView()
        fun showMessage(text: String)
        fun updateList(groupsList: GroupsListViewData)
        fun setCounterValue(counter: Int)
        fun onShowPopupWindow(group: GroupViewData)
        fun hideButton()
        fun showButton()
        fun showProgressBar()
        fun hideProgressBar()
        fun setGroupInfo(group: GroupViewData, friend: Int, date: String)
        fun darkenBackground()
        fun lightenBackground()
    }
}
