package com.abdulaziz.covidapp.ui.view.statistics

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abdulaziz.covidapp.CovidApp.Companion.CURRENT_COUNTRY
import com.abdulaziz.covidapp.CovidApp.Companion.DEFAULT_COUNTRY
import com.abdulaziz.covidapp.databinding.FragmentStatisticsBinding
import com.abdulaziz.covidapp.ui.adapter.PagerStatisticAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val GLOBAL = "Global"

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PagerStatisticAdapter(childFragmentManager, lifecycle, false)
    }

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: PagerStatisticAdapter
    private var checkedPosition = 0
    @Inject
    lateinit var sPref: SharedPreferences
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        viewModel.updateText.observe(viewLifecycleOwner) {
            binding.updateTimeTv.text = it
        }

        setSwitch()
        setTabs()

        return binding.root
    }

    private fun setSwitch() {
        val country = sPref.getString(CURRENT_COUNTRY, DEFAULT_COUNTRY)
        binding.toggleSwitch.setEntries(
            arrayListOf(
                country!!,
                GLOBAL
            )
        )
        binding.toggleSwitch.setCheckedPosition(checkedPosition)
        binding.toggleSwitch.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(position: Int) {
                val boolean = position!=0
                adapter = PagerStatisticAdapter(childFragmentManager, lifecycle, boolean)
                binding.viewPager.adapter = adapter
            }
        }
    }

    private fun setTabs() {
        val pagesList = arrayListOf<String>()
        pagesList.add("Total")
        pagesList.add("Today")
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pagesList[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}