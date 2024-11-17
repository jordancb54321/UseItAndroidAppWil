package com.jordan.useit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jordan.useit.databinding.ActivityMainBinding
import com.jordan.useit.repository.Blogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val scope = lifecycleScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scope.launch(Dispatchers.IO) {
            getBlogs()
        }
    }

    suspend fun getBlogs() {
        val blogsCall = Blogger.blogService.listPosts()!!
        val blogsResponse = blogsCall.awaitResponse()
        val blogsStatus = blogsResponse.code()

        Log.d("BLOGGER", "$blogsStatus")
        Log.d("BLOGGER", "${blogsResponse.body()}")
    }
}