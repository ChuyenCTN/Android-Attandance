package com.hust.attandance.ui.login

import com.hust.attandance.databinding.FragmentLoginBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseViewBindingFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModel<LoginViewModel>()
}