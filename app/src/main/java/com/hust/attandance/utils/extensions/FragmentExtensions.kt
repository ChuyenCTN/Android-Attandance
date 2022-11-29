package com.hust.attandance.utils.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.hust.attandance.R
import com.hust.attandance.ui.common.customviews.snackbar.SnackbarType
import com.hust.attandance.ui.common.customviews.snackbar.StateSnackbar
import com.hust.attandance.utils.helpers.UIUtils

//inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
//    @IdRes navGraphId: Int,
//    qualifier: Qualifier? = null,
//    noinline parameters: ParametersDefinition? = null
//) = lazy {
//    val owner = findNavController().getViewModelStoreOwner(navGraphId)
//    getKoin().getViewModel(
//        ViewModelParameter(
//            VM::class,
//            qualifier,
//            parameters,
//            Bundle(),
//            owner.viewModelStore,
//            null
//        )
//    )
//}
//
//inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
//    qualifier: Qualifier? = null,
//    noinline parameters: ParametersDefinition? = null
//) = lazy {
//    val owner = findNavController().getViewModelStoreOwner(findNavController().graph.id)
//
//    getKoin().getViewModel(
//        qualifier = qualifier,
//        state = null,
//        owner = { ViewModelOwner.from(owner) },
//        clazz = VM::class,
//        parameters = parameters
//    )
//}

fun Fragment.openFacebookUrl(url: String) {
    val packageManager = requireContext().packageManager
    val facebookLink = try {
        val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
        if (versionCode >= 3002850) {
            "fb://facewebmodal/f?href=$url"
        } else {
            url
        }
    } catch (e: PackageManager.NameNotFoundException) {
        url
    }
    try {
        val facebookIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookLink))
        startActivity(facebookIntent)
    } catch (ex: ActivityNotFoundException) {
    }
}

fun Fragment.openFacebookReferenceId(id: String) {
    val referenceLink = "https://www.facebook.com/$id"
    val packageManager = requireContext().packageManager
    val facebookLink = try {
        val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
        if (versionCode >= 3002850) {
            "fb://facewebmodal/f?href=$referenceLink"
        } else {
            referenceLink
        }
    } catch (e: PackageManager.NameNotFoundException) {
        referenceLink
    }
    try {
        val facebookIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookLink))
        startActivity(facebookIntent)
    } catch (ex: ActivityNotFoundException) {
    }
}

fun Fragment.showToast(@StringRes id: Int) = showToast(getString(id))

fun Fragment.showToast(text: String) =
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

fun Fragment.showToastGeneralError() = showToast(getString(R.string.error_unknow))

fun Fragment.showSuccessSnackBar(textResId: Int) {
    showSuccessSnackBar(requireActivity().getString(textResId))
}

fun Activity.showSuccessSnackBar(textResId: Int) {
    showSuccessSnackBar(getString(textResId))
}

fun Fragment.showSuccessSnackBar(text: String) {
    activity?.let {
        StateSnackbar.make(
            it.findViewById(android.R.id.content),
            text,
            SnackbarType.SUCCESS
        )?.show()
    }

}

fun Activity.showSuccessSnackBar(text: String) {
    StateSnackbar.make(
        findViewById(android.R.id.content),
        text,
        SnackbarType.SUCCESS
    )?.show()
}

fun Fragment.showErrorSnackBar(textResId: Int) {
    showErrorSnackBar(requireActivity().getString(textResId))
}

fun Fragment.showErrorSnackBar(text: String) {
    activity?.let {
        StateSnackbar.make(
            it.findViewById(android.R.id.content),
            text,
            SnackbarType.ERROR
        )?.show()
    }
}

fun Fragment.setBooleanResultListener(
    fragment: Fragment?,
    key: String,
    onResult: (result: Boolean) -> Unit
) {
    fragment?.setFragmentResultListener(key) { _, bundle ->
        onResult.invoke(bundle.getBoolean(key))
    }
}

fun Fragment.showNoticeDialog(message: String) {
    UIUtils.buildAlertDialog(requireContext(),
        getString(R.string.error_common_error_title),
        message,
        getString(R.string.label_ok_popup),
        { dialog, _ ->
            dialog.dismiss()
        }
    ).show()
}
