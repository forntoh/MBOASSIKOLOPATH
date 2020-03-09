package com.mboasikolopath.ui.main.home.explore.sector

import android.graphics.PorterDuff
import android.os.Bundle
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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_sector.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class SectorFragment : ScopedFragment() {

    private val viewModelFactory: SectorViewModelFactory by instance()

    private lateinit var viewModel: SectorViewModel

    private val mainSection = Section()

    private val args: SectorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sector, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SectorViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        GenericListItem.fragment = this@SectorFragment
        GenericListItem.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_set_center)
            .apply { this!!.setColorFilter(args.color, PorterDuff.Mode.SRC_ATOP) }

        val schoolsAdapter = GroupAdapter<ViewHolder>().apply {
            add(mainSection)
            setOnItemClickListener(onItemClickListener)
        }
        rv_sectors.apply {
            adapter = schoolsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        when (args.level) {
            0 -> {
                sectors = viewModel.sectors()
                mainSection.update(sectors)
            }

            1 -> {
                sectorOptions = viewModel.sectorOptions(args.parentId)
                long_description.visibility = View.VISIBLE
                long_description.text = sectorOptions.first().longDescription
                mainSection.update(sectorOptions)
                (activity as MainActivity).setToolbarTitle(args.title)
            }

            2 -> {
                optionSeries = when(args.after) {
                    R.id.button_after_primary -> viewModel.optionSeries(args.parentId, 1)
                    R.id.button_after_secondary -> viewModel.optionSeries(args.parentId, 2)
                    else -> viewModel.optionSeries(args.parentId)
                }
                long_description.visibility = View.VISIBLE
                long_description.text = optionSeries.first().longDescription
                mainSection.update(optionSeries)
                (activity as MainActivity).setToolbarTitle(args.title)
            }
        }

        when(args.after) {
            R.id.button_after_primary -> (activity as MainActivity).setToolbarSubTitle(getString(R.string.after_primary))
            R.id.button_after_secondary -> (activity as MainActivity).setToolbarSubTitle(getString(R.string.after_secondary))
            else -> (activity as MainActivity).setToolbarSubTitle("")
        }
    }

    private var sectors: List<GenericListItem> = emptyList()

    private var sectorOptions: List<GenericListItem> = emptyList()

    private var optionSeries: List<GenericListItem> = emptyList()

    private val onItemClickListener = OnItemClickListener { item, _ ->
        if (item is GenericListItem) {
            when (args.level) {
                2 -> {
                    optionSeries.find { item == it }!!.toExpand = !item.toExpand
                    mainSection.notifyChanged()
                }
                else -> NavHostFragment.findNavController(this).navigate(
                    SectorFragmentDirections.actionSectorFragmentSelf(args.color, args.level + 1, item._id.toInt(), item.title!!, args.after)
                )
            }
        }
    }

}
