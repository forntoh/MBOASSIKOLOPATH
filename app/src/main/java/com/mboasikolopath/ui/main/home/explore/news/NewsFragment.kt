package com.mboasikolopath.ui.main.home.explore.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mboasikolopath.R
import com.mboasikolopath.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.launch

class NewsFragment : ScopedFragment() {

    private val args: NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    private fun bindUI() = launch {
        news_title.text = args.title
        news_description.text = args.description
        Glide.with(this@NewsFragment).load(args.image).into(news_image)
    }

}
