package com.hana053.micropost.shared.posts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.curioustechizen.ago.RelativeTimeTextView
import com.hana053.micropost.R
import com.hana053.micropost.domain.Micropost
import com.hana053.micropost.domain.User
import com.hana053.micropost.shared.avatar.AvatarView
import kotlinx.android.synthetic.main.item_posts.view.*
import rx.subjects.PublishSubject


class PostListAdapter(
    val posts: MutableList<Micropost> = mutableListOf()
) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    val avatarClicksSubject: PublishSubject<User> = PublishSubject.create<User>()

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = posts[position]
        holder.userName.text = item.user.name
        holder.createdAt.setReferenceTime(item.createdAt)
        holder.content.text = item.content
        holder.container.tag = item

        AvatarView(holder.avatar).render(item.user)
        holder.avatar.setOnClickListener {
            avatarClicksSubject.onNext(item.user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posts, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: LinearLayout = view.container
        val avatar: ImageView = view.avatar
        val userName: TextView = view.userName
        val createdAt: RelativeTimeTextView = view.createdAt
        val content: TextView = view.content
    }

    fun getFirstItemId(): Long? {
        return posts.map { it.id }.firstOrNull()
    }

    fun getLastItemId(): Long? {
        return posts.map { it.id }.lastOrNull()
    }

    fun addAll(location: Int, posts: Collection<Micropost>): Boolean {
        if (this.posts.addAll(location, posts)) {
            notifyItemRangeInserted(location, posts.size)
            return true
        }
        return false
    }


}