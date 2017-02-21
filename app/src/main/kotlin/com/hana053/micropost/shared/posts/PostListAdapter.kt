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
    private val posts: MutableList<Micropost> = mutableListOf()
) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    val avatarClicksSubject: PublishSubject<User> = PublishSubject.create<User>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: LinearLayout = view.container
        val avatar: ImageView = view.avatar
        val userName: TextView = view.userName
        val createdAt: RelativeTimeTextView = view.createdAt
        val content: TextView = view.content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posts, parent, false)
            .let(::ViewHolder)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = posts[position]

        holder.apply {
            container.tag = item
            userName.text = item.user.name
            createdAt.setReferenceTime(item.createdAt)
            content.text = item.content
            AvatarView(avatar).render(item.user)
            avatar.setOnClickListener {
                avatarClicksSubject.onNext(item.user)
            }
        }

    }

    override fun getItemCount(): Int = posts.size

    fun getFirstItemId(): Long? = posts.map { it.id }.firstOrNull()

    fun getLastItemId(): Long? = posts.map { it.id }.lastOrNull()

    fun addAll(location: Int, posts: List<Micropost>): Boolean {
        if (this.posts.addAll(location, posts)) {
            notifyItemRangeInserted(location, posts.size)
            return true
        }
        return false
    }


}