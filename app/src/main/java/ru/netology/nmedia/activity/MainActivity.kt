package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val newPostRequestCode = 1
    val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
//                groupCancel.visibility = View.VISIBLE
                val intent = Intent(this@MainActivity, NewPostActivity::class.java)
                startActivityForResult(intent, newPostRequestCode)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }
        })

        binding.listRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this, { post ->
            if (post.id == 0L) {
                return@observe
            }
//            with(binding.contentEditText) {
//                requestFocus()
//                setText(post.content)
//            }
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPostActivity::class.java)
            startActivityForResult(intent, newPostRequestCode)
        }

//        binding.saveImageButton.setOnClickListener {
//            with(binding.contentEditText) {
//                if (TextUtils.isEmpty(text)) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        context.getString(R.string.error_empty_content),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//                groupCancel.visibility = View.INVISIBLE
//                setText("")
//                clearFocus()
//                Utils.hideKeyboard(this)
//            }
//        }
//
//        binding.cancelImageButton.setOnClickListener {
//            with(binding.contentEditText) {
//                groupCancel.visibility = View.INVISIBLE
//                setText("")
//                clearFocus()
//                Utils.hideKeyboard(this)
//            }
//        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            newPostRequestCode -> {
                if (resultCode != Activity.RESULT_OK) {
                    return
                }

                data?.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    viewModel.changeContent(it)
                    viewModel.save()
                }
            }
        }
    }
}