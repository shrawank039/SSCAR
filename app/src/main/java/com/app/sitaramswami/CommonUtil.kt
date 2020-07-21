package com.app.sitaramswami

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.util.Patterns
import android.text.TextUtils


/**
 * Created by kartik on 06-May-18.
 */
class CommonUtil {

    companion object {
        fun showDialog(context: Context, msg: String, goBack: Boolean) {
            AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle("Message")
                    .setMessage(msg)
                    .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                        if (goBack)
                            (context as Activity).finish()
                        else
                            dialogInterface.dismiss()
                    }).show()


        }

        fun isValidEmail(target: String): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

        fun <T> createIntent(context: Context, toClass: Class<T>, newTask: Boolean) :Intent {
            var intent = Intent(context, toClass)
            if (newTask) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
            }
            return intent

        }
    }
}