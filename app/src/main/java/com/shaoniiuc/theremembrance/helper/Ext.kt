package com.shaoniiuc.theremembrance.helper

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.initFragment(container : Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(container, fragment)
        .commit()
}