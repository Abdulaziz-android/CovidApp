package com.abdulaziz.covidapp.ui.view.statistics.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.local.entitiy.GlobalScoreEntity
import com.abdulaziz.covidapp.databinding.FragmentGlobalBinding
import com.abdulaziz.covidapp.ui.adapter.CountryRatingAdapter
import com.abdulaziz.covidapp.ui.adapter.GlobalStatisticAdapter
import com.abdulaziz.covidapp.ui.adapter.PagerShortNewsAdapter
import com.abdulaziz.covidapp.ui.view.statistics.StatisticsViewModel
import com.abdulaziz.covidapp.utils.GlobalScoreResource
import com.abdulaziz.covidapp.utils.GlobalStatisticResource
import com.abdulaziz.covidapp.utils.NewsResource
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs


private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class GlobalFragment : Fragment(), PagerShortNewsAdapter.OnItemClickListener {

    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM1)
        }
    }

    private var _binding: FragmentGlobalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GlobalViewModel by viewModels()
    @Inject lateinit var pagerAdapter: PagerShortNewsAdapter
    @Inject lateinit var ratingAdapter: CountryRatingAdapter
    @Inject lateinit var globalAdapter: GlobalStatisticAdapter

    private var runnable: Runnable? = null
    private var handler: Handler = Handler(Looper.getMainLooper())

    private val parentViewModel:StatisticsViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGlobalBinding.inflate(layoutInflater, container, false)

        setUpPager()
        observeData()

        return binding.root
    }

    private fun observeData() {
        binding.ratingRv.adapter = ratingAdapter
        binding.chartRv.adapter = globalAdapter
        lifecycleScope.launch(Dispatchers.Main) {
            launch {
                viewModel.getNewsStateFlow().collect {
                    when (it) {
                        is NewsResource.Loading -> {
                            binding.indicatorView.visibility = View.GONE
                            binding.newsProgressBar.visibility = View.VISIBLE
                        }
                        is NewsResource.Success -> {
                            binding.newsProgressBar.visibility = View.GONE
                            binding.indicatorView.visibility = View.VISIBLE
                            pagerAdapter.submitList(it.list, this@GlobalFragment)
                            autoSlidePage()
                        }
                        is NewsResource.Error -> {
                            binding.newsCard.visibility = View.GONE
                            binding.indicatorView.visibility = View.GONE
                            binding.newsProgressBar.visibility = View.GONE
                        }
                    }
                }
            }
            launch {
                viewModel.getStatisticStateFlow().collect {
                    when (it) {
                        is GlobalScoreResource.Loading -> {
                            setItemVisibility(View.VISIBLE, View.GONE)
                        }
                        is GlobalScoreResource.Success -> {
                            setItemVisibility(View.GONE, View.VISIBLE)
                            val globalData = it.globalData
                            val list = globalData.Countries
                            ratingAdapter.submitList(list, position == 0)
                            setPieChart(globalData.Global)
                            parentViewModel.setLastUpdate(globalData.Date)
                        }
                        is GlobalScoreResource.FetchLocal -> {
                            setPieChart(it.globalScoreEntity)
                            binding.topCountriesTextTv.visibility = View.GONE
                            binding.ratingProgressBar.visibility = View.GONE
                            binding.ratingCard.visibility = View.GONE
                            binding.pieCard.visibility = View.VISIBLE
                            parentViewModel.setLastUpdate(it.globalScoreEntity.Date)
                        }
                        is GlobalScoreResource.Error -> {
                            setItemVisibility(View.GONE, View.GONE)
                            binding.ratingCard.visibility = View.GONE
                            binding.chartRv.visibility = View.GONE
                        }
                    }
                }
            }
            launch {
                viewModel.getDailyStateFlow().collect {
                    when (it) {
                        is GlobalStatisticResource.Loading -> {
                            binding.chartProgressBar.visibility = View.VISIBLE
                        }
                        is GlobalStatisticResource.Success -> {
                            binding.chartProgressBar.visibility = View.GONE
                            globalAdapter.submitList(it.list, position == 0)
                        }
                        is GlobalStatisticResource.Error -> {
                            binding.chartProgressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }

    }

    private fun setItemVisibility(progressVisibility:Int?, itemVisibility:Int?){
        if (progressVisibility != null) {
            binding.ratingProgressBar.visibility = progressVisibility
        }
        if (itemVisibility != null) {
            binding.topCountriesTextTv.visibility = itemVisibility
            binding.pieCard.visibility = itemVisibility
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setPieChart(globalScoreEntity: GlobalScoreEntity) {
        binding.apply {
            val confirmed: Int
            val death: Int
            if (position == 0) {
                confirmed = globalScoreEntity.TotalConfirmed
                death = globalScoreEntity.TotalDeaths
            } else {
                confirmed = globalScoreEntity.NewConfirmed
                death = globalScoreEntity.NewDeaths
            }

            pieConfirmedCountTv.text = numberFormatter(confirmed)
            pieDeathCountTv.text = numberFormatter(death)

            var percent =
                if (death != 0) ((death.toDouble() / confirmed.toDouble()) * 100).toString() else "0,0"
            percent = if (percent.length > 3) percent.substring(0, 3) else percent
            piePercentTv.text = " $percent%"

            pieProgressBar.maxProgress = confirmed.toFloat()
            pieProgressBar.progress = death.toFloat()
        }

    }


    private fun setUpPager() {
        binding.apply {

            indicatorView.apply {
                setNormalSlideWidth(20f)
                setCheckedSlideWidth(60f)
                setSliderHeight(20f)
                setSlideMode(IndicatorSlideMode.SCALE)
                setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                setupWithViewPager(viewPager)
                setPageSize(3)
                notifyDataChanged()
            }

            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = 1
            viewPager.isUserInputEnabled = false
        }

    }

    override fun onMoreTextClicked() {
        Navigation.findNavController(binding.root).popBackStack()
        Navigation.findNavController(binding.root).navigate(R.id.nav_news)
    }

    private fun autoSlidePage() {
        runnable = Runnable {
            var pos: Int = binding.viewPager.currentItem
            pos += 1
            if (pos >= 3) pos = 0
            binding.viewPager.currentItem = pos
            runnable?.let { handler.postDelayed(it, 3000) }
        }
        handler.postDelayed(runnable!!, 3000)
    }


    private fun numberFormatter(number: Int): String {
        val numberString: String = when {
            abs(number / 1000000) > 1 -> {
                (number / 1000000).toString() + " M +"
            }
            abs(number / 1000) > 1 -> {
                (number / 1000).toString() + " K +"
            }
            else -> {
                "$number +"
            }
        }
        return numberString
    }


    override fun onDestroy() {
        super.onDestroy()
        runnable?.let { handler.removeCallbacks(it) }
    }


    companion object {
        fun newInstance(position: Int) =
            GlobalFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, position)
                }
            }
    }
}