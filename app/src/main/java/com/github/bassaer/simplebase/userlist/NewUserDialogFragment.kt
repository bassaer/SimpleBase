package com.github.bassaer.simplebase.userlist

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class NewUserDialogFragment: DialogFragment() {

    private var listener: NoticeDialogListener? = null

    interface NoticeDialogListener {
        fun onClickPositiveButton(input: String)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(com.github.bassaer.simplebase.R.layout.dialog_new_user, null)
            val editText: EditText = dialogView.findViewById(com.github.bassaer.simplebase.R.id.dialog_input)
            setView(dialogView)
            setMessage(getString(com.github.bassaer.simplebase.R.string.dialog_title))
            setPositiveButton(com.github.bassaer.simplebase.R.string.positive_button) { _, _ ->
                listener?.onClickPositiveButton(editText.text.toString())
            }
            setNegativeButton(com.github.bassaer.simplebase.R.string.negative_button) { _, _ ->
                // do nothing
            }
        }
        return builder.create()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(" must implement NoticeDialogListener")
        }
    }
}