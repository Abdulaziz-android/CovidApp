package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.model.global.daily.GlobalStatistic
import com.abdulaziz.covidapp.databinding.ItemGlobalChartBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import javax.inject.Inject

class GlobalStatisticAdapter @Inject constructor() : RecyclerView.Adapter<GlobalStatisticAdapter.TSVH>() {

    private var list: List<GlobalStatistic> = arrayListOf()
    private var isTotal = true

    inner class TSVH(private val itemBinding: ItemGlobalChartBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(position: Int) {
            itemBinding.apply {
                setUpChart()
                when (position) {
                    0 -> {
                        titleTv.text = "Confirmed"
                        val confirmed = if (isTotal) list[list.size - 1].TotalConfirmed
                        else list[list.size - 1].NewConfirmed
                        val count = "%,d".format(confirmed)
                        countTv.text = count

                        val color =
                            ContextCompat.getColor(itemBinding.root.context, R.color.red_normal)
                        val drawableResId = R.drawable.fade_red

                        val values: ArrayList<Entry> = ArrayList()
                        if (isTotal) {
                            list.forEachIndexed { index, i ->
                                i.TotalConfirmed.let { it1 ->
                                    values.add(Entry(index.toFloat(), it1.toFloat()))
                                }
                            }
                        } else {
                            list.forEachIndexed { index, i ->
                                i.NewConfirmed.let { it1 ->
                                    values.add(Entry(index.toFloat(), it1.toFloat()))
                                }
                            }
                        }
                        setData(values, color, drawableResId)
                    }
                    1 -> {
                        titleTv.text = "Death"
                        val death = if (isTotal) list[list.size - 1].TotalDeaths
                        else list[list.size - 1].NewDeaths
                        val count = "%,d".format(death)
                        countTv.text = count

                        val color =
                            ContextCompat.getColor(itemBinding.root.context, R.color.green_normal)
                        val drawableResId = R.drawable.fade_green

                        val values: ArrayList<Entry> = ArrayList()
                        if (isTotal) {
                            list.forEachIndexed { index, i ->
                                i.TotalDeaths.let { it1 ->
                                    values.add(Entry(index.toFloat(), it1.toFloat()))
                                }
                            }
                        } else {
                            list.forEachIndexed { index, i ->
                                i.NewDeaths.let { it1 ->
                                    values.add(Entry(index.toFloat(), it1.toFloat()))
                                }
                            }
                        }
                        setData(values, color, drawableResId)
                    }
                }
            }
        }

        private fun setUpChart() {

            itemBinding.apply {
                chart.apply {
                    setViewPortOffsets(0f, 0f, 0f, 0f)
                    setBackgroundColor(Color.rgb(104, 241, 175))
                    // no description text
                    description.isEnabled = false
                    // enable touch gestures
                    setTouchEnabled(false)
                    // enable scaling and dragging
                    isDragEnabled = false
                    setScaleEnabled(false)
                    // if disabled, scaling can be done on x- and y-axis separately
                    setPinchZoom(false)
                    setDrawGridBackground(false)
                    maxHighlightDistance = 300f
                }

                chart.apply {
                    xAxis.isEnabled = false
                    axisLeft.isEnabled = false
                    axisRight.isEnabled = false
                    legend.isEnabled = false

                    setBackgroundColor(Color.WHITE)
                    animateXY(2000, 2000)

                    // don't forget to refresh the drawing
                    invalidate()
                }
            }

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun setData(values: List<Entry>, color: Int, drawableResID: Int) {
            itemBinding.apply {
                val set1: LineDataSet
                if (chart.data != null &&
                    chart.data.dataSetCount > 0
                ) {
                    set1 = chart.data.getDataSetByIndex(0) as LineDataSet
                    set1.values = values
                    chart.data.notifyDataChanged()
                    chart.notifyDataSetChanged()
                } else {
                    // create a dataset and give it a type
                    set1 = LineDataSet(values, "")
                    set1.apply {
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        cubicIntensity = 0.2f
                        setDrawFilled(true)
                        setDrawCircles(false)
                        lineWidth = 1.8f
                        setColor(color)
                        fillDrawable = itemBinding.root.context.resources.getDrawable(
                            drawableResID,
                            itemBinding.root.context.theme
                        )
                        fillAlpha = 100
                        setDrawHorizontalHighlightIndicator(false)
                        fillFormatter =
                            IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }
                    }
                    // create a data object with the data sets
                    val data = LineData(set1)
                    //   data.setValueTypeface(tfLight)
                    data.setDrawValues(false)

                    // set data
                    chart.data = data
                }
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<GlobalStatistic>, isTotal: Boolean) {
        this.isTotal = isTotal
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TSVH {
        return TSVH(
            ItemGlobalChartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TSVH, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = if (list.isNotEmpty()) 2 else 0

}