package com.hust.attandance.ui.classes

import com.hust.attandance.databinding.FragmentStudentsBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StudentsFragment :
    BaseViewBindingFragment<FragmentStudentsBinding, DetailClassesViewModel>(
        FragmentStudentsBinding::inflate
    ) {
    override val viewModel by sharedViewModel<DetailClassesViewModel>()

}