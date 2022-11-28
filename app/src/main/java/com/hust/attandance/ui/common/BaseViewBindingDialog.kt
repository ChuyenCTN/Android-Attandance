package com.hust.attandance.ui.common

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.hust.attandance.utils.extensions.getScreenWidth
import com.hust.attandance.utils.extensions.hideKeyboard
import kotlin.math.min

abstract class BaseViewBindingDialog<T : ViewBinding>(private val initVb: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    DialogFragment() {
    companion object {
        val TAG = "Dialog"
    }

    private var _viewBinding: T? = null
    val viewBinding: T
        get() = _viewBinding!!

    protected open val screenWidthPercent = 0.9F

    open fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = initVb.invoke(inflater, container, false)
        return viewBinding.root
    }

    override fun onResume() {
        val window = dialog?.window ?: return super.onResume()
        val width = min(
            requireActivity().getScreenWidth() * screenWidthPercent,
            dp2px(requireActivity(), 400F)
        )

        window.setLayout(width.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun dismiss() {
        view?.hideKeyboard()
        super.dismiss()
    }

    private fun dp2px(context: Context?, dp: Float): Float {
        if (context == null) return dp
        val scale = context.resources.displayMetrics.density
        return dp * scale + 0.5f
    }
}