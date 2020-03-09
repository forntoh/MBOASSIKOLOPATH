package com.mboasikolopath.ui.main.home.explore.speciality

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
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
import kotlinx.android.synthetic.main.fragment_speciality.*
import kotlinx.coroutines.launch

class SpecialityFragment : ScopedFragment() {

    private val seriesSection = Section()

    private lateinit var viewModels: SpecialityViewModel

    private val args: SpecialityFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_speciality, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModels = ViewModelProviders.of(this).get(SpecialityViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        (activity as MainActivity).hideTabHost(true)

        GenericListItem.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_book_open_page)
            .apply { this!!.setColorFilter(args.color, PorterDuff.Mode.SRC_ATOP) }

        val schoolsAdapter = GroupAdapter<ViewHolder>().apply {
            add(seriesSection)
            setOnItemClickListener(onItemClickListener)
        }
        rv_speciality.apply {
            adapter = schoolsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        //seriesSection.update(viewModels.getSeries())
    }

    private val onItemClickListener = OnItemClickListener { item, _ ->
        if (item is GenericListItem) {
            //val fragment = NewsDetailFragment().apply { arguments = Bundle().apply { putLong("news", item.key) } }
            //(context as CategoryActivity).loadFragment(fragment).addToBackStack(null)
        }
    }

}
