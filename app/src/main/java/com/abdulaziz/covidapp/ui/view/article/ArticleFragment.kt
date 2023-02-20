package com.abdulaziz.covidapp.ui.view.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.model.Article
import com.abdulaziz.covidapp.databinding.FragmentArticleBinding
import com.abdulaziz.covidapp.ui.adapter.ArticleAdapter
import com.abdulaziz.covidapp.utils.local_resurces.ArticlesData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ArticleFragment : Fragment(), ArticleAdapter.OnMoreClickListener {

    private var _binding: FragmentArticleBinding? =null
    private val binding get() = _binding!!
    @Inject lateinit var adapter : ArticleAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(layoutInflater, container, false)

        loadData()

        return binding.root
    }


    private fun loadData() {
        binding.rv.adapter = adapter
        val list = ArticlesData.getData()
        adapter.submitList(list, this@ArticleFragment)
    }


    override fun onMoreClicked(article:Article) {
        Navigation.findNavController(binding.root).navigate(R.id.nav_show, bundleOf(Pair("article", article)))
    }

}