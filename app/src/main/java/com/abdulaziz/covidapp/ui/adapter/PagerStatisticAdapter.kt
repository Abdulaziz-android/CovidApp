package com.abdulaziz.covidapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abdulaziz.covidapp.ui.view.statistics.pages.CountryStatisticFragment
import com.abdulaziz.covidapp.ui.view.statistics.pages.GlobalFragment

class PagerStatisticAdapter(fa: FragmentManager, lc : Lifecycle, private val isGlobal:Boolean) : FragmentStateAdapter(fa, lc) {

    override fun createFragment(position: Int): Fragment {
        return if (isGlobal){
            GlobalFragment.newInstance(position)
        }else CountryStatisticFragment.newInstance(position)

    }
    override fun getItemCount(): Int = 2

}