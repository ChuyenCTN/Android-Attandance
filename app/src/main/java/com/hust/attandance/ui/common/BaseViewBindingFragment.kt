package com.hust.attandance.ui.common

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.hust.attandance.R
import com.hust.attandance.utils.exceptions.hideKeyboard
import com.hust.attandance.utils.helpers.UIUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import com.hust.attandance.utils.exceptions.KvException
import com.hust.attandance.utils.widgets.KvLoadingDialog

abstract class BaseViewBindingFragment<T : ViewBinding, VM : BaseViewModel>(private val initVb: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    Fragment() {
    companion object {
        val TAG = "Dialog"
    }

    private var _viewBinding: T? = null
    val viewBinding: T
        get() = _viewBinding!!

    abstract val viewModel: VM
    private var customErrorHandler: ((exception: KvException) -> Unit)? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = initVb.invoke(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Always hide keyboard and processing layout when enter a new screen
        hideProcessing()
        requireContext().hideKeyboard(view)

        viewModel.exception.observe(viewLifecycleOwner) {
            hideProcessing()
            if (customErrorHandler == null) {
                UIUtils.showException(requireContext(), it)
            } else {
                customErrorHandler?.invoke(it)
            }
        }
    }

    fun addCustomErrorHandler(block: ((exception: KvException) -> Unit)) {
        customErrorHandler = block
    }

    fun showProcessing(msgSynchronizing: Int = R.string.label_processing_content) {
        KvLoadingDialog.getDialog(requireContext(), msgSynchronizing).show()
    }

    fun hideProcessing() {
        KvLoadingDialog.dismiss(requireContext())
    }

    fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(viewLifecycleOwner) {
            it?.let {
                block.invoke(it)
            }
        }
    }

    fun requireApplication(): Application = requireActivity().application
}
