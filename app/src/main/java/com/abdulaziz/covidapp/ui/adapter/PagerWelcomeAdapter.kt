package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.model.CustomDataModel
import com.abdulaziz.covidapp.databinding.ItemPagerWelcomeBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagerWelcomeAdapter @Inject constructor() : RecyclerView.Adapter<PagerWelcomeAdapter.PWH>() {

    private var list: List<CustomDataModel> = arrayListOf()

    inner class PWH(private val itemBinding: ItemPagerWelcomeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(data: CustomDataModel) {
            itemBinding.apply {
                titleTv.text = data.title
                subtitleTv.text = data.subtitle
                Picasso.get().load(data.image_res_id).into(itemBinding.imageView)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CustomDataModel>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PWH {
        return PWH(
            ItemPagerWelcomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PWH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}