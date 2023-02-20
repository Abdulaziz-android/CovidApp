package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.databinding.ItemShortArticleBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class HomeNewsAdapter @Inject constructor() : RecyclerView.Adapter<HomeNewsAdapter.HNVH>() {

    private var list: List<NewsEntity> = arrayListOf()
    private var listener: OnNewsClickListener?=null

    inner class HNVH(private val itemBinding: ItemShortArticleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(newsEntity: NewsEntity) {
            itemBinding.apply {
                titleTv.text = newsEntity.source
                subtitleTv.text = newsEntity.description
                if (newsEntity.image_url.isNotEmpty()) {
                    Picasso.get().load(newsEntity.image_url).into(imageView)
                }
                root.setOnClickListener {
                    listener?.onNewsClicked(newsEntity)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<NewsEntity>, listener: OnNewsClickListener){
        this.list = list
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNVH {
        return HNVH(
            ItemShortArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HNVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnNewsClickListener{
        fun onNewsClicked(newsEntity: NewsEntity)
    }
}