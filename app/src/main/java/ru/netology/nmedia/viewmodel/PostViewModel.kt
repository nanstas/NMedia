package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.model.ApiError
import ru.netology.nmedia.model.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.*
import ru.netology.nmedia.utils.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    authorAvatar = "",
    likedByMe = false,
    likes = 0,
    published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    private val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated
    private val _postCreateError = SingleLiveEvent<ApiError>()
    val postCreateError: LiveData<ApiError>
        get() = _postCreateError

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                _data.value = FeedModel(posts = posts, empty = posts.isEmpty())
            }

            override fun onError(e: ApiError) {
                _data.value = FeedModel(errorVisible = true, error = e)
            }
        })
    }

    fun save() {
        edited.value?.let {
            repository.save(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                    _postCreated.value = Unit
                }

                override fun onError(e: ApiError) {
                    _postCreateError.value = e
                }
            })
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
        loadPosts()
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        repository.likeById(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(posts: Post) {
                _postCreated.value = Unit
                loadPosts()
            }

            override fun onError(e: ApiError) {
                _postCreateError.value = e
            }
        })
    }

    fun disLikeById(id: Long) {
        repository.dislikeById(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(posts: Post) {
                _postCreated.value = Unit
                loadPosts()
            }

            override fun onError(e: ApiError) {
                _postCreateError.value = e
            }
        })
    }

    fun removeById(id: Long) {
        repository.removeById(id, object : PostRepository.Callback<Unit> {
            override fun onSuccess(posts: Unit) {
                loadPosts()
            }

            override fun onError(e: ApiError) {
                _postCreateError.value = e
            }
        })
    }

    fun shareById(id: Long) = repository.shareById(id)
    fun getPost(id: Long) = repository.getPost(id)
}