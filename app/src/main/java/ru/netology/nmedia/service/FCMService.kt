//package ru.netology.nmedia.service
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//import com.google.gson.Gson
//import ru.netology.nmedia.R
//import kotlin.random.Random
//
//
//class FCMService : FirebaseMessagingService() {
//    private val action = "action"
//    private val content = "content"
//    private val channelId = "remote"
//    private val gson = Gson()
//
//    override fun onCreate() {
//        super.onCreate()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.channel_remote_name)
//            val descriptionText = getString(R.string.channel_remote_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, name, importance).apply {
//                description = descriptionText
//            }
//            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
//        }
//    }
//
//    override fun onMessageReceived(message: RemoteMessage) {
//        message.data[action]?.let {
//            when (it) {
//                "LIKE" -> handleLike(gson.fromJson(message.data[content], Like::class.java))
//                "NEWPOST" -> handleNewPost(gson.fromJson(message.data[content], NewPost::class.java))
//                else -> handleOther()
//            }
//        }
//    }
//
//    override fun onNewToken(token: String) {
//        println(token)
//    }
//
//    private fun handleLike(content: Like) {
//        val notification = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle(
//                getString(
//                    R.string.notification_user_liked,
//                    content.userName,
//                    content.postAuthor,
//                )
//            )
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .build()
//
//        NotificationManagerCompat.from(this)
//            .notify(Random.nextInt(100_000), notification)
//    }
//
//    private fun handleNewPost(content: NewPost) {
//        val notification = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle(
//                getString(
//                    R.string.notification_post_published,
//                    content.userName
//                )
//            )
//            .setStyle(NotificationCompat.BigTextStyle()
//                .bigText(
//                    content.text
//                )
//            )
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .build()
//
//        NotificationManagerCompat.from(this)
//            .notify(Random.nextInt(100_000), notification)
//    }
//
//    private fun handleOther() {
//        val notification = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle(
//                getString(
//                    R.string.notification_other
//                )
//            )
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .build()
//
//        NotificationManagerCompat.from(this)
//            .notify(Random.nextInt(100_000), notification)
//    }
//}
//
//data class Like(
//    val userId: Long,
//    val userName: String,
//    val postId: Long,
//    val postAuthor: String,
//)
//
//data class NewPost(
//    val userId: Long,
//    val userName: String,
//    val postId: Long,
//    val text: String
//)
package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.netology.nmedia.auth.AuthState
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
//        println(message.data["content"])

        val id = AppAuth.getInstance().authStateFlow.value.id
        val content = gson.fromJson(message.data[content], Handler::class.java)

        when (content.recipientId) {
            null, id -> handler(content)
            else -> AppAuth.getInstance().sendPushToken()
        }
    }

    override fun onNewToken(token: String) {
        AppAuth.getInstance().sendPushToken(token)
    }

    private fun handler(content: Handler) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_for_user,
                    content.recipientId.toString(),
                    content.content,
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    data class Handler(
        val recipientId: Long?,
        val content: String
    )
}