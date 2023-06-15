package com.nguyen.coroutines2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nguyen.coroutines2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val BASE_URL = "https://jsonplaceholder.typicode.com"

data class Post(val id: Int, val userId: Int, val title: String)
data class User(val id: Int, val name: String, val email: String)

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNetwork.setOnClickListener {
            doApiRequests()
        }
    }

    private fun doApiRequests() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val blogService = retrofit.create(BlogService::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            Log.i(TAG, "doApiRequests coroutine thread: ${Thread.currentThread().name}")
            val post = blogService.getPost(1)
            val user = blogService.getUser(post.userId)
            val posts = blogService.getPostsByUser(user.id)
            binding.textView.text = "User ${user.name} made ${posts.size} posts"
        }
    }
}