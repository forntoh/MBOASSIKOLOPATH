package com.mboasikolopath.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.mboasikolopath.R
import com.mboasikolopath.ui.main.about.AboutFragment
import com.mboasikolopath.ui.main.home.HomeFragment
import com.mboasikolopath.ui.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var pagerAdapter: MyPagerAdapter
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        pagerAdapter = MyPagerAdapter(this.childFragmentManager)

        pager.adapter = pagerAdapter

        tabLayout = activity!!.findViewById(R.id.tab_layout)
        tabLayout.visibility = View.VISIBLE

        tabLayout.setupWithViewPager(pager)

        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_home)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_account_circle)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_about)
    }

    class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> HomeFragment()
            1 -> ProfileFragment()
            else -> AboutFragment()
        }

        override fun getCount() = 3
    }
}
