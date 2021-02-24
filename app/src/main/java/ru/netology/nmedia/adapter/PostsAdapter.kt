package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.view.loadCircleCrop

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onOwnPost(post: Post) {}
    fun onShare(post: Post) {}
    fun onPlay(post: Post) {}

}

class PostsAdapter(private val onInteractionListener: OnInteractionListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            authorTextView.text = post.author
            publishedTextView.text = post.published
            contentTextView.text = post.content
            likeImageView.isChecked = post.likedByMe
            likeImageView.text = Utils.numToPostfix(post.likes)
            shareImageView.text = Utils.numToPostfix(post.shares)
            avatarImageView.loadCircleCrop("${BuildConfig.BASE_URL}/avatars/${post.authorAvatar}")

//            if (post.attachment != null) {
//                attachmentImageView.visibility = View.VISIBLE
//                Glide.with(attachmentImageView.context)
//                    .load(urlAttachment)
//                    .error(R.drawable.ic_baseline_person_24)
//                    .timeout(30_000)
//                    .into(attachmentImageView)
//            } else {
//                attachmentImageView.visibility = View.GONE
//            }

            root.setOnClickListener {
                onInteractionListener.onOwnPost(post)
            }

            menuImageButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
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