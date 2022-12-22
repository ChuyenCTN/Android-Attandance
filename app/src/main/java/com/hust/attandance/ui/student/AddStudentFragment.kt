package com.hust.attandance.ui.student

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.hust.attandance.R
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.databinding.FragmentAddStudentBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.utils.setSafeNavClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AddStudentFragment :
    BaseViewBindingFragment<FragmentAddStudentBinding, AddStudentViewModel>(
        FragmentAddStudentBinding::inflate
    ) {
    override val viewModel by sharedViewModel<AddStudentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message/string")

        myRef.setValue(StudentResponse("20221","Nguyen Van A","bsdhbash", "0000732417", "Ha Noi","fdgskdn"))
    }

    private fun initToolBar() {
        viewBinding.toolbarLayout.tbTopBar.apply {
            title = "Thêm mới sinh viên"
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setSafeNavClickListener {
                findNavController().navigateUp()
            }
        }
    }

}