package com.jordan.useit.repository.data

data class Post(
    val kind: String,
    val id: String,
    val title: String,
    val content: String,
    val images: List<PostImage>?,
    val author: PostAuthor,
    val url: String,
)
