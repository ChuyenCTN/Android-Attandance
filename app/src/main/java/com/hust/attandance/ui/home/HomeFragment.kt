package com.hust.attandance.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hust.attandance.databinding.FragmentHomeBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseViewBindingFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel by viewModel<HomeViewModel>() // class danh sach lop

    private lateinit var classesAdapter: HomeClassesAdapter

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        classesAdapter = HomeClassesAdapter {

        }
        viewBinding.apply {
            rcvClass.apply {
                adapter = classesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }


}