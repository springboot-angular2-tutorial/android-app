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

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_related_users, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]

        holder.userName.text = item.name
        holder.container.tag = item

        AvatarView(holder.avatar).render(item.toUser())
        holder.avatar.setOnClickListener {
            avatarClicksSubject.onNext(item.toUser())
        }

        val followBtnView = FollowBtnView(holder.followBtn)
        followBtnView.render(item.toUser())
        holder.followBtn.setOnClickListener {
            followBtnClicksSubject.onNext(followBtnView)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: LinearLayout = view.container
        val avatar: ImageView = view.avatar
        val userName: TextView = view.userName
        val followBtn: Button = view.followBtn
    }

    fun getLastItemId(): Long? {
        return users.map { it.relationshipId }.lastOrNull()
    }

    fun addAll(location: Int, users: List<RelatedUser>): Boolean {
        if (this.users.addAll(location, users)) {
            notifyItemRangeInserted(location, users.size)
            return true
        }
        return false
    }

}