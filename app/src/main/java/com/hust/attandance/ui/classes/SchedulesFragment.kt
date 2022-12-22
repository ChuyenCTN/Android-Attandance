package com.hust.attandance.ui.classes

import android.os.Bundle
import android.view.View
import com.hust.attandance.databinding.FragmentScheduleBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SchedulesFragment :
    BaseViewBindingFragment<FragmentScheduleBinding, DetailClassesViewModel>(
        FragmentScheduleBinding::inflate
    ) {
    override val viewModel by sharedViewModel<DetailClassesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}