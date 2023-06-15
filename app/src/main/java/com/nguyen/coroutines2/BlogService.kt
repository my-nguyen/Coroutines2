package com.nguyen.coroutines2

import retrofit2.http.GET
import retrofit2.http.Path

interface BlogService {
    @GET("/posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

    @GET("/users/{id}/posts")
    suspend fun getPostsByUser(@Path("id") userId: Int): List<Post>
}