package com.shaoniiuc.theremembrance.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shaoniiuc.theremembrance.R
import com.shaoniiuc.theremembrance.fragments.DashboardFragment
import com.shaoniiuc.theremembrance.fragments.NewReminderFragment
import com.shaoniiuc.theremembrance.helper.initFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavView()
        //TODO remove the comment
//        initHomePage()
        initNewReminderPage()

    }

    private fun setNavView() {

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_dashboard -> {
                    //initHomePage()
                    initNewReminderPage()
                    true
                }
                R.id.nav_new_reminder -> {
                    initNewReminderPage()
                    true
                }

                else -> false
            }
        }


    }

    private fun initNewReminderPage() {
        initFragment(R.id.frameContainer, NewReminderFragment.newInstance())
    }

    private fun initHomePage() {
        initFragment(R.id.frameContainer, DashboardFragment.newInstance())
    }

}
