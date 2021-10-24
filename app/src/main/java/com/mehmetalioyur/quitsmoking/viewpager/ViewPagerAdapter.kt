package com.mehmetalioyur.quitsmoking.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter( list : ArrayList<Fragment>,
    fM: FragmentManager,
    lifecycle: Lifecycle) :
    FragmentStateAdapter(fM, lifecycle) {

    val fragmentList = list
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}