package ru.netology.nmedia.repository

import ru.netology.nmedia.model.ApiError
import ru.netology.nmedia.model.Post

interface PostRepository {
    fun getAllAsync(callback: Callback<List<Post>>)
    fun save(post: Post, callback: Callback<Post>)
    fun removeById(id: Long, callback: Callback<Unit>)
    fun likeById(id: Long, callback: Callback<Post>)
    fun dislikeById(id: Long, callback: Callback<Post>)
    fun shareById(id: Long)
    fun getPost(id: Long): Post

    interface Callback<T> {
        fun onSuccess(posts: T) {}
        fun onError(e: ApiError) {}
    }
}