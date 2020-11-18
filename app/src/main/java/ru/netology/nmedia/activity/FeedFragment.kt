package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    companion object {
        private const val NEW_POST_REQUEST_CODE = 1
        private const val EDIT_POST_REQUEST_CODE = 2
    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)

//                Intent(Intent.ACTION_SEND)
//                    .putExtra(Intent.EXTRA_TEXT, post.content)
//                    .putExtra("REQUEST_CODE", EDIT_POST_REQUEST_CODE)
//                    .setType("text/plain")
//                    .setClass(this@FeedFragment, EditPostFragment::class.java)
//                    .also {
//                        if (it.resolveActivity(packageManager) == null) {
//                            Toast.makeText(this@FeedFragment, "app not found", Toast.LENGTH_SHORT)
//                                .show()
//                        } else {
//                            startActivityForResult(it, EDIT_POST_REQUEST_CODE)
//                        }
//                    }
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onPlay(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }
        })

        binding.listRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_feedFragment_to_editPostFragment) }


//            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//                super.onActivityResult(requestCode, resultCode, data)
//                when (requestCode) {
//                    NEW_POST_REQUEST_CODE -> {
//                        if (resultCode != Activity.RESULT_OK) {
//                            return
//                        }
//
//                        data?.getStringExtra(Intent.EXTRA_TEXT)?.let {
//                            viewModel.changeContent(it)
//                            viewModel.save()
//                        }
//                    }
//
//                    EDIT_POST_REQUEST_CODE -> {
//                        if (resultCode != Activity.RESULT_OK) {
//                            return
//                        }
//
//                        data?.getStringExtra(Intent.EXTRA_TEXT)?.let {
//                            viewModel.changeContent(it)
//                            viewModel.save()
//                        }
//                    }
//                }
//            }

        return binding.root
    }
}