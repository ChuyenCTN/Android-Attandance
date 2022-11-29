package com.hust.attandance.ui.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hust.attandance.AppNavigationDirections
import com.hust.attandance.databinding.FragmentSplashBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.welcome.WelcomeViewModel
import com.hust.attandance.utils.extensions.safeNavigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseViewBindingFragment<FragmentSplashBinding, WelcomeViewModel>(
    FragmentSplashBinding::inflate
) {
    override val viewModel by viewModel<WelcomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            navToMain.observe {
                viewLifecycleOwner.lifecycleScope.launch {
                    launch(Dispatchers.Main) {
                        delay(2000)
                        if (it) {
                            findNavController().safeNavigate(AppNavigationDirections.actionToMain())
                        } else {
                            findNavController().safeNavigate(SplashFragmentDirections.actionToWelcom())
                        }
                    }
                }

            }
        }
    }
}