package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.view.loadCircleCrop

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onOwnPost(post: Post) {}
    fun onPhoto(post: Post) {}
    fun onShare(post: Post) {}
    fun onPlay(post: Post) {}

}

class PostsAdapter(private val onInteractionListener: OnInteractionListener) : PagingDataAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            val urlAvatar = "${BuildConfig.BASE_URL}/avatars/${post.authorAvatar}"
            val urlAttachment = "${BuildConfig.BASE_URL}/media/${post.attachment?.url}"

            authorTextView.text = post.author
            publishedTextView.text = post.published.toString()
            contentTextView.text = post.content
            likeImageView.isChecked = post.likedByMe
            likeImageView.text = Utils.numToPostfix(post.likes)
            shareImageView.text = Utils.numToPostfix(post.shares)
            avatarImageView.loadCircleCrop(urlAvatar)

            if (post.attachment != null) {
                attachmentView.visibility = View.VISIBLE
                Glide.with(attachmentView.context)
                    .load(urlAttachment)
                    .error(R.drawable.ic_baseline_person_24)
                    .timeout(30_000)
                    .into(attachmentView)
            } else {
                attachmentView.visibility = View.GONE
            }

            menuImageButton.visibility = if (post.ownedByMe) View.VISIBLE else View.INVISIBLE

            menuImageButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    // TODO: if we don't have other options, just remove dots
                    menu.setGroupVisible(R.id.owned, post.ownedByMe)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            attachmentView.setOnClickListener {
                onInteractionListener.onPhoto(post)
            }

            root.setOnClickListener {
                onInteractionListener.onOwnPost(post)
            }

            likeImageView.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            shareImageView.setOnClickListener {
                onInteractionListener.onShare(post)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}