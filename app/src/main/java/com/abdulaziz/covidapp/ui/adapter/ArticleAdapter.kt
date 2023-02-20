package com.abdulaziz.covidapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulaziz.covidapp.data.model.Article
import com.abdulaziz.covidapp.databinding.ItemArticleBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ArticleAdapter @Inject constructor() : RecyclerView.Adapter<ArticleAdapter.AVH>() {

    private var list: List<Article> = arrayListOf()
    private var listener:OnMoreClickListener?=null

    inner class AVH(private val itemBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(article:Article) {
            itemBinding.apply {
                titleTv.text = article.title
                subtitleTv.text = article.description
                Picasso.get().load(article.image_url).into(imageView)

                likeCountTv.text = article.title.length.toString()
                showCountTv.text = (article.content.length*2).toString()
                commentCountTv.text = (article.description.length*5).toString()

                readMoreBtn.setOnClickListener {
                    listener?.onMoreClicked(article)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Article>, listener:OnMoreClickListener){
        this.list = list
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AVH {
        return AVH(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnMoreClickListener{
        fun onMoreClicked(article: Article)
    }
}