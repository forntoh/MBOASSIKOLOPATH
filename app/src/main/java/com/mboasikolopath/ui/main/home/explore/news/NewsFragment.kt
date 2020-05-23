package com.mboasikolopath.ui.main.home.explore.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mboasikolopath.R
import com.mboasikolopath.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class NewsFragment : ScopedFragment() {

    private val args: NewsFragmentArgs by navArgs()

    private lateinit var newsViewModel: NewsViewModel
    private val newsViewModelFactory: NewsViewModelFactory by instance<NewsViewModelFactory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val news = newsViewModel.findNewsByID(args.newsId)
        news_title.text = news.Title
        news_description.text = news.Body
        Glide.with(this@NewsFragment).load(news.Thumbnail).placeholder(R.drawable.welcome_image).into(news_image)
    }

}
