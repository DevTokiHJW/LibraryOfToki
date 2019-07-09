package com.devtokihjw.libraryoftoki.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devtokihjw.libraryoftoki.fragment.BaseFragment

class BaseFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val list: MutableList<BaseFragment>?) : FragmentStateAdapter(fragmentManager, lifecycle) {

    constructor(fragmentActivity: FragmentActivity, list: MutableList<BaseFragment>?) : this(fragmentActivity.supportFragmentManager, fragmentActivity.lifecycle, list)

    constructor(fragment: Fragment, list: MutableList<BaseFragment>?) : this(fragment.childFragmentManager, fragment.lifecycle, list)

    override fun getItemCount(): Int = list?.size ?: 0

    override fun createFragment(position: Int) = list?.get(position) ?: Fragment()
}