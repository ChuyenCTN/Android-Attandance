package com.hust.attandance.ui.classes

import com.hust.attandance.databinding.FragmentScheduleBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SchedulesFragment :
    BaseViewBindingFragment<FragmentScheduleBinding, DetailClassesViewModel>(
        FragmentScheduleBinding::inflate
    ) {
    override val viewModel by sharedViewModel<DetailClassesViewModel>()


}