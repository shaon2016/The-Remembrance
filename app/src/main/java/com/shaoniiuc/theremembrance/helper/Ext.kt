package com.shaoniiuc.theremembrance.helper

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun AppCompatActivity.initFragment(container : Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(container, fragment)
        .commit()
}

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(viewModelClass)

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)).get(viewModelClass)

