package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.model.Article
import com.abdulaziz.covidapp.databinding.ItemShortArticleBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject


class HomeArticleAdapter @Inject constructor() : RecyclerView.Adapter<HomeArticleAdapter.HNVH>() {

    private var list: List<Article> = arrayListOf()
    private var listener: OnArticleClickListener?=null

    inner class HNVH(private val itemBinding: ItemShortArticleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(article:Article) {
            itemBinding.apply {
                titleTv.text = article.title
                subtitleTv.text = article.description
                Picasso.get().load(article.image_url).into(imageView)
                root.setOnClickListener {
                    listener?.onArticleClicked(article)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Article>, listener: OnArticleClickListener){
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

    override fun getItemCount(): Int = 3

    interface OnArticleClickListener{
        fun onArticleClicked(article: Article)
    }
}