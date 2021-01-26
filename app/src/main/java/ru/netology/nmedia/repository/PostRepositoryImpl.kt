package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.model.Post


class PostRepositoryImpl : PostRepository {
    override fun getAllAsync(callback: PostRepository.GetAllCallback) {
        PostsApi.retrofitService.getAll()
            .enqueue(object : Callback<List<Post>> {
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful) {
                        callback.onSuccess(response.body().orEmpty())
                    } else {
                        callback.onError(RuntimeException(response.message()))
                    }
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }
            })
    }

    override fun getAll(): List<Post> {
        TODO("Not yet implemented")
    }

    override fun likeById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun unLikeById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun save(post: Post) {
        TODO("Not yet implemented")
    }

    override fun removeById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun shareById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getPost(id: Long): Post {
        TODO("Not yet implemented")
    }

}