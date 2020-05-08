package com.mboasikolopath.ui.main.home.explore.jobs

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mboasikolopath.R
import com.mboasikolopath.internal.view.GenericListItem
import com.mboasikolopath.ui.base.ScopedFragment
import com.mboasikolopath.ui.main.MainActivity
import com.mboasikolopath.utilities.invalidateViewState
import com.mboasikolopath.utilities.onQueryTextListener
import com.mboasikolopath.utilities.onSearchViewShown
import com.mboasikolopath.utilities.toggleViewState
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class JobsFragment : ScopedFragment() {

    private val viewModelFactory: JobsViewModelFactory by instance<JobsViewModelFactory>()

    private lateinit var viewModel: JobsViewModel
    private lateinit var jobs: List<GenericListItem>

    private val jobsSection = Section()

    private val args: JobsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_jobs, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(JobsViewModel::class.java)
        buildUI()
    }

    private fun buildUI() = launch {
        GenericListItem.fragment = this@JobsFragment
        GenericListItem.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_worker)
            .apply { this!!.setColorFilter(args.color, PorterDuff.Mode.SRC_ATOP) }

        val schoolsAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(jobsSection)
            setOnItemClickListener(onItemClickListener)
        }
        rv_jobs.apply {
            adapter = schoolsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        rv_jobs.invalidateViewState()
        rv_jobs.toggleViewState(jobsSection.apply { update(viewModel.jobs.await().apply { jobs = this }) })
        setupSearchView()
    }

    private fun setupSearchView() = (activity as MainActivity).searchView.apply {
        onQueryTextListener { search(it) }
        onSearchViewShown({ setQuery(query, false) }, { jobsSection.update(jobs) })
    }

    private fun search(text: String) = launch {
        query = text
        rv_jobs.invalidateViewState()
        rv_jobs.toggleViewState(jobsSection.apply { update(viewModel.searchJobs(text)) })
    }

    private val onItemClickListener = OnItemClickListener { item, _ ->
        if (item is GenericListItem) {
            jobs.find { item == it }!!.toExpand = !item.toExpand
            jobsSection.notifyChanged()
        }
    }

}
