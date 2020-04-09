package com.mboasikolopath.ui.main.home.explore.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mboasikolopath.R
import com.mboasikolopath.ui.base.ScopedFragment
import com.mboasikolopath.utilities.setupGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.fragment_series.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class SeriesFragment : ScopedFragment() {

    private val viewModelFactory: SeriesViewModelFactory by instance()

    private val args: SeriesFragmentArgs by navArgs()

    private lateinit var viewModel: SeriesViewModel

    private val accessConditionsSection = Section()
    private val subjectsTaughtSection = Section()
    private val skillsObtainedSection = Section()
    private val relatedSchoolsSection = Section()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_series, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory.seriesID = args.series
        viewModelFactory.cycle = args.cycle
        viewModel = ViewModelProvider(this, viewModelFactory).get(SeriesViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        tv_speciality_acronym.text = args.series
        tv_cycle.text = args.cycle.toString()
        tv_speciality.text = viewModel.series.await().Name
        tv_diploma.text = viewModel.diploma.await()
        // Load Acceder A***********************************************************************************************
        tv_acceder_a.text = viewModel.getAccederA()

        // Load Access conditions***************************************************************************************
        val accessConditionsAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(accessConditionsSection)
        }
        rv_access_conditions.apply {
            adapter = accessConditionsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        accessConditionsSection.update(viewModel.getAccessConditions())

        // Load subjects taught*****************************************************************************************
        val subjectsTaughtAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(subjectsTaughtSection)
        }
        rv_subject_taught.apply {
            adapter = subjectsTaughtAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        subjectsTaughtSection.update(viewModel.subjectsTaught.await())

        // Load skills obtained*****************************************************************************************
        val skillsObtainedAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(skillsObtainedSection)
        }
        rv_skills_obtained.apply {
            adapter = skillsObtainedAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        skillsObtainedSection.update(viewModel.getSkillsObtained())

        // Load related schools*****************************************************************************************
        val relatedSchoolsAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(relatedSchoolsSection)
        }
        rv_schools.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = relatedSchoolsAdapter
        }
        relatedSchoolsSection.update(viewModel.relatedSchools.await())

        // Load jobs and sectors****************************************************************************************
        jobs_chip_group.setupGroup(viewModel.jobs.await(), null, null)
        viewModel.debouches.await()?.let { sectors_chip_group.setupGroup(it, null, null) }
    }

}
