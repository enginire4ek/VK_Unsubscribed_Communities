package com.project.giniatovia.presentation.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.project.giniatovia.presentation.R
import com.project.giniatovia.presentation.base.LoginContract
import com.project.giniatovia.presentation.presenter.LoginPresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKAuthException

class LoginFragment : Fragment(), LoginContract.View {

    private lateinit var presenter: LoginContract.Presenter
    private lateinit var authLauncher: ActivityResultLauncher<Collection<VKScope>>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authLauncher = VK.login(requireActivity()) { result: VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> onSuccess(result)
                is VKAuthenticationResult.Failed -> onLoginFailed(result.exception)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPresenter(LoginPresenter(this@LoginFragment))
        presenter.onAttach()

        configSettings()

        view.findViewById<Button>(R.id.enterButton).setOnClickListener {
            onLogin()
        }
    }

    private fun configSettings() {
        if (resources.configuration.orientation ==
            Configuration.ORIENTATION_LANDSCAPE
        ) {
            val icon = view?.findViewById<View>(R.id.vk_icon)
            val param = icon?.layoutParams as ViewGroup.MarginLayoutParams
            param.topMargin = MARGIN_TOP
            icon.layoutParams = param
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetach()
    }

    private fun authLaunch() = authLauncher.launch(arrayListOf(VKScope.GROUPS))

    private fun onSuccess(result: VKAuthenticationResult.Success) {
        val fragment = GroupsFragment.newInstance(user_id = result.token.userId.value)
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container, fragment)
        }
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun onLogin() {
        authLaunch()
    }

    override fun onLoginFailed(exception: VKAuthException) {
        if (!exception.isCanceled) {
            AlertDialog.Builder(requireActivity())
                .setMessage(getString(R.string.auth_error))
                .setPositiveButton(R.string.retry) { _, _ ->
                    authLaunch()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    companion object {
        const val MARGIN_TOP = 25
    }
}
