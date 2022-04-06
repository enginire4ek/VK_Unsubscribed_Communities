package com.project.giniatovia.presentation.presenter

import com.project.giniatovia.presentation.base.LoginContract

class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {

    private var mView: LoginContract.View? = null

    override fun onAttach() {
        this.mView = view
    }

    override fun onDetach() {
        this.mView = null
    }
}
