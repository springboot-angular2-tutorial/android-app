package com.hana053.micropost.presentation.core.components.micropostlist;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hana053.micropost.databinding.PostItemBinding;
import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.presentation.core.components.avatar.AvatarViewListener;
import com.hana053.micropost.presentation.core.components.avatar.AvatarViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private final List<Micropost> posts = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Micropost micropost = posts.get(position);
        final PostItemViewModel viewModel = new PostItemViewModel(micropost);
        holder.binding.setModel(viewModel);
        final AvatarViewModel avatarViewModel = new AvatarViewModel(micropost.getUser());
        holder.binding.setAvatarModel(avatarViewModel);
        holder.binding.setAvatarListener(new AvatarViewListener(avatarViewModel));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public List<Micropost> getItems() {
        return new ArrayList<>(posts);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final PostItemBinding binding;

        private ViewHolder(PostItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private static ViewHolder newInstance(ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final PostItemBinding binding = PostItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(binding);
        }
    }

    @Nullable
    public Long getFirstItemId() {
        return Observable.from(posts)
                .take(1)
                .map(Micropost::getId)
                .toBlocking()
                .singleOrDefault(null);
    }

    @Nullable
    public Long getLastItemId() {
        return Observable.from(posts)
                .takeLast(1)
                .map(Micropost::getId)
                .toBlocking()
                .singleOrDefault(null);
    }

    public boolean addAll(int location, Collection<Micropost> posts) {
        if (this.posts.addAll(location, posts)) {
            notifyItemRangeInserted(location, posts.size());
            return true;
        }
        return false;
    }
}
