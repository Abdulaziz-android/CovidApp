package com.abdulaziz.covidapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.model.CustomDataModel
import com.abdulaziz.covidapp.databinding.ItemHelpBinding

class HelpAdapter(private val list: List<CustomDataModel>) : RecyclerView.Adapter<HelpAdapter.HVH>() {

    inner class HVH(private val itemBinding: ItemHelpBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(data:CustomDataModel) {
            itemBinding.apply {
                titleTv.text = data.title
                subtitleTv.text = data.subtitle
                imageView.setImageResource(data.image_res_id)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HVH {
        return HVH(
            ItemHelpBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}