package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var countLikes: Int = 0,
    var countShares: Int = 0,
    val video: String? = null
)