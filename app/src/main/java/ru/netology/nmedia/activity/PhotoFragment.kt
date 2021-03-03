package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentOwnPostBinding
import ru.netology.nmedia.databinding.FragmentPhotoBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.view.loadCircleCrop
import ru.netology.nmedia.viewmodel.PostViewModel

class PhotoFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentPhotoBinding.inflate(
            inflater,
            container,
            false
        )

        val post: Post = arguments?.get("post") as Post
        post.let { post ->
            binding.apply {
                val urlAttachment = "${BuildConfig.BASE_URL}/media/${post.attachment?.url}"
                if (post.attachment != null) {
                    photoView.visibility = View.VISIBLE
                    Glide.with(photoView.context)
                        .load(urlAttachment)
                        .error(R.drawable.ic_baseline_person_24)
                        .timeout(30_000)
                        .into(photoView)
                } else {
                    photoView.visibility = View.GONE
                }
            }
        }
        return binding.root
    }
}