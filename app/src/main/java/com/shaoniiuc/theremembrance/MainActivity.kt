package com.shaoniiuc.theremembrance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.shaoniiuc.theremembrance.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavView()
        initHomePage()
    }

    private fun setNavView() {

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    initHomePage()
                    true
                }

                else -> false
            }
        }


    }

    private fun initHomePage() {
        initFragment(HomeFragment.newInstance())
    }

    private fun initFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .commit()
    }
}
