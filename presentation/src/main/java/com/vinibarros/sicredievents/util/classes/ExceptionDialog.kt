package com.vinibarros.sicredievents.util.classes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.vinibarros.sicredievents.R

class ExceptionDialog(val exception: Exception) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.title_error_dialog)
        builder.setMessage(exception.message)
        builder.setPositiveButton(R.string.label_close) { dialog, _ -> dialog?.dismiss() }
        return builder.create()
    }
}