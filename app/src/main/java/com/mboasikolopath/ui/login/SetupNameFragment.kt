package com.mboasikolopath.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.mboasikolopath.R
import com.mboasikolopath.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_setup_name.*

class SetupNameFragment : ScopedFragment() {

    private lateinit var titleText: String
    private lateinit var viewToAdd: View

    companion object {
        fun newInstance(title: String, viewToAdd: View): SetupNameFragment =
            SetupNameFragment().apply {
                this.titleText = title
                this.viewToAdd = viewToAdd
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_setup_name, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        tv_title?.text = titleText
        if (viewToAdd.parent != null) (viewToAdd.parent as ViewGroup).removeView(viewToAdd)
        container.addView(viewToAdd, (viewToAdd.layoutParams as LinearLayout.LayoutParams))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

}
