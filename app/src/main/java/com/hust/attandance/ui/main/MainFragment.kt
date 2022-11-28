package com.hust.attandance.ui.main

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hust.attandance.R
import com.hust.attandance.databinding.FragmentMainBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseViewBindingFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    override val viewModel by viewModel<MainViewModel>()
    private lateinit var mainAdapter: MainAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navView: BottomNavigationView = viewBinding.navView


        mainAdapter = MainAdapter(this)
        viewBinding.apply {
            mainViewPager.apply {
                offscreenPageLimit = 4
                isUserInputEnabled = false
                adapter = mainAdapter
            }
        }

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    0
                }
                R.id.nav_schedule -> {
                    1
                }
                R.id.nav_profile -> {
                    2
                }
                else -> 0
            }.let {
                mainViewPager.currentItem = it
            }
            true
        }


    }

    override fun onStart() {
        super.onStart()
        viewBinding.mainViewPager.registerOnPageChangeCallback(onPageChanged)
    }

    override fun onStop() {
        super.onStop()
        viewBinding.mainViewPager.unregisterOnPageChangeCallback(onPageChanged)
    }

    private val onPageChanged = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            /*MainActivity.IS_CONVERSATION_LIST = (position == homeAdapter.conversationTab)
            if (position == homeAdapter.notificationTab) {
                if (mainViewModel.usageFeature == UsageFeatureEnum.FACEBOOK_ONLY) {
                    viewModel.setSeenAllFBNotification()
                }
                if (mainViewModel.usageFeature == UsageFeatureEnum.OMNI_ONLY) {
                    viewModel.setSeenAllOmniNotification()
                }
            }*/
        }
    }

}

