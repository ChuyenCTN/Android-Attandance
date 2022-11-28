package com.hust.attandance.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hust.attandance.databinding.FragmentWelcomeBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.login.LoginViewModel
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseViewBindingFragment<FragmentWelcomeBinding, LoginViewModel>(
    FragmentWelcomeBinding::inflate
) {
    override val viewModel by viewModel<LoginViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnLogin.setSafeOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionToLogin())
        }
    }
}