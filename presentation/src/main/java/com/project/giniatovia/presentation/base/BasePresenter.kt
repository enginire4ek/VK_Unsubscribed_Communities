package com.project.giniatovia.presentation.base

interface BasePresenter {
    fun onAttach()
    fun onDetach()
}

interface BaseView<T> {
    fun setPresenter(presenter: T)
}
