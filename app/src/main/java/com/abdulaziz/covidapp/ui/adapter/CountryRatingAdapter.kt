package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.model.global.Country
import com.abdulaziz.covidapp.databinding.ItemCountryBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CountryRatingAdapter @Inject constructor() : RecyclerView.Adapter<CountryRatingAdapter.CRVH>() {

    private var list = listOf<Country>()
    private var isTotal = true

    inner class CRVH(private val itemBinding: ItemCountryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(country: Country, position: Int) {
            itemBinding.apply {
                numberTv.text = (position + 1).toString()

                val confirmed: Int
                val death: Int
                if (isTotal) {
                    confirmed = country.TotalConfirmed
                    death = country.TotalDeaths
                } else {
                    confirmed = country.NewConfirmed
                    death = country.NewDeaths
                }

                nameTv.text = country.Country
                val confirmedCount = "%,d".format(confirmed)
                val deathCount = "%,d".format(death)
                confirmedTv.text = "Confirmed - $confirmedCount"
                deathTv.text = "Death - $deathCount"

                Picasso.get().load("https://flagcdn.com/w80/${
                        country.CountryCode.lowercase().substring(0, 2)}.png"
                ).into(flagIv)

                var percent =
                    if (death != 0) ((death.toDouble() / confirmed.toDouble()) * 100).toString() else "0,0"
                percent = if (percent.length > 3) percent.substring(0, 3) else percent
                progressTv.text = "$percent%"

                progressBar.maxProgress = confirmed.toFloat()
                progressBar.progress = death.toFloat()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Country>, isTotal: Boolean) {
        this.isTotal = isTotal
        this.list = if (isTotal){
            list.sortedByDescending { it.TotalConfirmed }
        }else{
            list.sortedByDescending { it.NewConfirmed }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CRVH {
        return CRVH(ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CRVH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}