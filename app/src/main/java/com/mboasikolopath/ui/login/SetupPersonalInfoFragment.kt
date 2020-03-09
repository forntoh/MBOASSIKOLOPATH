package com.mboasikolopath.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mboasikolopath.R
import com.mboasikolopath.ui.base.ScopedFragment

/**
 * A simple [Fragment] subclass.
 */
class SetupPersonalInfoFragment : ScopedFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup_personal_info, container, false)
    }


}
