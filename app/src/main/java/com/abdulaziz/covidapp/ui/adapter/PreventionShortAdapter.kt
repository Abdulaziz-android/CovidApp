package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.model.CustomDataModel
import com.abdulaziz.covidapp.databinding.ItemPreventionShortBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PreventionShortAdapter @Inject constructor() : RecyclerView.Adapter<PreventionShortAdapter.PSHVH>() {

    private var list: List<CustomDataModel> = arrayListOf()
    private var listener:OnPreventionClickListener?=null

    inner class PSHVH(val itemBinding: ItemPreventionShortBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(data: CustomDataModel) {
            itemBinding.apply {
                titleTv.text = data.title
                subtitleTv.text = data.subtitle
                Picasso.get().load(data.image_res_id).into(itemBinding.imageView)
                root.setOnClickListener {
                    listener?.onPreventionClicked(data)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CustomDataModel>, listener:OnPreventionClickListener){
        this.list = list
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PSHVH {
        return PSHVH(
            ItemPreventionShortBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PSHVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size


    interface OnPreventionClickListener{
        fun onPreventionClicked(prevention:CustomDataModel)
    }
}