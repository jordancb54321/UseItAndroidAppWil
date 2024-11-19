package com.jordan.useit.ui.blogs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jordan.useit.databinding.ActivityBlogDetailsBinding

class BlogDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlogDetailsBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val postContent = intent.getStringExtra("post_content")

        if (postContent != null) {
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.pluginState = WebSettings.PluginState.ON

            binding.webView.loadDataWithBaseURL(null, postContent, "text/html", "utf-8", null)
        } else {
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
        }
    }
}
