package com.hust.attandance.ui.schedule

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hust.attandance.databinding.FragmentScheduleBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.main.MainFragmentDirections
import com.hust.attandance.utils.extensions.safeNavigate
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_students.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SchedulesFragment :
    BaseViewBindingFragment<FragmentScheduleBinding, ScheduleViewModel>(
        FragmentScheduleBinding::inflate
    ) {
    override val viewModel: ScheduleViewModel by viewModel()

    lateinit var adapter: SchedulesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {

            adapter = SchedulesAdapter {

            }.apply {
                rcvSchedule.adapter = this
                rcvSchedule.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        with(viewModel) {
            getSchedules()
            schedules.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.setData(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}