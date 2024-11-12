package com.jordan.useit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordan.useit.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize RecyclerView with a LinearLayoutManager and Adapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)
        homeAdapter = HomeAdapter(getHomeContent())
        binding.homeRecyclerView.adapter = homeAdapter

        return binding.root
    }

    // Sample hardcoded data for the home screen
    private fun getHomeContent(): List<HomeContent> {
        return listOf(
            HomeContent(
                "eThekwini Waste Material Recovery",
                "Waste material recovery industry development cluster.\n\n" +
                        "Diverting waste from landfill by creating employment opportunities in the green economy. " +
                        "Building successful future entrepreneurs.",
                "https://use-it.co.za/assets/img/projects/goodeconomy-usei-it.png"
            ),
            HomeContent(
                "Our History",
                "USE-IT Non-Profit Organisation\n\n" +
                        "USE-IT has constructed a world-class facility in Hammarsdale that houses 10 incubators for training and " +
                        "skills development to develop SMEs within the upcycling and recycling industry.\n\n" +
                        "Additional sources of funding for the operation of the site are obtained through tenancy on site and partnerships " +
                        "created with the aim of ensuring sustainable enterprises to operate under the waste license held by USE-IT.",
                "https://use-it.co.za/assets/img/about/contact-us.jpg"
            ),
            HomeContent(
                "Our Mandate",
                "USE-IT has been established as a non-profit organisation to research and develop waste beneficiation technologies " +
                        "with the aim of diverting waste from landfill and creating jobs in the green economy. Our primary funding and " +
                        "mandate is in the eThekwini area in association with the Economic Development Unit.\n\n" +
                        "USE-IT is a special purpose vehicle, designed to act as a cluster for the waste materials recovery industry in " +
                        "eThekwini Municipality. In terms of Governance, USE-IT is overseen by a board of directors made up of members " +
                        "from a diverse range of backgrounds in waste material recovery, business, and environmental management.",
                "https://use-it.co.za/assets/img/about/new-img.png"
            ),
            HomeContent(
                "Our Vision",
                "USE-IT is the leading authority on driving economic opportunities in waste diversion and beneficiation to " +
                        "support the circular economy.",
                "https://use-it.co.za/assets/img/hero/hero-smme.jpeg"
            ),
            HomeContent(
                "Our Strategy",
                "Developing partnerships to grow and succeed within the green economy.",
                "https://use-it.co.za/assets/img/hero/Plinth%20for%20new%20JoJo%20with%20KEY%20Brick%20(1)%20(1).jpg"
            ),
            HomeContent(
                "Our Goals",
                "EMPOWERING PEOPLE\nSkills development and incubation.\n\n" +
                        "DO WHAT IS RIGHT\nTransparent, credible representation.\n\n" +
                        "GROUND TRUTH SOLUTIONS\nProvide solutions based on real-life scenarios.\n\n" +
                        "LEARN AND COLLABORATE\nEffective learning strategies and collaboration.",
                "https://use-it.co.za/assets/img/hero/7.jpg"
            )
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
