package com.project.giniatovia.presentation.fragment

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.giniatovia.domain.repositories.GroupsRepository
import com.project.giniatovia.presentation.R
import com.project.giniatovia.presentation.adapters.GroupAdapter
import com.project.giniatovia.presentation.base.GroupsContract
import com.project.giniatovia.presentation.dependencies.getDependenciesProvider
import com.project.giniatovia.presentation.models.GroupViewData
import com.project.giniatovia.presentation.models.GroupsListViewData
import com.project.giniatovia.presentation.presenter.GroupsPresenter

class GroupsFragment : Fragment(), GroupsContract.View {

    private lateinit var presenter: GroupsContract.Presenter

    private lateinit var adapter: GroupAdapter

    private var _backgroundProfile: SwipeRefreshLayout? = null
    private val backgroundProfile get() = _backgroundProfile!!
    private lateinit var popupWindow: PopupWindow

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPresenter(
            GroupsPresenter(
                arguments?.getLong("user_id") ?: -1,
                this@GroupsFragment,
                requireContext().getDependenciesProvider().get(GroupsRepository::class)
            )
        )

        initBackground()
        initRecyclerView()

        if (savedInstanceState != null) {
            presenter.setUnsubscribeSet(savedInstanceState.getLongArray(CHOICE)!!.toMutableSet())
            presenter.restoreResultList()
        } else {
            presenter.setUserId()
        }

        view.findViewById<AppCompatButton>(R.id.button_counter).setOnClickListener {
            presenter.onUnsubscribe()
        }

        view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout).apply {
            setColorSchemeColors(ContextCompat.getColor(context, R.color.light_button_background))
            setOnRefreshListener {
                presenter.onRefresh()
                isRefreshing = false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLongArray(CHOICE, presenter.unsubCollection.toLongArray())
        presenter.saveGroups()
    }

    private fun initBackground() {
        _backgroundProfile = view!!.findViewById(R.id.swipe_refresh_layout)
        backgroundProfile.foreground = ResourcesCompat
            .getDrawable(resources, R.drawable.drawable_background, null)
        lightenBackground()
    }

    override fun onShowPopupWindow(group: GroupViewData) {
        val popupView: View = LayoutInflater.from(activity)
            .inflate(R.layout.pop_up_info, null)

        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )

        popupWindow.apply {
            showAtLocation(view, Gravity.BOTTOM, 0, 0)
            animationStyle = R.style.PopUpAnimation
            darkenBackground()
        }
    }

    override fun initRecyclerView() {
        adapter = GroupAdapter()
        adapter.setOnItemClickListener(
            object : GroupAdapter.ClickListener {
                override fun onItemClick(group: GroupViewData) {
                    presenter.onGroupClick(group)
                }

                override fun onItemLongClick(group: GroupViewData) {
                    presenter.onGroupLongClick(group)
                }
            }
        )
        val columns = if (resources.configuration.orientation ==
            Configuration.ORIENTATION_PORTRAIT
        ) {
            COLUMNS_PORTRAIT
        } else {
            COLUMNS_LANDSCAPE
        }
        val recycler = view?.findViewById<RecyclerView>(R.id.recycler_view)
        recycler?.layoutManager = GridLayoutManager(requireContext(), columns)
        recycler?.adapter = adapter
    }

    override fun setCounterValue(counter: Int) {
        view?.findViewById<TextView>(R.id.counter)?.text = counter.toString()
    }

    override fun hideButton() {
        view?.findViewById<TextView>(R.id.counter)?.visibility = View.GONE
        view?.findViewById<AppCompatButton>(R.id.button_counter)?.visibility = View.GONE
    }

    override fun showButton() {
        view?.findViewById<TextView>(R.id.counter)?.visibility = View.VISIBLE
        view?.findViewById<AppCompatButton>(R.id.button_counter)?.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        view?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE
    }

    override fun showProgressBar() {
        view?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.VISIBLE
    }

    override fun setGroupInfo(group: GroupViewData, friend: Int, date: String) {
        popupWindow.contentView.apply {
            findViewById<ProgressBar>(R.id.pop_up_progress_bar).visibility = View.GONE
            findViewById<AppCompatButton>(R.id.pop_up_enterButton).visibility = View.VISIBLE
            findViewById<View>(R.id.view1).visibility = View.VISIBLE
            findViewById<View>(R.id.view2).visibility = View.VISIBLE
            findViewById<View>(R.id.view3).visibility = View.VISIBLE
            findViewById<TextView>(R.id.group_name).text = group.name
            if ((friend == 0) || (group.members_count == 0)) {
                findViewById<TextView>(R.id.subscribers).text =
                    getString(R.string.subs, group.members_count)
            } else {
                findViewById<TextView>(R.id.subscribers).text =
                    getString(R.string.subs_with_friends, group.members_count, friend)
            }
            if (group.description.isEmpty()) {
                findViewById<LinearLayout>(R.id.description_block).visibility = View.GONE
            } else {
                findViewById<TextView>(R.id.description).text = group.description
            }
            findViewById<TextView>(R.id.last_post).text =
                getString(R.string.last_post_string, date)
            findViewById<View>(R.id.close_button).setOnClickListener {
                popupWindow.dismiss()
                lightenBackground()
            }
            findViewById<AppCompatButton>(R.id.pop_up_enterButton).setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("vkontakte://profile/${-group.id}")
                    )
                )
            }
        }
    }

    override fun darkenBackground() {
        backgroundProfile.foreground.alpha = DARK_BACKGROUND
    }

    override fun lightenBackground() {
        backgroundProfile.foreground.alpha = NORMAL_BACKGROUND
    }

    override fun showMessage(text: String) {
        Toast.makeText(requireContext(), "Ошибка $text", Toast.LENGTH_SHORT).show()
    }

    override fun updateList(groupsList: GroupsListViewData) {
        adapter.updateList(groupsList)
    }

    override fun setPresenter(presenter: GroupsContract.Presenter) {
        this.presenter = presenter
    }

    companion object {
        const val DARK_BACKGROUND = 100
        const val NORMAL_BACKGROUND = 0
        const val COLUMNS_PORTRAIT = 3
        const val COLUMNS_LANDSCAPE = 5
        const val NO_BORDER = 0.0f
        const val BORDER = 5.0f
        const val CHOICE = "CHOICE"

        @JvmStatic
        fun newInstance(userId: Long) =
            GroupsFragment().apply {
                arguments = Bundle().apply {
                    putLong("user_id", userId)
                }
            }
    }
}
