package com.jordan.useit.ui.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordan.useit.databinding.FragmentProjectsBinding

class ProjectsFragment : Fragment() {

    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

    private lateinit var projectsAdapter: ProjectsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        binding.projectsRecyclerView.layoutManager = LinearLayoutManager(context)
        projectsAdapter = ProjectsAdapter()
        binding.projectsRecyclerView.adapter = projectsAdapter

        // Load hardcoded projects
        loadProjects()
    }

    private fun loadProjects() {
        // Define a hardcoded list of projects
        val projectList = listOf(
            Project(
                id = "1",
                title = "Educational Toy Project",
                description = "Cultivating great partnerships is one of the best strategies for any business. Nokulunga Ngcobo and Cebo Cele have been a great example of this value, a testament to the power of partnerships, persistence, innovation and the belief that starting small can lead into something extraordinary.  Home Deco Tech specializes in manufacturing ECD toys, using waste wood sponsored by CHEP.  The importance of motor skill development in early childhood development is instilled into the fabric of this project.  Home Deco Tech also has a showroom on site to display their custom-made furniture, which is another income stream to the project.",
                imageUrl = "https://use-it.co.za/assets/img/projects/edutoy-9.jpg"
            ),
            Project(
                id = "2",
                title = "Ikhwezi Bakery",
                description = "Serving both corporate and local consumers, Ikhwezi Bakery has become a specialist in delicious pasties and treats. The bakery is owned by Phindile Zungu who is very passionate about her business. Just like all start-ups Ikhwezi Bakery also faced challenges that tested our resilience with Covid19, but their resilience became the seeds in which the business roots have been built on. The bakery produces products such as bread, birthday cakes, muffins, cupcakes, doughnuts, cream cakes and is a bread supplier to a number of retailers.",
                imageUrl = "https://use-it.co.za/assets/img/projects/bakery3.jpg"
            ),
            Project(
                id = "3",
                title = "Owethu Sewing Project",
                description = "Born and bred in Hammasdale, Owethu Umqhele is a co-operative made up of a group of women from our community who had a vision that required nurturing. This vision fueled their determination to turn a small sewing business into a manufacturing powerhouse. The co- operative makes products such as umbrellas, school bags, graduation gowns, pillowcases and waste collection bags. With their unique style and quality fabrics, Owethu Umqhele now supplies its products to international clients.",
                imageUrl = "https://use-it.co.za/assets/img/projects/sewing-2.jpg"
            ),
            Project(
                id = "4",
                title = "Clothing 4 Repurpose",
                description = "Crafting has been one of our key projects in our drive for upcycling. When learnership opportunities came through our sponsors, Nkanyiso Ntanzi and Noxolo Madela were the first to grab this opportunities with both hands and have been involved in crafting fashion products using waste. With their vision to redefine waste, celebrate creativity, and fashion a world where every discarded item is an opportunity for beauty and purpose, this partnership, armed with creativity and a passion for sustainability, have seen potential where others see only trash. Over the next years we hope the Clothing Repurpose project will grow from strength.",
                imageUrl = "https://use-it.co.za/assets/img/projects/clothing.jpg"
            )
            // Add more projects as needed
        )

        // Submit the list to the adapter
        projectsAdapter.submitList(projectList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
