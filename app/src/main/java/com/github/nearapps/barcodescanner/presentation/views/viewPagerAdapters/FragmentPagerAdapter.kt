package com.github.nearapps.barcodescanner.presentation.views.viewPagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPagerAdapter(fragmentManager: FragmentManager,
                           lifecycle: Lifecycle,
                           vararg fragment: Fragment)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentArray: Array<out Fragment> = fragment

    override fun getItemCount(): Int = fragmentArray.size
    override fun createFragment(position: Int): Fragment = fragmentArray[position]
}