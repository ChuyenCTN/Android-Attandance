package com.hust.attandance.ui.student

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.hust.attandance.R
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.databinding.FragmentAddStudentBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.main.MainFragmentDirections
import com.hust.attandance.utils.Constants
import com.hust.attandance.utils.extensions.safeNavigate
import com.hust.attandance.utils.extensions.safeNavigateUp
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import com.hust.attandance.utils.extensions.showErrorSnackBar
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

        viewBinding.apply {
            btnSave.setSafeOnClickListener {
                val name = etCustomerName.text.toString().trim()
                val code = etCode.text.toString().trim()
                val lop = etClass.text.toString().trim()
                val address = etAddress.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val dateOfBirth = etBirthDay.text.toString().trim()
                val email = etEmail.text.toString().trim()

                if (name.isEmpty()) {
                    showErrorSnackBar("Bạn chưa nhập họ tên!")
                    return@setSafeOnClickListener
                } else if (code.isEmpty()) {
                    showErrorSnackBar("Bạn chưa nhập MSSV!")
                    return@setSafeOnClickListener
                } else if (viewModel.faceId == null) {
                    showErrorSnackBar("Bạn chưa lấy mẫu khuôn mặt")
                    return@setSafeOnClickListener
                } else {
                    showProcessing()
                    viewModel.createStudent(
                        StudentResponse(
                            code,
                            name = name,
                            faceId = viewModel.faceId!!,
                            phone,
                            address,
                            dateOfBirth,
                            "",
                            email
                        )
                    )
                }
            }
            ivChangeImage.setSafeOnClickListener {
                findNavController().safeNavigate(
                    MainFragmentDirections.actionToAttandanceFragment(
                        true
                    )
                )
            }
        }

        viewModel.createStudent.observe(viewLifecycleOwner) {
            hideProcessing()
            findNavController().safeNavigateUp()
        }

        setFragmentResultListener(Constants.FACE_ID_KEY) { a, b ->
            val json = b.getString(Constants.FACE_ID_DATA)
            viewModel.faceId = json
        }
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