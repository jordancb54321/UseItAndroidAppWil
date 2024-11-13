package com.jordan.useit.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jordan.useit.R
import com.jordan.useit.databinding.FragmentOurStoryBinding

class OurStoryFragment : Fragment() {

    private var _binding: FragmentOurStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOurStoryBinding.inflate(inflater, container, false)

        setupGoals()
        setupBoardMembers()

        return binding.root
    }

    private fun setupGoals() {
        val goals = listOf(
            Goal("EMPOWERING PEOPLE", "Skills development Incubation"),
            Goal("DO WHAT IS RIGHT", "Transparent, credible represent"),
            Goal("GROUND TRUTH SOLUTIONS", "Provide solutions based on the real life scenarios"),
            Goal("LEARN AND COLLABORATE", "Effective learning strategies and collaboration")
        )

        goals.forEachIndexed { index, goal ->
            val goalView = when (index) {
                0 -> binding.goals.goalEmpowering
                1 -> binding.goals.goalRight
                2 -> binding.goals.goalSolutions
                3 -> binding.goals.goalLearn
                else -> null
            }

            goalView?.apply {
                goalTitle.text = goal.title
                goalDescription.text = goal.description
            }
        }
    }

    private fun setupBoardMembers() {
        val boardMembers = listOf(
            BoardMember("Paulos Ngcobo", "Chairman", R.drawable.paulos_ngcobo),
            BoardMember("Belinda Putterill", "Managing Director", R.drawable.belinda_putterill),
            BoardMember("Adrienne Brown", "Finance", R.drawable.adrienne_brown),
            BoardMember("Portio Dlamini", "Director", R.drawable.portio_dlamini),
            BoardMember("Devon de Sousa", "Legal & HR", R.drawable.devon_de_sousa),
            BoardMember("Gerald Chotu", "Circular Economy Sustainability", R.drawable.gerald_chotu),
            BoardMember("Heidi Cox", "Textile Sustainability", R.drawable.heidi_cox),
            BoardMember("Anuka Brown", "Flexing Packaging Sustainability", R.drawable.anuka_brown)
        )

        boardMembers.forEachIndexed { index, member ->
            val memberView = when (index) {
                0 -> binding.boardMembers.memberChairman
                1 -> binding.boardMembers.memberMd

                else -> null
            }

            memberView?.apply {
                memberImage.setImageResource(member.imageRes)
                memberName.text = member.name
                memberRole.text = member.role
                viewInfoButton.setOnClickListener {
                    showMemberInfo(member)
                }
            }
        }
    }

    private fun showMemberInfo(member: BoardMember) {
        // Implement member info dialog or navigation
        Snackbar.make(binding.root, "Viewing info for ${member.name}", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class Goal(val title: String, val description: String)
    data class BoardMember(val name: String, val role: String, val imageRes: Int)
}