package com.jordan.useit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordan.useit.databinding.ActivityMainBinding
import com.jordan.useit.repository.Blogger
import com.jordan.useit.repository.data.Post
import com.jordan.useit.repository.services.IBlogService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MainActivity(private val blogService: IBlogService? = null) : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val scope = lifecycleScope
    private val blogPosts = mutableListOf<Post>() // List to store blog posts
    private lateinit var adapter: BlogPostsAdapter // RecyclerView adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        scope.launch(Dispatchers.IO) {
            getBlogs()
        }
    }

    private fun setupRecyclerView() {
        adapter = BlogPostsAdapter(blogPosts) { post ->
            // Navigate to details activity
            val intent = Intent(this, BlogDetailsActivity::class.java)
            intent.putExtra("post_url", post.url)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    suspend fun getBlogs() {
        @Suppress("UNUSED_VARIABLE")
        val service = blogService ?: Blogger.blogService

        val blogsCall = Blogger.blogService.listPosts()!!
        val blogsResponse = blogsCall.awaitResponse()
        val blogsStatus = blogsResponse.code()

        Log.d("BLOGGER", "$blogsStatus")
        Log.d("BLOGGER", "${blogsResponse.body()}")
    }
}
