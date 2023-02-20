package com.abdulaziz.covidapp.ui.view.symptoms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.databinding.FragmentSymptomsBinding

class SymptomsFragment : Fragment() {

    private var _binding: FragmentSymptomsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymptomsBinding.inflate(inflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners() {
        binding.apply {
            moreTv.setOnClickListener { showNextUpdateToast() }
            dryCoughCard.setOnClickListener { showNextUpdateToast() }
            feverCard.setOnClickListener { showNextUpdateToast() }
        }
    }

    private fun showNextUpdateToast(){
        Toast.makeText(binding.root.context, R.string.next_update, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}