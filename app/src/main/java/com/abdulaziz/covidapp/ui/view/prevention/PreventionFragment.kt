package com.abdulaziz.covidapp.ui.view.prevention

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.model.CustomDataModel
import com.abdulaziz.covidapp.databinding.FragmentPreventionBinding
import com.abdulaziz.covidapp.ui.adapter.PreventionAdapter
import com.abdulaziz.covidapp.ui.adapter.PreventionShortAdapter
import com.abdulaziz.covidapp.utils.local_resurces.PreventionData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreventionFragment : Fragment(), PreventionShortAdapter.OnPreventionClickListener {

    private var _binding: FragmentPreventionBinding? =null
    private val binding get() = _binding!!
    @Inject lateinit var adapter:PreventionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreventionBinding.inflate(layoutInflater, container, false)

        loadData()

        return binding.root
    }

    private fun loadData() {
        binding.rv.adapter = adapter
        val list = PreventionData.getData(binding.root.context)
        adapter.submitList(list, this@PreventionFragment)
    }

    override fun onPreventionClicked(prevention: CustomDataModel) {
        Navigation.findNavController(binding.root).navigate(R.id.nav_show, bundleOf(Pair("prevention", prevention)))
    }

}