package com.github.bassaer.simplebase.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.github.bassaer.simplebase.R

class CheckDialogFragment: DialogFragment() {

    var listener: CheckDialogListener? = null

    public interface CheckDialogListener {
        fun onClickPositiveButton()
        fun onClickNegativeButton()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setMessage(arguments?.getString(ARGUMENT_TITLE))
            setPositiveButton(R.string.positive_button) { _, _ ->
                listener?.onClickPositiveButton()
            }
            setNegativeButton(R.string.negative_button) { _, _ ->
                listener?.onClickNegativeButton()
            }
        }
        return builder.create()
    }

    companion object {
        private const val ARGUMENT_TITLE = "DIALOG_TITLE"
        fun newtInstance(title: String) =
                CheckDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARGUMENT_TITLE, title)
                    }
                }
    }
}