package com.abdulaziz.covidapp.ui.view.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.data.model.Article
import com.abdulaziz.covidapp.data.model.CustomDataModel
import com.abdulaziz.covidapp.databinding.FragmentShowBinding
import com.abdulaziz.covidapp.ui.activities.main.MainView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_PARAM1 = "news"
private const val ARG_PARAM2 = "article"
private const val ARG_PARAM3 = "prevention"

@AndroidEntryPoint
class ShowFragment : Fragment() {

    private var newsEntity: NewsEntity? = null
    private var article: Article? = null
    private var prevention: CustomDataModel? = null
    @Inject lateinit var newsDao: NewsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getSerializable(ARG_PARAM1).let {
                if (it != null) {
                    newsEntity = it as NewsEntity
                }
            }
            bundle.getSerializable(ARG_PARAM2).let {
                if (it != null) {
                    article = it as Article
                }
            }
            bundle.getSerializable(ARG_PARAM3).let {
                if (it != null) {
                    prevention = it as CustomDataModel
                }
            }
        }
    }

    private var _binding: FragmentShowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowBinding.inflate(layoutInflater, container, false)
        (activity as MainView?)?.hideActionBar()

        loadData()
        setOnClickListeners()

        return binding.root
    }

    private fun loadData() {
        if (newsEntity != null) {
            val content = if (newsEntity!!.snippet != null && newsEntity!!.snippet!!.isNotEmpty()) {
                newsEntity?.snippet
            } else newsEntity?.description ?: "Content is empty"

            // update viewCount
            val lastViewCount = newsDao.getLastViewCount()
            val viewCount = if (lastViewCount!=null) lastViewCount+1 else 1
            newsEntity!!.viewCount = viewCount
            newsDao.insert(newsEntity!!)

            val data = ShowData(
                title = newsEntity!!.title,
                source = newsEntity!!.source ?: "Covid19",
                content = content!!,
                image_url = newsEntity!!.image_url
            )
            setData(data)
        } else if (article != null) {
            val data = ShowData(
                title = article!!.title,
                source = "Covid19",
                content = article!!.content,
                image_url = article!!.image_url
            )
            setData(data)
        } else if (prevention != null) {
            val data = ShowData(
                title = prevention!!.title,
                source = "Covid19",
                content = prevention!!.content!!,
                image_res_id = prevention!!.image_res_id
            )
            binding.imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            setData(data)
        }
    }

    private fun setOnClickListeners() {
        binding.backIv.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }


    private fun setData(data: ShowData) {
        binding.apply {
            if (data.image_url != null && data.image_url.isNotEmpty()) {
                Picasso.get().load(data.image_url).into(imageView)
            } else if (data.image_res_id != null) {
                Picasso.get().load(data.image_res_id).into(imageView)
            }
            sourceBtn.text = data.source ?: "Covid19"
            titleTv.text = data.title ?: ""
            contentTv.text = data.content
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainView?)?.showActionBar()
    }

    data class ShowData(
        val title: String,
        val source: String,
        val content: String,
        val image_url: String? = null,
        val image_res_id: Int? = null
    )
}