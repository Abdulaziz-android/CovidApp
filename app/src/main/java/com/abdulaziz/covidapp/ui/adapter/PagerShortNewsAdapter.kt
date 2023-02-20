package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.databinding.ItemShortNewsBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PagerShortNewsAdapter @Inject constructor() :
    RecyclerView.Adapter<PagerShortNewsAdapter.PSHNH>() {

    private var list: List<NewsEntity> = arrayListOf()
    private var listener: OnItemClickListener?=null

    inner class PSHNH(private val itemBinding: ItemShortNewsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(newsEntity: NewsEntity) {
            itemBinding.apply {
                contentTv.text = newsEntity.source
                descTv.text = newsEntity.description
                if (newsEntity.image_url.isNotEmpty()) {
                    Picasso.get().load(newsEntity.image_url).into(imageView)
                }
                moreTv.setOnClickListener {
                    listener?.onMoreTextClicked()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<NewsEntity>, listener:OnItemClickListener) {
        this.list = list
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PSHNH {
        return PSHNH(
            ItemShortNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PSHNH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onMoreTextClicked()
    }
}