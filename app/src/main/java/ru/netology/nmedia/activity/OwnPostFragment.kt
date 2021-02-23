package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentOwnPostBinding
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.viewmodel.PostViewModel

class OwnPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentOwnPostBinding.inflate(
            inflater,
            container,
            false
        )

        val post: Post = arguments?.get("post") as Post
            post.let { post ->
                binding.apply {
                    authorTextView.text = post.author
                    publishedTextView.text = post.published
                    contentTextView.text = post.content
                    likeImageView.isChecked = post.likedByMe
                    likeImageView.text = Utils.numToPostfix(post.likes)
                    shareImageView.text = Utils.numToPostfix(post.shares)
                    avatarImageView.setImageResource(R.drawable.ic_netology)

                    val url = "http://10.0.2.2:9999/avatars/${post.authorAvatar}"
                    Glide.with(avatarImageView)
                        .load(url)
                        .error(R.drawable.ic_baseline_person_24)
                        .circleCrop()
                        .timeout(10_000)
                        .into(avatarImageView)

                    menuImageButton.setOnClickListener {
                        PopupMenu(it.context, it).apply {
                            inflate(R.menu.options_post)
                            setOnMenuItemClickListener { item ->
                                when (item.itemId) {
                                    R.id.remove -> {
                                        viewModel.removeById(post.id)
                                        findNavController().navigateUp()
                                        true
                                    }
                                    R.id.edit -> {
                                        viewModel.edit(post)
                                        findNavController().navigate(R.id.action_ownPostFragment_to_editPostFragment,
                                            Bundle().apply {
                                                textArg = post.content
                                            })
                                        true
                                    }
                                    else -> false
                                }
                            }
                        }.show()
                    }
                    likeImageView.setOnClickListener {
                        if (!post.likedByMe) {
                            viewModel.likeById(post.id)
                        } else {
                            viewModel.disLikeById(post.id)
                        }
                        // проброс количества лайков из FeedFragment!?
                    }
                }
            }
        return binding.root
    }
}