package ru.netology.nmedia.model

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val loading: Boolean = false,
    val errorVisible: Boolean = false,
    val error: ApiError? = null,
    val empty: Boolean = false,
    val refreshing: Boolean = false,
)