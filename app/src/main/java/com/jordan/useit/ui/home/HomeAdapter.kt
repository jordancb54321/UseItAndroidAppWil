package com.jordan.useit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordan.useit.R
import com.jordan.useit.databinding.ItemHomeCardBinding

class HomeAdapter(private val contentList: List<HomeContent>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(contentList[position])
    }

    override fun getItemCount(): Int = contentList.size

    inner class HomeViewHolder(private val binding: ItemHomeCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: HomeContent) {
            binding.titleTextView.text = content.title
            binding.descriptionTextView.text = content.description

            // Load image using Glide, handle null imageUrl by hiding the ImageView
            if (!content.imageUrl.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(content.imageUrl)
                    .into(binding.imageView)
                binding.imageView.visibility = View.VISIBLE
            } else {
                binding.imageView.visibility = View.GONE
            }

            // Set click listener to navigate to DetailFragment
            binding.root.setOnClickListener {
                val bundle = bundleOf(
                    "title" to content.title,
                    "description" to content.description,
                    "imageUrl" to content.imageUrl
                )
                binding.root.findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        }
    }
}
