package com.abdulaziz.covidapp.ui.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.databinding.FragmentNewsBinding
import com.abdulaziz.covidapp.ui.adapter.NewsInnerAdapter
import com.abdulaziz.covidapp.ui.adapter.PagerNewsAdapter
import com.abdulaziz.covidapp.utils.NewsResource
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class NewsFragment : Fragment(), NewsInnerAdapter.OnInnerItemClickListener {


    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var adapter: PagerNewsAdapter
    @Inject lateinit var newsDao: NewsDao
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)

        setTabs()
        observeData()

        return binding.root
    }

    private fun observeData() {
        lifecycleScope.launch(Dispatchers.Main) {
            launch {
                viewModel.getNewsStateFlow().collect {
                    when (it) {
                        is NewsResource.Loading -> {
                            setItemVisibility(View.VISIBLE, View.GONE)
                        }
                        is NewsResource.Success -> {
                            setItemVisibility(View.GONE, View.VISIBLE)

                            val l = arrayListOf<NewsEntity>()
                            l.addAll(it.list)
                            newsDao.getSavedNewsFlow().collect { data ->
                                adapter.submitList(it.list, this@NewsFragment)
                                binding.tabLayout.getTabAt(0)?.text = "Saved(${data.size})"
                            }
                        }
                        is NewsResource.Error -> {
                            setItemVisibility(View.GONE, View.GONE)
                            Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setItemVisibility(progressVisibility: Int?, itemVisibility: Int?) {
        if (progressVisibility != null) {
            binding.progressBar.visibility = progressVisibility
        }
        if (itemVisibility != null) {
            binding.tabCard.visibility = itemVisibility
            binding.viewPager.visibility = itemVisibility
        }
    }


    private fun setTabs() {
        val pagesList = arrayListOf<String>()
        pagesList.add("Saved")
        pagesList.add("Recently viewed")
        pagesList.add("Highlighted")
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pagesList[position]
        }.attach()

    }

    override fun onItemClicked(newsEntity: NewsEntity) {
        Navigation.findNavController(binding.root)
            .navigate(R.id.nav_show, bundleOf(Pair("news", newsEntity)))
    }

    interface OnScrollListener {
        fun onScrolled()
    }


}