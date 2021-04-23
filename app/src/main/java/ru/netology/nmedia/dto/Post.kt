package ru.netology.nmedia.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import ru.netology.nmedia.enumeration.AttachmentType

sealed class FeedItem{
    abstract val id: Long
}

data class Ad(
    override val id: Long,
    val url: String,
    val image: String,
) : FeedItem()

@Parcelize
data class Post(
    override val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: Long,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var shares: Int = 0,
    val video: String? = null,
    val ownedByMe: Boolean = false,
    val newPost: Boolean = false,
    val attachment: @RawValue Attachment? = null,
) : Parcelable, FeedItem()

data class Attachment(
    val url: String,
    val type: AttachmentType,
)