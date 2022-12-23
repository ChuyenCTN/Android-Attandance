package com.hust.attandance.ui.student

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hust.attandance.databinding.FragmentStudentsBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.main.MainFragmentDirections
import com.hust.attandance.utils.extensions.safeNavigate
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudentsFragment :
    BaseViewBindingFragment<FragmentStudentsBinding, StudentViewModel>(
        FragmentStudentsBinding::inflate
    ) {
    override val viewModel: StudentViewModel by viewModel()

    lateinit var adapter: StudentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {
            btnAddStuden.setSafeOnClickListener {
                findNavController().safeNavigate(MainFragmentDirections.actionToAddStudentFragment())
            }

            adapter = StudentAdapter {

            }.apply {
                rcvStudent.adapter = this
                rcvStudent.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        with(viewModel) {
            getStudents()
            students.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.setData(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}