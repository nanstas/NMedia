package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this, { post ->
            with(binding) {
                authorTextView.text = post.author
                publishedTextView.text = post.published
                contentTextView.text = post.content
                likeImageView.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_like_active_24 else R.drawable.ic_baseline_like_border_24
                )
                likeCountsTextView.text = numToPostfix(post.countLikes)
                shareCountsTextView.text = numToPostfix(post.countShares)
            }
        })

        binding.likeImageView.setOnClickListener {
            viewModel.like()
        }

        binding.shareImageView.setOnClickListener {
            viewModel.share()
        }
    }
}

fun numToPostfix(num: Int): String = when (num) {
    in 0..999 -> num.toString()
    in 1_000..9_999 ->  if (num.toString()[1] == '0') "${num.toString()[0]}K" else "${num.toString()[0]},${num.toString()[1]}K"
    in 10_000..999_999 -> "${num.toString().dropLast(3)}K"
    else -> if (num.toString()[1] == '0') "${num.toString()[0]}M" else "${num.toString()[0]},${num.toString()[1]}M"
}