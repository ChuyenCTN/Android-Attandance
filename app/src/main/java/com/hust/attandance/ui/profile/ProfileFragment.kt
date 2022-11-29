package com.hust.attandance.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.hust.attandance.AppNavigationDirections
import com.hust.attandance.R
import com.hust.attandance.databinding.FragmentProfileBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.utils.extensions.safeNavigate
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import com.hust.attandance.utils.extensions.showImageAvatarProfile
import com.hust.attandance.utils.helpers.UIUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseViewBindingFragment<FragmentProfileBinding, ProfileViewModel>(
    FragmentProfileBinding::inflate
) {
    override val viewModel by viewModel<ProfileViewModel>()

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment().apply {
//                arguments = bundleOf(
//                    ARG_BRANCH_IDS to branchIds.toLongArray(),
//                )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            profileResponse.observe {
                it?.let { profile ->
                    viewBinding.apply {
                        profile.image?.let {
                            imgAvatar.showImageAvatarProfile(it)
                        } ?: kotlin.run {
                            imgAvatar.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.easter_bunny
                                )
                            )
                        }
                        tvName.text = profile.userName ?: ""
                        tvCode.text = profile.userId.toString()
                        tvAddress.text = profile.address ?: ""
                    }
                }
            }
        }

        viewBinding.apply {
            btnLogout.setSafeOnClickListener {
                UIUtils.buildAlertDialog(
                    requireContext(),
                    title = "Thông báo",
                    message = "Bạn có chắc muốn đăng xuất tài khoản?",
                    positiveText = "Đăng xuất",
                    negativeText = "Không",
                    onPositiveClick = { dialog, a ->
                        viewModel.logout()
                        findNavController().safeNavigate(AppNavigationDirections.actionBackLogin())
                    },
                    onNegativeClick = null
                ).show()

            }
        }
    }


}