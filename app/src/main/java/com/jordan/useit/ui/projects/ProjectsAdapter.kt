package com.jordan.useit.ui.projects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jordan.useit.R
import com.jordan.useit.databinding.ItemProjectBinding

class ProjectsAdapter : ListAdapter<Project, ProjectsAdapter.ProjectViewHolder>(ProjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProjectViewHolder(private val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(project: Project) {
            binding.titleTextView.text = project.title
            binding.descriptionTextView.text = project.description

            // Check if imageUrl is provided
            if (!project.imageUrl.isNullOrEmpty()) {
                // Load the image with Glide
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.useiticon) // Placeholder image if loading takes time
                    .error(R.drawable.baseline_info_24)             // Error image if URL fails

                Glide.with(binding.root.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(project.imageUrl)
                    .into(binding.projectImageView)

                // Make sure the ImageView is visible
                binding.projectImageView.visibility = View.VISIBLE
            } else {
                // Hide the ImageView or set a default placeholder if no image is provided
                binding.projectImageView.visibility = View.GONE
            }
        }
    }
}

class ProjectDiffCallback : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }
}
