package com.hust.attandance.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hust.attandance.AppNavigationDirections
import com.hust.attandance.MainNavigationDirections
import com.hust.attandance.databinding.FragmentHomeBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.utils.extensions.safeNavigate
import com.hust.attandance.utils.extensions.setSafeOnClickListener
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
            findNavController().safeNavigate(MainNavigationDirections.actionToClassDetail(Gson().toJson(it)))
        }
        viewBinding.apply {
            rcvClass.apply {
                adapter = classesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

        }

        with(viewModel) {
            getData()
            classes.observe(viewLifecycleOwner) {
                if (it != null) {
                    classesAdapter.setData(it)
                    classesAdapter.notifyDataSetChanged()
                }
            }
        }
    }


}