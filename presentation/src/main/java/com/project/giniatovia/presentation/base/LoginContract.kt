package com.project.giniatovia.presentation.base

import com.vk.api.sdk.exceptions.VKAuthException

interface LoginContract {
    interface Presenter : BasePresenter

    interface View : BaseView<Presenter> {
        fun onLogin()
        fun onLoginFailed(exception: VKAuthException)
    }
}
