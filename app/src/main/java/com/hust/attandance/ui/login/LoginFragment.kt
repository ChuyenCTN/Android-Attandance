package com.hust.attandance.ui.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.hust.attandance.AppNavigationDirections
import com.hust.attandance.databinding.FragmentLoginBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.utils.extensions.safeNavigate
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import com.hust.attandance.utils.extensions.setupTextChangedAction
import com.hust.attandance.utils.extensions.showErrorSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseViewBindingFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {
            etPasswordLogin.setupTextChangedAction(true)
            etUsernameLogin.setupTextChangedAction(true)
            etPasswordLogin.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnLogin.performClick()
                    true
                } else false
            }

            etUsernameLogin.doAfterTextChanged {
                viewModel.userName = it.toString()
            }

            etPasswordLogin.doAfterTextChanged {
                viewModel.password = it.toString()
            }

            btnLogin.setSafeOnClickListener {
                if (viewModel.userName.isEmpty()) {
                    showErrorSnackBar("Vui lòng nhập tài khoản!")
                    return@setSafeOnClickListener
                }
                if (viewModel.password.isEmpty()) {
                    showErrorSnackBar("Vui lòng nhập mật khẩu!")
                    return@setSafeOnClickListener
                }
                showProcessing()
                viewModel.login()
            }
        }
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            loginSuccessful.observe {
                hideProcessing()
                if (it) {
                    findNavController().safeNavigate(AppNavigationDirections.actionToMain())
                } else {
                    showErrorSnackBar("Thông tin tài khoản hoặc mật khẩu không đúng. Vui lòng thử lại!")
                }
            }
        }
    }
}