package com.hust.attandance.ui.classes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hust.attandance.databinding.FragmentStudentsBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.main.MainFragmentDirections
import com.hust.attandance.utils.extensions.safeNavigate
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_students.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StudentsFragment :
    BaseViewBindingFragment<FragmentStudentsBinding, DetailClassesViewModel>(
        FragmentStudentsBinding::inflate
    ) {
    override val viewModel by sharedViewModel<DetailClassesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            btnAddStuden.setSafeOnClickListener {
                findNavController().safeNavigate(MainFragmentDirections.actionToAddStudentFragment())
            }


        }
    }

}