package com.hust.attandance.ui.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hust.attandance.AppNavigationDirections
import com.hust.attandance.databinding.FragmentSplashBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.login.LoginViewModel
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseViewBindingFragment<FragmentSplashBinding, LoginViewModel>(
    FragmentSplashBinding::inflate
) {
    override val viewModel by viewModel<LoginViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnLoginAction.setSafeOnClickListener {
            findNavController().navigate(AppNavigationDirections.actionToMain())
        }
    }
}