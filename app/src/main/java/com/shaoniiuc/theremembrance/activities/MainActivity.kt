package com.shaoniiuc.theremembrance.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.shaoniiuc.theremembrance.R
import com.shaoniiuc.theremembrance.fragments.DashboardFragment
import com.shaoniiuc.theremembrance.fragments.NewReminderFragment
import com.shaoniiuc.theremembrance.helper.SimplestCallback
import com.shaoniiuc.theremembrance.helper.initFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_toolbar.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavView()
        configureToolbar()
        initDashboardPage()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.title = getString(R.string.dashboard)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun setNavView() {
        NewReminderFragment.loadDashboardFragmentCallback = SimplestCallback {
            initDashboardPage()
        }

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_dashboard -> {
                    drawer_layout.closeDrawer(Gravity.LEFT)
                    initDashboardPage()
                    true
                }
                R.id.nav_new_reminder -> {
                    drawer_layout.closeDrawer(Gravity.LEFT)
                    initNewReminderPage()
                    true
                }
                R.id.nav_exit -> {
                    finish()
                    true
                }

                else -> false
            }
        }
    }

    private fun initNewReminderPage() {
        initFragment(R.id.frameContainer, NewReminderFragment.newInstance())
    }

    private fun initDashboardPage() {
        initFragment(R.id.frameContainer, DashboardFragment.newInstance())
    }


    /** Override to implement drawer listener*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawer_layout.openDrawer(GravityCompat.START)
            return true
        }

        return onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}
