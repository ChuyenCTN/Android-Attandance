package com.hust.attandance.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hust.attandance.R
import com.hust.attandance.utils.widgets.KvLoadingDialog

open class BaseBottomSheetFragment(@LayoutRes val resLayout: Int): BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(resLayout, container, false)
    }

    fun showProcessing() {
        KvLoadingDialog.getDialog(requireContext(), R.string.label_processing_content).show()
    }

    fun hideProcessing() {
        KvLoadingDialog.dismiss(requireContext())
    }
}