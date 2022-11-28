package com.hust.attandance.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hust.attandance.databinding.FragmentNotificationsBinding
import com.hust.attandance.ui.home.HomeFragment

class ProfileFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(
        ) =
            ProfileFragment().apply {
//                arguments = bundleOf(
//                    ARG_BRANCH_IDS to branchIds.toLongArray(),
//                )
            }

        const val ARG_BRANCH_IDS = "arg_branch_ids"
    }


    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}