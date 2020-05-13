package com.mboasikolopath.ui.main.home.explore.schools

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
import com.mboasikolopath.R
import com.mboasikolopath.data.model.School
import com.mboasikolopath.internal.GenericItemPagedListAdapter
import com.mboasikolopath.internal.OnItemClickListener
import com.mboasikolopath.ui.base.ScopedFragment
import com.mboasikolopath.ui.main.MainActivity
import com.mboasikolopath.utilities.invalidateViewState
import com.mboasikolopath.utilities.onQueryTextListener
import com.mboasikolopath.utilities.onSearchViewShown
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_schools.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class SchoolsFragment : ScopedFragment() {

    private val viewModelFactory: SchoolsViewModelFactory by instance<SchoolsViewModelFactory>()

    private lateinit var viewModel: SchoolsViewModel

    private val args: SchoolsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_schools, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SchoolsViewModel::class.java)
        viewModel.lifecycleOwner = viewLifecycleOwner
        buildUI()
    }

    private fun buildUI() = launch {
        GenericItemPagedListAdapter.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_schools)
            .apply { this!!.setColorFilter(args.color, PorterDuff.Mode.SRC_ATOP) }

        val schoolsAdapter = GenericItemPagedListAdapter<School>(
            context!!,
            this@SchoolsFragment
        ).apply {
            subtitleListener = this@SchoolsFragment.subtitleListener
            onItemClickListener = this@SchoolsFragment.onItemClickListener
        }

        rv_schools.apply {
            adapter = schoolsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        rv_schools.setState(StatesConstants.LOADING_STATE)
        viewModel.schoolsLiveData.observe(viewLifecycleOwner, Observer { schools ->
            if (schools != null) schoolsAdapter.submitList(schools) {
                if (schools.isEmpty()) rv_schools.setState(StatesConstants.EMPTY_STATE)
                else rv_schools.setState(StatesConstants.NORMAL_STATE)
            }
        })
        setupSearchView()
    }

    private val subtitleListener = object : GenericItemPagedListAdapter.SubtitleListener {
        override suspend fun genSubtitle(id: Int): String {
            return viewModel.getLocality(id) ?: "N/A"
        }
    }

    private fun setupSearchView() = (activity as MainActivity).searchView.apply {
        onQueryTextListener { search(it) }
        onSearchViewShown({ setQuery(query, false) }, {})
    }

    private fun search(text: String) = launch {
        query = text
        rv_schools.invalidateViewState()
        viewModel.searchSchoolByName(text)
    }

    private val onItemClickListener =  OnItemClickListener { item ->
        if (item is School) NavHostFragment.findNavController(this@SchoolsFragment).navigate(
            SchoolsFragmentDirections.actionSchoolsFragmentToSchoolFragment(item.SchoolID)
        )
    }

}
