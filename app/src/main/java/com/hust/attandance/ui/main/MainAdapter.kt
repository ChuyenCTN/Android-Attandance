package com.hust.attandance.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hust.attandance.ui.home.HomeFragment
import com.hust.attandance.ui.profile.ProfileFragment

class MainAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment.newInstance()
            }
            1 -> {
                ProfileFragment.newInstance()
            }
            else -> HomeFragment.newInstance()
        }
    }
}