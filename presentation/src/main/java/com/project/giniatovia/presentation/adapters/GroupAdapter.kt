package com.project.giniatovia.presentation.adapters

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.project.giniatovia.presentation.R
import com.project.giniatovia.presentation.fragment.GroupsFragment
import com.project.giniatovia.presentation.models.GroupViewData
import com.project.giniatovia.presentation.models.GroupsListViewData

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    private var groups = GroupsListViewData(listOf())
    private var clickListener: ClickListener? = null

    interface ClickListener {
        fun onItemClick(group: GroupViewData)
        fun onItemLongClick(group: GroupViewData)
    }

    fun setOnItemClickListener(clickList: ClickListener) {
        clickListener = clickList
    }

    private fun getGroupByPosition(position: Int): GroupViewData {
        return groups.groupsList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_holder_group, parent, false)
        return GroupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups.groupsList[position])
        Log.d("TAG", "$position ${groups.groupsList[position].name}")
    }

    override fun getItemCount() = groups.groupsList.size

    fun updateList(groupsList: GroupsListViewData) {
        val groupsDiffUtilCallback = GroupsDiffUtilCallback(groups, groupsList)
        val groupsDiffResult = DiffUtil.calculateDiff(groupsDiffUtilCallback)
        groups = groupsList
        groupsDiffResult.dispatchUpdatesTo(this)
    }

    inner class GroupViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener {

        init {
            itemView.findViewById<FrameLayout>(R.id.icon).setOnClickListener(this)
            itemView.findViewById<FrameLayout>(R.id.icon).setOnLongClickListener(this)
        }

        fun selectGroup() {
            val draweeView = itemView.findViewById<SimpleDraweeView>(R.id.draweeView)
            draweeView.setPadding(MARGIN_LEFT, MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM)
            draweeView.background = ColorDrawable(
                ContextCompat.getColor(itemView.context, R.color.more_light_button_background)
            )
            itemView.findViewById<CheckBox>(R.id.groupSelected).visibility = View.VISIBLE
            val whiteColor = ContextCompat.getColor(itemView.context, R.color.white)
            val roundingParams = RoundingParams()
            roundingParams.setBorder(whiteColor, GroupsFragment.BORDER)
            roundingParams.roundAsCircle = true
            draweeView.hierarchy.roundingParams = roundingParams
        }

        fun unselectGroup() {
            val draweeView = itemView.findViewById<SimpleDraweeView>(R.id.draweeView)
            draweeView.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM)
            draweeView.background = null
            itemView.findViewById<CheckBox>(R.id.groupSelected).visibility = View.INVISIBLE
            val roundingParams = RoundingParams()
            val whiteColor = ContextCompat.getColor(itemView.context, R.color.white)
            roundingParams.setBorder(whiteColor, GroupsFragment.NO_BORDER)
            roundingParams.roundAsCircle = false
            draweeView.hierarchy.roundingParams = roundingParams
        }

        fun bind(group: GroupViewData) {
            if (group.checked) {
                selectGroup()
            } else {
                unselectGroup()
            }
            setImageResource(group.photo_100)
            itemView.findViewById<TextView>(R.id.tv_title).text = group.name
        }

        private fun setImageResource(photo100: String) {
            val uri = Uri.parse(photo100)
            val draweeView = itemView.findViewById<SimpleDraweeView>(R.id.draweeView)
            draweeView.setImageURI(uri)
        }

        override fun onClick(p0: View?) {
            clickListener!!.onItemClick(
                this@GroupAdapter.getGroupByPosition(bindingAdapterPosition)
            )
        }

        override fun onLongClick(p0: View?): Boolean {
            clickListener!!.onItemLongClick(
                this@GroupAdapter.getGroupByPosition(bindingAdapterPosition)
            )
            return false
        }
    }

    companion object {
        const val MARGIN_TOP = 8
        const val MARGIN_BOTTOM = 8
        const val MARGIN_LEFT = 8
        const val MARGIN_RIGHT = 8

        const val PADDING_TOP = 0
        const val PADDING_BOTTOM = 0
        const val PADDING_LEFT = 0
        const val PADDING_RIGHT = 0
    }
}
