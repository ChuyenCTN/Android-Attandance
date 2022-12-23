package com.hust.attandance.ui.classes

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hust.attandance.ui.schedule.SchedulesFragment
import com.hust.attandance.ui.student.StudentsFragment

class DetailClassManagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        const val NOTIFICATION_MANAGER_ADAPTER_SIZE = 2
        const val STUDENT_TAB_INDEX = 0
        const val SCHEDULE_TAB_INDEX = 1
    }

    override fun getItemCount() = NOTIFICATION_MANAGER_ADAPTER_SIZE

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            STUDENT_TAB_INDEX -> StudentsFragment()
            else -> SchedulesFragment()
        }
    }
}