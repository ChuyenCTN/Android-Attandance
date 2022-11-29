package com.hust.attandance.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hust.attandance.databinding.FragmentHomeBinding

class HomeFragment : Fragment() { // class danh sach lop

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!


    companion object {
        @JvmStatic
        fun newInstance(
        ) =
            HomeFragment().apply {
//                arguments = bundleOf(
//                    ARG_BRANCH_IDS to branchIds.toLongArray(),
//                )
            }

        const val ARG_BRANCH_IDS = "arg_branch_ids"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}