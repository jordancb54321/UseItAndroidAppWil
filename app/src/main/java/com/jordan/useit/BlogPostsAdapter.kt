package com.jordan.useit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordan.useit.repository.data.Post

class BlogPostsAdapter(
    private val blogPosts: List<Post>,
    private val onPostClick: (Post) -> Unit
) : RecyclerView.Adapter<BlogPostsAdapter.BlogPostViewHolder>() {

    inner class BlogPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val author: TextView = itemView.findViewById(R.id.authorTextView)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnailImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_blog, parent, false)
        return BlogPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogPostViewHolder, position: Int) {
        val post = blogPosts[position]
        holder.title.text = post.title
        holder.author.text = post.author.displayName
        Glide.with(holder.itemView.context).load(post.images?.firstOrNull()?.url).into(holder.thumbnail)

        holder.itemView.setOnClickListener { onPostClick(post) }
    }

    override fun getItemCount(): Int = blogPosts.size
}
