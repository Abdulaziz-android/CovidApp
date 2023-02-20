package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.model.CustomDataModel
import com.abdulaziz.covidapp.databinding.ItemPreventionBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PreventionAdapter @Inject constructor() : RecyclerView.Adapter<PreventionAdapter.PVH>() {

    private var list: List<CustomDataModel> = arrayListOf()
    private var listener:PreventionShortAdapter.OnPreventionClickListener?=null

    inner class PVH(private val itemBinding: ItemPreventionBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(prevention:CustomDataModel) {
            itemBinding.apply {
                titleTv.text = prevention.title
                subtitleTv.text = prevention.subtitle
                Picasso.get().load(prevention.image_res_id).into(imageView)

                root.setOnClickListener {
                    listener?.onPreventionClicked(prevention)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CustomDataModel>, listener:PreventionShortAdapter.OnPreventionClickListener){
        this.list = list
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return PVH(
            ItemPreventionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

}