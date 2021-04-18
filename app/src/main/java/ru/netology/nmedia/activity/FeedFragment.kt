package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.AuthViewModel
import ru.netology.nmedia.viewmodel.PostViewModel

@AndroidEntryPoint
class FeedFragment : Fragment() {
    @ExperimentalCoroutinesApi
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    private val viewModelAuth: AuthViewModel by viewModels(ownerProducer = ::requireParentFragment)
    private var countNewPost = 0

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                val bundle = Bundle().apply {
                    putString("content", post.content)
                    putLong("postId", post.id)
                }
                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, bundle)
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onLike(post: Post) {
                if (!viewModelAuth.authenticated) {
                    findNavController().navigate(R.id.action_feedFragment_to_authFragment)
                    return
                }
                if (!post.likedByMe) {
                    viewModel.likeById(post.id)

                } else {
                    viewModel.disLikeById(post.id)
                }
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

            override fun onOwnPost(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_ownPostFragment,
                    Bundle().apply {
                        putParcelable("post", post)
                    })
            }

            override fun onPhoto(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_photoFragment,
                    Bundle().apply {
                        putParcelable("post", post)
                    })
            }
        })
        binding.listRecyclerView.adapter = adapter

        lifecycleScope.launchWhenCreated {
            viewModel.dataPaging.collectLatest(adapter::submitData)
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { state ->
                binding.swiperefresh.isRefreshing =
                    state.refresh is LoadState.Loading ||
                            state.prepend is LoadState.Loading ||
                            state.append is LoadState.Loading
            }
        }

        viewModel.dataState.observe(viewLifecycleOwner, { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        })

        viewModel.newerCount.observe(viewLifecycleOwner) { state ->
            println(state)
            if (state > countNewPost) {
                binding.extendedFab.visibility = View.VISIBLE
            }
            binding.extendedFab.setOnClickListener {
                viewModel.loadPosts()
                binding.extendedFab.visibility = View.GONE
                binding.listRecyclerView.smoothScrollToPosition(0)
                countNewPost = 0
            }
        }

        binding.swiperefresh.setOnRefreshListener(adapter::refresh)

        binding.fab.setOnClickListener {
            if (!viewModelAuth.authenticated) {
                findNavController().navigate(R.id.action_feedFragment_to_authFragment)
                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_feedFragment_to_editPostFragment)
        }

        return binding.root
    }
}