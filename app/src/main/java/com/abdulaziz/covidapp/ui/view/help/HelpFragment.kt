package com.abdulaziz.covidapp.ui.view.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdulaziz.covidapp.databinding.FragmentHelpBinding
import com.abdulaziz.covidapp.ui.adapter.HelpAdapter
import com.abdulaziz.covidapp.utils.local_resurces.HelpData

class HelpFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HelpAdapter(HelpData.getData())
    }
    
    private var _binding: FragmentHelpBinding? =null
    private val binding get() = _binding!!
    private lateinit var adapter:HelpAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(layoutInflater, container, false)

        binding.rv.adapter = adapter
                
        return binding.root
    }

}