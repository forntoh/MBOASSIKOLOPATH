package com.mboasikolopath.ui.main.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mboasikolopath.R
import com.mboasikolopath.ui.base.ScopedFragment
import com.mboasikolopath.utilities.InsetDecoration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.coroutines.launch

class AboutFragment : ScopedFragment() {

    private val simpleContactsSection = Section()
    private val otherContactsSection = Section()

    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_about, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        tv_about.text = viewModel.about

        val contactsAdapter = GroupAdapter<ViewHolder>().apply {
            add(simpleContactsSection)
        }
        rv_contact.apply {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(InsetDecoration(16))
        }

        val res = resources
        val icons = res.obtainTypedArray(R.array.contact_icons)

        simpleContactsSection.update(viewModel.getSimpleContacts(icons))

        icons.recycle()
    }

}
