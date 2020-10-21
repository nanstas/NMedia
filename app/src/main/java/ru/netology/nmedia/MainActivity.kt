package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            countLikes = 1_999_999,
            countShares = 999
        )

        with(binding) {
            authorTextView.text = post.author
            publishedTextView.text = post.published
            contentTextView.text = post.content
            likeCountsTextView.text = numToPostfix(post.countLikes)
            shareCountsTextView.text = numToPostfix(post.countShares)

            if (post.likedByMe) {
                likeImageView.setImageResource(R.drawable.ic_baseline_like_active_24)
            }

            likeImageView.setOnClickListener {
                post.likedByMe = !post.likedByMe

                if (post.likedByMe) {
                    likeImageView.setImageResource(R.drawable.ic_baseline_like_active_24)
                    likeCountsTextView.text = numToPostfix(++post.countLikes)
                } else {
                    likeImageView.setImageResource(R.drawable.ic_baseline_like_border_24)
                    likeCountsTextView.text = numToPostfix(--post.countLikes)
                }
            }

            shareImageView.setOnClickListener {
                shareCountsTextView.text = numToPostfix(++post.countShares)
            }
        }
    }
}

fun numToPostfix(num: Int): String = when (num) {
    in 0..999 -> num.toString()
    in 1_000..9_999 ->  if (num.toString()[1] == '0') "${num.toString()[0]}K" else "${num.toString()[0]},${num.toString()[1]}K"
    in 10_000..999_999 -> "${num.toString().dropLast(3)}K"
    else -> if (num.toString()[1] == '0') "${num.toString()[0]}M" else "${num.toString()[0]},${num.toString()[1]}M"
}