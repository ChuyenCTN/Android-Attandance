package com.hust.attandance.ui.classes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.hust.attandance.databinding.FragmentDetailClassesBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.main.MainFragmentDirections
import com.hust.attandance.utils.extensions.safeNavigate
import com.hust.attandance.utils.extensions.safeNavigateUp
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_detail_classes.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class DetailClassesFragment :
    BaseViewBindingFragment<FragmentDetailClassesBinding, DetailClassesViewModel>(
        FragmentDetailClassesBinding::inflate
    ) {
    override val viewModel by sharedViewModel<DetailClassesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            val managerAdapter = DetailClassManagerAdapter(this@DetailClassesFragment)
            pagerClass.adapter = managerAdapter
            pagerClass.isUserInputEnabled = false
            for (position in 0 until managerAdapter.itemCount) {
                val tab = tabClass.newTab()
                val title = when (position) {
                    DetailClassManagerAdapter.STUDENT_TAB_INDEX -> "Sinh viên"
                    else -> "Lịch học"
                }
                tab.customView = tabClass.inflateTabView(title)
                tabClass.addTab(tab, false)

                tabClass.addOnTabSelectedListener(onTabSelected)
            }
            btnBack.setSafeOnClickListener {
                findNavController().safeNavigateUp()
            }

            btnAttandance.setSafeOnClickListener {
                findNavController().safeNavigate(MainFragmentDirections.actionToAttandanceFragment())
            }
        }
    }

    private val onTabSelected = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            Timber.d("onTabSelected: $tab")
            viewBinding.apply {
                tab?.position?.let { position ->
                    tabClass.updateTabSelected(position)
                    pagerClass.setCurrentItem(position, true)
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

    private val onPageChanged = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Timber.d("onPageSelected: $position")
            super.onPageSelected(position)
            btnAttandance.text = if (position == 0) "Thêm sinh viên" else "Thêm lịch học"
            viewBinding.tabClass.selectTab(
                viewBinding.tabClass.getTabAt(position), true

            )
        }
    }

    override fun onDestroyView() {
        viewBinding.tabClass.removeOnTabSelectedListener(onTabSelected)
        viewBinding.pagerClass.adapter = null
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        viewBinding.pagerClass.registerOnPageChangeCallback(onPageChanged)
    }

    override fun onStop() {
        super.onStop()
        viewBinding.pagerClass.unregisterOnPageChangeCallback(onPageChanged)
    }

}