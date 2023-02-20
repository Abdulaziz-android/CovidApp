package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.utils.local_resurces.Country
import com.squareup.picasso.Picasso

class SpinnerCustomAdapter(context: Context, private var countries: List<Country>) :
    BaseAdapter() {
    private var inflter: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return countries.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {

        val mView = inflter.inflate(R.layout.spinner_custom_layout,null)
        val icon = mView.findViewById<View>(R.id.imageView) as ImageView?
        val names = mView.findViewById<View>(R.id.textView) as TextView?
        Picasso.get().load("https://flagcdn.com/w80/${countries[position].abbreviation.lowercase().substring(0, 2)}.png").into(icon)

        names!!.text = countries[position].abbreviation
        return mView
    }
}