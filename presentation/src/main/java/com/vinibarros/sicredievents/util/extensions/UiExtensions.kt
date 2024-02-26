package com.vinibarros.sicredievents.util.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.vinibarros.sicredievents.util.classes.ExceptionDialog
import com.vinibarros.sicredievents.util.classes.LiveEvent

fun Fragment.errorObserver(callback: () -> Unit = {}) =
    Observer<LiveEvent<Exception?>> { exception ->
        callback()
        activity?.supportFragmentManager?.let {
            exception.getContentIfNotHandled()?.let { ex ->
                ExceptionDialog(ex).show(it, tag)
            }
        }
    }

fun Fragment.loadingObserver(loadingViewId: Int) = Observer<LiveEvent<Boolean>> { loading ->
    when (loading.getContentIfNotHandled()) {
        true -> showLoadingView(loadingViewId)
        false -> hideLoadingView(loadingViewId)
        else -> {}
    }
}


fun Fragment.showLoadingView(loadingViewId: Int) {
    view?.findViewById<View>(loadingViewId)?.visibility = View.VISIBLE
}


fun Fragment.hideLoadingView(loadingViewId: Int) {
    view?.findViewById<View>(loadingViewId)?.visibility = View.GONE
}