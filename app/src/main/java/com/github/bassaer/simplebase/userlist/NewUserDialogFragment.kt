package com.github.bassaer.simplebase.userlist

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.github.bassaer.simplebase.R

class NewUserDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setMessage(getString(R.string.dialog_title))
            setPositiveButton(R.string.positive_button) { _, _ ->
                // create user
            }
            setNegativeButton(R.string.negative_button) {_, _ ->
                // do nothing
            }
        }
        return builder.create()
    }
}