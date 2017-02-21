package com.hana053.micropost.pages.relateduserlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hana053.micropost.R
import com.hana053.micropost.domain.RelatedUser
import com.hana053.micropost.domain.User
import com.hana053.micropost.shared.avatar.AvatarView
import com.hana053.micropost.shared.followbtn.FollowBtnView
import kotlinx.android.synthetic.main.item_related_users.view.*
import rx.subjects.PublishSubject


class RelatedUserListAdapter(
    private val users: MutableList<RelatedUser> = mutableListOf()
) : RecyclerView.Adapter<RelatedUserListAdapter.ViewHolder>() {

    val followBtnClicksSubject: PublishSubject<FollowBtnView> = PublishSubject.create()
    val avatarClicksSubject: PublishSubject<User> = PublishSubject.create()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: LinearLayout = view.container
        val avatar: ImageView = view.avatar
        val userName: TextView = view.userName
        val followBtn: Button = view.followBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_related_users, parent, false)
            .let(::ViewHolder)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]

        holder.apply {
            container.tag = item
            userName.text = item.name

            AvatarView(avatar).render(item.toUser())
            avatar.setOnClickListener {
                avatarClicksSubject.onNext(item.toUser())
            }

            FollowBtnView(followBtn).apply {
                render(item.toUser())
                followBtn.setOnClickListener {
                    followBtnClicksSubject.onNext(this)
                }
            }
        }
    }

    override fun getItemCount() = users.size

    fun getLastItemId() = users.map { it.relationshipId }.lastOrNull()

    fun addAll(location: Int, users: List<RelatedUser>): Boolean {
        if (this.users.addAll(location, users)) {
            notifyItemRangeInserted(location, users.size)
            return true
        }
        return false
    }

}