package com.jordan.useit.ui.contact

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.jordan.useit.databinding.FragmentContactBinding

class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    // Contact information
    private val whatsappNumber = "+1234567890"
    private val emailAddress = "example@example.com"
    private val phoneNumber = "+0987654321"
    private val mapUrl = "https://www.google.com/maps"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        setupClickListeners()
        setupWebView()
        return binding.root
    }

    private fun setupClickListeners() {
        // Handle WhatsApp button click
        binding.buttonWhatsapp.setOnClickListener {
            showContactInfo("WhatsApp", whatsappNumber)
        }

        // Handle Email button click
        binding.buttonEmail.setOnClickListener {
            showContactInfo("Email", emailAddress)
        }

        // Handle Phone button click
        binding.buttonPhone.setOnClickListener {
            showContactInfo("Phone", phoneNumber)
        }
    }

    private fun showContactInfo(title: String, info: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(info)
            .setPositiveButton("Copy") { dialog, _ ->
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(title, info)
                clipboard.setPrimaryClip(clip)
                dialog.dismiss()
            }
            .setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupWebView() {
        // Initialize WebView and load map
        val webView = binding.webviewMap
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(mapUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
