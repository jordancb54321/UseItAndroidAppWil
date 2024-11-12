package com.jordan.useit.ui.donations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jordan.useit.databinding.FragmentDonationsBinding

class DonationsFragment : Fragment() {

    private var _binding: FragmentDonationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customAmount.setOnClickListener {
            binding.customAmountEditText.visibility = if (binding.customAmount.isChecked) View.VISIBLE else View.GONE
        }

        binding.donateButton.setOnClickListener {
            val amount = when {
                binding.amount50.isChecked -> 50
                binding.amount100.isChecked -> 100
                binding.amount200.isChecked -> 200
                binding.customAmount.isChecked -> binding.customAmountEditText.text.toString().toIntOrNull()
                else -> null
            }

            val cardNumber = binding.cardNumberEditText.text.toString()
            val expiryDate = binding.expiryDateEditText.text.toString()
            val cvv = binding.cvvEditText.text.toString()

            if (amount != null && cardNumber.isNotBlank() && expiryDate.isNotBlank() && cvv.isNotBlank()) {
                // Proceed with donation process (non-functional)
                Toast.makeText(context, "Donation of R$amount submitted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please fill in all details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
