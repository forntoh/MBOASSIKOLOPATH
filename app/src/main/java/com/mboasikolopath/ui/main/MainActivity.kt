package com.mboasikolopath.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mboasikolopath.R
import com.mboasikolopath.utilities.enableWhiteStatusBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var searchItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindUI()
    }

    private fun bindUI() {
        setSupportActionBar(toolbar)
        enableWhiteStatusBar()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController)

        navController.addOnDestinationChangedListener { _, destination, args ->
            when (destination.id) {
                R.id.schoolsFragment -> searchItem?.isVisible = true
                R.id.jobsFragment -> searchItem?.isVisible = true
                R.id.sectorFragment -> {
                    when (args?.get("level")) {
                        0 -> searchItem?.isVisible = false

                        1 -> searchItem?.isVisible = false

                        2 -> searchItem?.isVisible = true
                    }
                }
                else -> searchItem?.isVisible = false
            }
        }
    }

    fun hideTabHost(toHide: Boolean) {
        tab_layout.visibility = if (toHide) View.GONE else View.VISIBLE
    }

    fun setToolbarTitle(title: String) {
        toolbar.title = title
    }
    fun setToolbarSubTitle(title: String) {
        toolbar.subtitle = title
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, null)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        searchItem = menu.findItem(R.id.search).apply { isVisible = false }
        searchView.setMenuItem(searchItem!!)
        searchView.tabLayout = tab_layout
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (searchView.onBackPressed()) return
        super.onBackPressed()
    }

}
