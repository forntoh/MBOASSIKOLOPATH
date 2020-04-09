package com.mboasikolopath.ui.main.home.explore.schools.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mboasikolopath.R
import com.mboasikolopath.ui.base.ScopedFragment
import com.mboasikolopath.utilities.InsetDecoration
import com.mboasikolopath.utilities.setupGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.fragment_school.*
import kotlinx.android.synthetic.main.item_expansion.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class SchoolFragment : ScopedFragment() {

    private val viewModelFactory: SchoolViewModelFactory by instance()

    private val args: SchoolFragmentArgs by navArgs()

    private lateinit var viewModel: SchoolViewModel
    private val locationDataSection = Section()
    private val photosSection = Section()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_school, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SchoolViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        tv_school_name.text = viewModel.school(args.id).Name

        val schoolsAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(locationDataSection)
        }
        rv_location_data.apply {
            adapter = schoolsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(InsetDecoration(16))
        }
        locationDataSection.update(viewModel.locationData(args.id))

        val photosAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(photosSection)
        }
        rv_photos.apply {
            adapter = photosAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(InsetDecoration(16))
        }
        photosSection.update(viewModel.getPhotos())

        item_list_1_chips.setupGroup(viewModel.seriesFirstCycle(args.id), item_list_1_title, chipOnClickListener)
        item_list_2_chips.setupGroup(viewModel.seriesSecondCycle(args.id), item_list_2_title, chipOnClickListener)
    }

    private val chipOnClickListener = View.OnClickListener {
        val cycle = if ((it.parent as View).id == R.id.item_list_1_chips) 1 else 2
        NavHostFragment.findNavController(this).navigate(
            SchoolFragmentDirections.actionSchoolFragmentToSeriesFragment((it as Chip).text.toString(), cycle)
        )
    }
}
