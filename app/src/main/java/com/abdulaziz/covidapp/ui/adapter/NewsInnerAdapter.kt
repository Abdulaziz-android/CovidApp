package com.abdulaziz.covidapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.databinding.ItemNewsBinding
import com.abdulaziz.covidapp.utils.NewsAdapterResource
import com.squareup.picasso.Picasso

class NewsInnerAdapter(
    private val newsDao: NewsDao,
    private val list: ArrayList<NewsEntity>,
    private val listener: OnInnerItemClickListener?,
    private val adapterState: NewsAdapterResource
) : RecyclerView.Adapter<NewsInnerAdapter.NIVH>() {

    inner class NIVH(private val itemBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(newsEntity: NewsEntity, position: Int) {
            itemBinding.apply {
                titleTv.text = newsEntity.source
                subtitleTv.text = newsEntity.description
                if (newsEntity.image_url.isNotEmpty()) {
                    Picasso.get().load(newsEntity.image_url).into(imageView)
                }

                val localNews = newsDao.getNewsByUUID(newsEntity.uuid)
                if (localNews!=null) {
                    newsEntity.isSaved = localNews.isSaved
                    if (localNews.isSaved) {
                        saveIv.setImageResource(R.drawable.ic_baseline_bookmark_24)
                    }
                }

                when (adapterState) {
                    is NewsAdapterResource.Saved -> {
                        itemBinding.saveIv.visibility = View.GONE
                    }
                    is NewsAdapterResource.Recently -> {
                        itemBinding.trashIv.visibility = View.GONE
                    }
                    is NewsAdapterResource.Highlighted -> {
                        itemBinding.trashIv.visibility = View.GONE
                    }
                }
                setOnClickListeners(newsEntity, position)
            }
        }

        private fun setOnClickListeners(newsEntity: NewsEntity, position: Int) {
            itemBinding.apply {
                trashIv.setOnClickListener {
                    list.remove(newsEntity)
                    newsEntity.isSaved = false
                    newsDao.insert(newsEntity)
                    notifyItemRemoved(position)
                    notifyItemChanged(position)
                }
                saveIv.setOnClickListener {
                    newsEntity.isSaved = !newsEntity.isSaved
                    if (newsEntity.isSaved){
                        saveIv.setImageResource(R.drawable.ic_baseline_bookmark_24)
                    }else saveIv.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                    newsDao.insert(newsEntity)
                }
                selectIv.setOnClickListener {
                    Toast.makeText(itemBinding.root.context, R.string.next_update, Toast.LENGTH_SHORT).show()
                }
                root.setOnClickListener {
                    listener?.onItemClicked(newsEntity)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NIVH {
        return NIVH(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NIVH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnInnerItemClickListener {
        fun onItemClicked(newsEntity: NewsEntity)
    }

}