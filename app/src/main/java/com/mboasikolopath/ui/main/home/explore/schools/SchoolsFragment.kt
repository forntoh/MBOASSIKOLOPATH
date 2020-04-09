package com.mboasikolopath.ui.main.home.explore.schools

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
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
import kotlinx.android.synthetic.main.fragment_schools.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class SchoolsFragment : ScopedFragment() {

    private val viewModelFactory: SchoolsViewModelFactory by instance()

    private lateinit var viewModel: SchoolsViewModel
    private lateinit var schools: List<GenericListItem>

    private val schoolsSection = Section()

    private val args: SchoolsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_schools, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SchoolsViewModel::class.java)
        buildUI()
    }

    private fun buildUI() = launch {
        GenericListItem.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_schools)
            .apply { this!!.setColorFilter(args.color, PorterDuff.Mode.SRC_ATOP) }

        val schoolsAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(schoolsSection)
            setOnItemClickListener(onItemClickListener)
        }
        rv_schools.apply {
            adapter = schoolsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        rv_schools.invalidateViewState()
        rv_schools.toggleViewState(schoolsSection.apply { update(viewModel.schools.await().apply { schools = this }) })
        setupSearchView()
    }

    private fun setupSearchView() = (activity as MainActivity).searchView.apply {
        onQueryTextListener { search(it) }
        onSearchViewShown({ setQuery(query, false) }, { schoolsSection.update(schools) })
    }

    private fun search(text: String) = launch {
        query = text
        rv_schools.invalidateViewState()
        rv_schools.toggleViewState(schoolsSection.apply { update(viewModel.searchSchoolByName(text)) })
    }

    private val onItemClickListener = OnItemClickListener { item, _ ->
        Log.d("API", item.toString())
        if (item is GenericListItem) NavHostFragment.findNavController(this).navigate(
            SchoolsFragmentDirections.actionSchoolsFragmentToSchoolFragment(item._id.toInt())
        )
    }

}
