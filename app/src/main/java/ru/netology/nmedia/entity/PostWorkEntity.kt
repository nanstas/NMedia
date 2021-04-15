package ru.netology.nmedia.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostWorkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: Long,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var countShares: Int = 0,
    val video: String? = null,
    val ownedByMe: Boolean = false,
    val newPost: Boolean = false,
    @Embedded
    var attachment: AttachmentEmbeddable?,
    var uri: String? = null,
) {
    fun toDto(): Post = Post(id, authorId, author, authorAvatar, content, published, likedByMe, likes, countShares, video, ownedByMe, newPost, attachment?.toDto())

    companion object {
        fun fromDto(dto: Post): PostWorkEntity =
            PostWorkEntity(dto.id, dto.authorId, dto.author, dto.authorAvatar, dto.content, dto.published, dto.likedByMe, dto.likes, dto.shares, dto.video, dto.ownedByMe, dto.newPost, AttachmentEmbeddable.fromDto(dto.attachment))
    }
}