package com.abdulaziz.covidapp.ui.view.statistics.pages

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.abdulaziz.covidapp.CovidApp.Companion.CURRENT_COUNTRY
import com.abdulaziz.covidapp.CovidApp.Companion.DEFAULT_COUNTRY
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.model.statistic.Statistic
import com.abdulaziz.covidapp.databinding.FragmentCountryStatisticBinding
import com.abdulaziz.covidapp.ui.adapter.CountryStatisticAdapter
import com.abdulaziz.covidapp.ui.adapter.PagerShortNewsAdapter
import com.abdulaziz.covidapp.ui.view.statistics.StatisticsViewModel
import com.abdulaziz.covidapp.utils.NewsResource
import com.abdulaziz.covidapp.utils.StatisticResource
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class CountryStatisticFragment : Fragment(), PagerShortNewsAdapter.OnItemClickListener {

    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM1)
        }
    }

    private var _binding: FragmentCountryStatisticBinding? = null
    private val binding get() = _binding!!
    private var runnable: Runnable? = null
    private var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var chart: LineChart

    @Inject
    lateinit var pagerAdapter: PagerShortNewsAdapter
    @Inject
    lateinit var statisticAdapter: CountryStatisticAdapter
    @Inject
    lateinit var sPref: SharedPreferences
    private val viewModel: CountryStatisticViewModel by viewModels()
    private val parentViewModel: StatisticsViewModel by viewModels({ requireParentFragment() })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryStatisticBinding.inflate(layoutInflater, container, false)

        setUpPager()
        observeData()

        return binding.root
    }

    private fun observeData() {
        val country = sPref.getString(CURRENT_COUNTRY, DEFAULT_COUNTRY)
        viewModel.setCountry(country ?: DEFAULT_COUNTRY)
        binding.statisticsRv.adapter = statisticAdapter

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
                            pagerAdapter.submitList(it.list, this@CountryStatisticFragment)
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
                        is StatisticResource.Loading -> {
                            setItemVisibility(View.VISIBLE, null)
                            binding.totalTv.visibility = View.GONE
                        }
                        is StatisticResource.Success -> {
                            setItemVisibility(View.GONE, View.VISIBLE)
                            val list = it.statistic.list
                            statisticAdapter.submitList(list)
                            it.statistic.date?.let { it1 -> parentViewModel.setLastUpdate(it1) }
                            prepareChart(list)
                        }
                        is StatisticResource.Error -> {
                            setItemVisibility(View.GONE, View.GONE)
                            Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }


    private fun setItemVisibility(progressVisibility: Int?,itemVisibility: Int?) {
        if (progressVisibility != null) {
            binding.totalChartProgressBar.visibility = progressVisibility
            binding.statisticsProgressBar.visibility = progressVisibility
        }
        if (itemVisibility != null) {
            binding.totalTv.visibility = itemVisibility
            binding.statisticsRv.visibility = itemVisibility
            binding.totalChartCard.visibility = itemVisibility
        }
    }

    private fun prepareChart(list: List<Statistic>) {

        chart = binding.chart
        chart.apply {
            setTouchEnabled(false)
            dragDecelerationFrictionCoef = 0.9f
            isDragEnabled = false
            setScaleEnabled(false)
            setDrawGridBackground(false)
            isHighlightPerDragEnabled = false
            setPinchZoom(false)
            setBackgroundColor(Color.WHITE)
            animateX(1500)
            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            description = null
            setNoDataText("")
        }


        val l: Legend = chart.legend
        l.apply {
            form = Legend.LegendForm.LINE
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            orientation = Legend.LegendOrientation.HORIZONTAL
            textSize = 14f
            setDrawInside(false)
        }

        setChart(list)
    }


    private fun setChart(list: List<Statistic>) {
        val active = arrayListOf<Entry>()
        val confirmed = arrayListOf<Entry>()
        val death = arrayListOf<Entry>()
        list.forEachIndexed { index, statistic ->
            statistic.Active.let { i -> active.add(Entry(index.toFloat(), i.toFloat())) }
            statistic.Confirmed.let { i -> confirmed.add(Entry(index.toFloat(), i.toFloat())) }
            statistic.Deaths.let { i -> death.add(Entry(index.toFloat(), i.toFloat())) }
        }
        setData(active, confirmed, death)
    }


    private fun setData(values1: List<Entry>, values2: List<Entry>, values3: List<Entry>) {

        val set1: LineDataSet
        val set2: LineDataSet
        val set3: LineDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set2 = chart.data.getDataSetByIndex(1) as LineDataSet
            set3 = chart.data.getDataSetByIndex(2) as LineDataSet
            set1.values = values1
            set2.values = values2
            set3.values = values3
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {

            set1 = LineDataSet(values1, "Active")
            set1.apply {
                mode = LineDataSet.Mode.CUBIC_BEZIER
                color = ColorTemplate.getHoloBlue()
                lineWidth = 4f
                fillAlpha = 65
                setDrawCircles(false)
                setDrawCircleHole(false)
            }

            set2 = LineDataSet(values2, "Confirmed")
            set2.apply {
                mode = LineDataSet.Mode.CUBIC_BEZIER
                color = Color.GREEN
                lineWidth = 4f
                setDrawCircles(false)
                fillAlpha = 65
                setDrawCircleHole(false)
            }

            set3 = LineDataSet(values3, "Death")
            set3.apply {
                mode = LineDataSet.Mode.CUBIC_BEZIER
                color = Color.RED
                lineWidth = 4f
                fillAlpha = 65
                setDrawCircles(false)
                setDrawCircleHole(false)
            }

            val data = LineData(set2, set1, set3)
            data.setDrawValues(false)

            // set data
            chart.data = data
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

    override fun onDestroy() {
        super.onDestroy()
        runnable?.let { handler.removeCallbacks(it) }
    }

    companion object {

        fun newInstance(position: Int) =
            CountryStatisticFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, position)
                }
            }
    }
}