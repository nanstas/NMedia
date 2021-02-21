package ru.netology.nmedia.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.model.Post

private const val BASE_URL = "${BuildConfig.BASE_URL}/api/slow/"

private val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val okhttp = OkHttpClient.Builder()
    .addInterceptor(logging)
//    .addInterceptor(PostInterceptor())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okhttp)
    .build()

interface PostsApiService {
    @GET("posts")
    fun getAll(): Response<List<Post>>

    @GET("posts/{id}")
    fun getById(@Path("id") id: Long): Response<Post>

    @POST("posts")
    fun save(@Body post: Post): Response<Post>

    @DELETE("posts/{id}")
    fun removeById(@Path("id") id: Long): Response<Unit>

    @POST("posts/{id}/likes")
    fun likeById(@Path("id") id: Long): Response<Post>

    @DELETE("posts/{id}/likes")
    fun dislikeById(@Path("id") id: Long): Response<Post>
}

object PostsApi {
    val retrofitService: PostsApiService by lazy {
        retrofit.create(PostsApiService::class.java)
    }
}