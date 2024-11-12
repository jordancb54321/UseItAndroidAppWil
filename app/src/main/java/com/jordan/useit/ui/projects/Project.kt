package com.jordan.useit.ui.projects

data class Project(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null // Made optional with a default value of null
)
