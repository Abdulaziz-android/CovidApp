package com.abdulaziz.covidapp.ui.activities.welcome

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.abdulaziz.covidapp.CovidApp.Companion.REGISTER
import com.abdulaziz.covidapp.databinding.ActivityWelcomeBinding
import com.abdulaziz.covidapp.ui.activities.main.MainActivity
import com.abdulaziz.covidapp.ui.adapter.PagerWelcomeAdapter
import com.abdulaziz.covidapp.utils.local_resurces.WelcomeResource
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    @Inject lateinit var pagerAdapter: PagerWelcomeAdapter
    @Inject lateinit var sPref:SharedPreferences
    private var pagerPosition:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPager()
    }

    private fun setUpPager() {
        val resource = WelcomeResource.getResource(this)
        pagerAdapter.submitList(resource)
        binding.apply {

            indicatorView.apply {
                setNormalSlideWidth(30f)
                setCheckedSlideWidth(90f)
                setSliderHeight(30f)
                setSlideMode(IndicatorSlideMode.SCALE)
                setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                setupWithViewPager(viewPager)
                setPageSize(3)
                notifyDataChanged()
            }
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = 1
            viewPager.isUserInputEnabled = false
            startBtn.setOnClickListener {
                if (pagerPosition!=2) {
                    binding.viewPager.currentItem = pagerPosition + 1
                }
                else{
                    sPref.edit().putBoolean(REGISTER, true).apply()
                    val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                  }
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(position: Int, pOffset: Float, pPixs: Int) {
                    super.onPageScrolled(position, pOffset, pPixs)
                    pagerPosition = position
                    startBtn.text = if (position == 0) "GET STARTED" else  "NEXT"
                }
            })
        }
    }

    override fun onBackPressed() {
        if (pagerPosition > 0) {
            binding.viewPager.currentItem = pagerPosition - 1
        } else super.onBackPressed()
    }

}