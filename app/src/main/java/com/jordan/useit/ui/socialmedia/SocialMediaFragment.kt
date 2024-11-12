package com.jordan.useit.ui.socialmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.jordan.useit.databinding.FragmentSocialMediaBinding

class SocialMediaFragment : Fragment() {

    private var _binding: FragmentSocialMediaBinding? = null
    private val binding get() = _binding!!

    private val facebookUrl = "https://www.facebook.com/useitkzn"
    private val whatsappUrl = "https://api.whatsapp.com/send?phone=0670300360"
    private val linkedinUrl = "https://www.linkedin.com/company/use-it-ethekwini-waste-materials-recovery-industry-development-cluster"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSocialMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.facebookButton.setOnClickListener {
            openUrl(facebookUrl)
        }

        binding.whatsappButton.setOnClickListener {
            openUrl(whatsappUrl)
        }

        binding.linkedinButton.setOnClickListener {
            openUrl(linkedinUrl)
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
