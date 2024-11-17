package com.jordan.useit.ui.media

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jordan.useit.databinding.FragmentMediaBinding

class MediaFragment : Fragment() {
    private var _binding: FragmentMediaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Annual Reports Click Listeners
        binding.report2023.setOnClickListener {
            openUrl("https://use-it.co.za/assets/img/Annual%20Report%202022%202023%20Final.pdf")
        }

        binding.report2022.setOnClickListener {
            openUrl("https://example.com/2022_report.pdf")
        }

        binding.report2021.setOnClickListener {
            openUrl("https://example.com/2021_report.pdf")
        }

        binding.report2020.setOnClickListener {
            openUrl("https://example.com/2020_report.pdf")
        }

        binding.report2019.setOnClickListener {
            openUrl("https://example.com/2019_report.pdf")
        }

        binding.report2018.setOnClickListener {
            openUrl("https://example.com/2018_report.pdf")
        }

        binding.report2017.setOnClickListener {
            openUrl("https://example.com/2017_report.pdf")
        }

        // Green Environment Links Click Listeners
        binding.greenLink1.setOnClickListener {
            openUrl("https://156c9157-d9cf-4d98-a423-ffd1268d3ad9.filesusr.com")
        }

        binding.greenLink2.setOnClickListener {
            openUrl("https://156c9157-d9cf-4d98-a423-ffd1268d3ad9.filesusr.com")
        }

        binding.greenLink3.setOnClickListener {
            openUrl("https://156c9157-d9cf-4d98-a423-ffd1268d3ad9.filesusr.com")
        }

        binding.greenLink4.setOnClickListener {
            openUrl("https://156c9157-d9cf-4d98-a423-ffd1268d3ad9.filesusr.com")
        }

        binding.greenLink5.setOnClickListener {
            openUrl("https://156c9157-d9cf-4d98-a423-ffd1268d3ad9.filesusr.com")
        }

        binding.greenLink6.setOnClickListener {
            openUrl("https://www.citizen.co.za/highway-mail/news-headlines/advertorial/2023/09/06/key-bricks-unlocking-the-future-of-building-on-the-african-continent/")
        }

        binding.greenLink7.setOnClickListener {
            openUrl("https://www.citizen.co.za/highway-mail/news-headlines/local-news/2024/01/20/recycling-centre-blossoms-into-a-bright-2024/")
        }

        // Development Strategy Links Click Listeners
        binding.developmentLink1.setOnClickListener {
            openUrl("https://156c9157-d9cf-4d98-a423-ffd1268d3ad9.filesusr.com")
        }

        binding.developmentLink2.setOnClickListener {
            openUrl("https://156c9157-d9cf-4d98-a423-ffd1268d3ad9.filesusr.com")
        }

        binding.developmentLink3.setOnClickListener {
            openUrl("https://156c9157-d9cf-4d98-a423-ffd1268d3ad9.filesusr.com")
        }


    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
