package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.databinding.ItemPagerNewsBinding
import com.abdulaziz.covidapp.ui.view.news.NewsFragment
import com.abdulaziz.covidapp.utils.NewsAdapterResource
import javax.inject.Inject


class PagerNewsAdapter @Inject constructor(private val newsDao: NewsDao) :
    RecyclerView.Adapter<PagerNewsAdapter.PNVH>() {

    private var list: List<NewsEntity> = arrayListOf()
    private var listener: NewsInnerAdapter.OnInnerItemClickListener? = null

    inner class PNVH(private val itemBinding: ItemPagerNewsBinding) :
        RecyclerView.ViewHolder(itemBinding.root), NewsFragment.OnScrollListener{

        private lateinit var adapter:NewsInnerAdapter

        fun onBind(position: Int) {

            val state = when (position) {
                0 -> NewsAdapterResource.Saved
                1 -> NewsAdapterResource.Recently
                2 -> NewsAdapterResource.Highlighted
                else -> NewsAdapterResource.Saved
            }

            val mList: List<NewsEntity> = when (position) {
                0 -> newsDao.getSavedNews() ?: emptyList()
                1 -> newsDao.getRecentlyViewedNews() ?: emptyList()
                2 -> list
                else -> emptyList()
            }

            itemBinding.apply {
                adapter =
                    NewsInnerAdapter(newsDao, mList as ArrayList<NewsEntity>, listener, state)
                rv.adapter = adapter
            }
        }

        override fun onScrolled() {
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<NewsEntity>, listener: NewsInnerAdapter.OnInnerItemClickListener) {
        this.list = list
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PNVH {
        return PNVH(
            ItemPagerNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PNVH, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = 3
}