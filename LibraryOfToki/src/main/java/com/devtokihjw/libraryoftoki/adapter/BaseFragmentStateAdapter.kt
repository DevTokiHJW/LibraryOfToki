package com.devtokihjw.libraryoftoki.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class BaseFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val list: MutableList<Any>?) : FragmentStateAdapter(fragmentManager, lifecycle) {

    constructor(fragmentActivity: FragmentActivity, list: MutableList<Any>?) : this(fragmentActivity.supportFragmentManager, fragmentActivity.lifecycle, list)

    constructor(fragment: Fragment, list: MutableList<Any>?) : this(fragment.childFragmentManager, fragment.lifecycle, list)

    override fun getItemCount(): Int = list?.size ?: 0
}