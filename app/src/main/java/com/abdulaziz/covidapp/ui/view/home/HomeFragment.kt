package com.abdulaziz.covidapp.ui.view.home

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.abdulaziz.covidapp.CovidApp.Companion.CURRENT_COUNTRY
import com.abdulaziz.covidapp.CovidApp.Companion.SPINNER_POSITION
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.data.model.Article
import com.abdulaziz.covidapp.data.model.CustomDataModel
import com.abdulaziz.covidapp.databinding.FragmentHomeBinding
import com.abdulaziz.covidapp.ui.adapter.HomeArticleAdapter
import com.abdulaziz.covidapp.ui.adapter.HomeNewsAdapter
import com.abdulaziz.covidapp.ui.adapter.PreventionShortAdapter
import com.abdulaziz.covidapp.ui.adapter.SpinnerCustomAdapter
import com.abdulaziz.covidapp.utils.NewsResource
import com.abdulaziz.covidapp.utils.local_resurces.ArticlesData
import com.abdulaziz.covidapp.utils.local_resurces.CountriesData
import com.abdulaziz.covidapp.utils.local_resurces.PreventionData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(), HomeNewsAdapter.OnNewsClickListener,
    PreventionShortAdapter.OnPreventionClickListener, HomeArticleAdapter.OnArticleClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preventionAdapter: PreventionShortAdapter

    @Inject
    lateinit var articleAdapter: HomeArticleAdapter

    @Inject
    lateinit var newsAdapter: HomeNewsAdapter

    @Inject
    lateinit var sPref: SharedPreferences

    @Inject
    lateinit var newsDao: NewsDao
    private val viewModel: HomeViewModel by viewModels()
    private val countries = CountriesData.getCountries()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        loadData()
        observeData()
        setOnClickListeners()
        setSpinner()


        return binding.root
    }

    private fun setSpinner() {
        val customAdapter = SpinnerCustomAdapter(binding.root.context, countries)
        binding.spinner.adapter = customAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                sPref.edit()
                    .putInt(SPINNER_POSITION, position)
                    .putString(CURRENT_COUNTRY, countries[position].name)
                    .apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        val position = sPref.getInt(SPINNER_POSITION, 0)
        binding.spinner.setSelection(position)
    }

    private fun setOnClickListeners() {
        binding.apply {
            preventionMoreIv.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.nav_prevention)
            }
            articleMoreIv.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.nav_article)
            }
            newsMoreIv.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.nav_news)
            }
            callCard.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:103")
                startActivity(intent)
            }
            sendMessageCard.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "tel:103", null))
                startActivity(intent)
            }
        }
    }

    private fun observeData() {
        binding.newsRv.adapter = newsAdapter
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getNewsStateFlow().collect {
                when (it) {
                    is NewsResource.Loading -> {
                        binding.newsTv.visibility = View.GONE
                        binding.newsMoreIv.visibility = View.GONE
                    }
                    is NewsResource.Success -> {
                        binding.newsTv.visibility = View.VISIBLE
                        binding.newsMoreIv.visibility = View.VISIBLE
                        newsAdapter.submitList(it.list, this@HomeFragment)
                    }
                    is NewsResource.Error -> {
                        binding.newsTv.visibility = View.GONE
                        binding.newsMoreIv.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun loadData() {
        // load prevention data
        binding.preventionRv.adapter = preventionAdapter
        val preventionList = PreventionData.getData(binding.root.context)
        preventionAdapter.submitList(preventionList, this@HomeFragment)

        // load article data
        binding.articleRv.adapter = articleAdapter
        val articleList = ArticlesData.getData()
        articleAdapter.submitList(articleList, this@HomeFragment)
    }

    override fun onNewsClicked(newsEntity: NewsEntity) {
        Navigation.findNavController(binding.root)
            .navigate(R.id.nav_show, bundleOf(Pair("news", newsEntity)))
    }

    override fun onPreventionClicked(prevention: CustomDataModel) {
        Navigation.findNavController(binding.root)
            .navigate(R.id.nav_show, bundleOf(Pair("prevention", prevention)))
    }

    override fun onArticleClicked(article: Article) {
        Navigation.findNavController(binding.root)
            .navigate(R.id.nav_show, bundleOf(Pair("article", article)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}