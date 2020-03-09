package com.mboasikolopath.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.mboasikolopath.R
import com.mboasikolopath.internal.view.NewsItem
import com.mboasikolopath.ui.base.ScopedFragment
import com.mboasikolopath.ui.login.SetupViewModel
import com.mboasikolopath.ui.login.SetupViewModelFactory
import com.mboasikolopath.ui.main.MainActivity
import com.mboasikolopath.ui.main.MainFragmentDirections
import com.mboasikolopath.ui.main.home.explore.news.NewsViewModel
import com.mboasikolopath.ui.main.home.explore.news.NewsViewModelFactory
import com.mboasikolopath.utilities.InsetDecoration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class HomeFragment : ScopedFragment(), View.OnClickListener {

    private lateinit var newsViewModel: NewsViewModel
    private val newsViewModelFactory: NewsViewModelFactory by instance()

    private lateinit var viewModelSetup: SetupViewModel
    private val setupViewModelFactory: SetupViewModelFactory by instance()

    private val newsSection = Section()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)
        viewModelSetup = ViewModelProvider(this, setupViewModelFactory).get(SetupViewModel::class.java)
        buildUI()
    }

    private fun buildUI() = launch {
        (activity as MainActivity).hideTabHost(false)

        val newsAdapter = GroupAdapter<ViewHolder>().apply {
            add(newsSection)
            setOnItemClickListener(onItemClickListener)
        }
        rv_news_home.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = newsAdapter
            addItemDecoration(InsetDecoration(16))
        }
        newsSection.update(newsViewModel.getNews())

        btn_schools.setOnClickListener(this@HomeFragment)
        btn_jobs.setOnClickListener(this@HomeFragment)
        btn_sector.setOnClickListener(this@HomeFragment)
        btn_series.setOnClickListener(this@HomeFragment)

        button_after_primary.setOnClickListener(this@HomeFragment)
        button_after_secondary.setOnClickListener(this@HomeFragment)
        //button_after_high.setOnClickListener(this@HomeFragment)
    }

    override fun onClick(v: View?) {
        var color = 0
        if (v is CardView)
            color = (v.cardBackgroundColor).defaultColor
        else if (v is MaterialButton)
            color = (v.strokeColor).defaultColor

        when (v?.id) {
            R.id.btn_schools -> NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToSchoolsFragment(color)
            )
            R.id.btn_jobs -> NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToJobsFragment(color)
            )
            R.id.btn_sector -> NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToSectorFragment(color, 0, 0, "")
            )

            R.id.button_after_primary -> NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToSectorFragment(color, 0, 0, (v as MaterialButton).text.toString(), v.id)
            )
            R.id.button_after_secondary -> NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToSectorFragment(color, 0, 0, (v as MaterialButton).text.toString(), v.id)
            )
            /*R.id.button_after_high -> NavHostFragment.findNavController(this).navigate(

            )*/
        }
        (activity as MainActivity).hideTabHost(true)
    }

    private val onItemClickListener = OnItemClickListener { item, _ ->
        if (item is NewsItem) {
            (activity as MainActivity).hideTabHost(true)
            NavHostFragment.findNavController(this).navigate(
                MainFragmentDirections.actionMainFragmentToNewsFragment(item._id)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        launch { viewModelSetup.populateDatabase() }
        (activity as MainActivity).setToolbarSubTitle("")
    }

}
