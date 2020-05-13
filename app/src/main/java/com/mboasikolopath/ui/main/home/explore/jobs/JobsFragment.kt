package com.mboasikolopath.ui.main.home.explore.jobs

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mboasikolopath.R
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.internal.GenericItemPagedListAdapter
import com.mboasikolopath.ui.base.ScopedFragment
import com.mboasikolopath.ui.main.MainActivity
import com.mboasikolopath.utilities.invalidateViewState
import com.mboasikolopath.utilities.onQueryTextListener
import com.mboasikolopath.utilities.onSearchViewShown
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class JobsFragment : ScopedFragment() {

    private val viewModelFactory: JobsViewModelFactory by instance<JobsViewModelFactory>()

    private lateinit var viewModel: JobsViewModel

    private val args: JobsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_jobs, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(JobsViewModel::class.java)
        viewModel.lifecycleOwner = viewLifecycleOwner
        buildUI()
    }

    private fun buildUI() = launch {
        GenericItemPagedListAdapter.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_worker)
            .apply { this!!.setColorFilter(args.color, PorterDuff.Mode.SRC_ATOP) }

        val jobsAdapter = GenericItemPagedListAdapter<Job>(
            context!!,
            this@JobsFragment
        ).apply {
            chipItemsListener = this@JobsFragment.chipItemsListener
            subtitleListener = this@JobsFragment.subtitleListener
            chipOnClickListener = this@JobsFragment.chipOnClickListener
        }

        rv_jobs.apply {
            adapter = jobsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        rv_jobs.setState(StatesConstants.LOADING_STATE)
        viewModel.jobsLiveData.observe(viewLifecycleOwner, Observer { schools ->
            if (schools != null) jobsAdapter.submitList(schools) {
                if (schools.isEmpty()) rv_jobs.setState(StatesConstants.EMPTY_STATE)
                else rv_jobs.setState(StatesConstants.NORMAL_STATE)
            }
        })
        setupSearchView()
    }

    private val subtitleListener = object : GenericItemPagedListAdapter.SubtitleListener {
        override suspend fun genSubtitle(id: Int): String {
            return viewModel.getSpecialityOfJob(id)
        }
    }

    private val chipItemsListener = object : GenericItemPagedListAdapter.ChipItemsListener {
        override suspend fun getSeriesOfJob(id: Int): List<Series> {
            return viewModel.getSeriesOfJob(id)
        }
    }

    private val chipOnClickListener = View.OnClickListener {
        val cycle = if ((it.parent as View).id == R.id.item_list_1_chips) 1 else 2
        NavHostFragment.findNavController(this).navigate(JobsFragmentDirections.actionJobsFragmentToSeriesFragment(
            (it as Chip).text.toString(),
            cycle
        ))
    }

    private fun setupSearchView() = (activity as MainActivity).searchView.apply {
        onQueryTextListener { search(it) }
        onSearchViewShown({ setQuery(query, false) }, {})
    }

    private fun search(text: String) = launch {
        query = text
        rv_jobs.invalidateViewState()
        viewModel.searchJobs(text)
    }

}
