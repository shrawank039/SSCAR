package com.app.sitaramswami.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.sitaramswami.ChangePasswordData
import com.app.sitaramswami.CommonUtil
import com.app.sitaramswami.Constants
import com.app.sitaramswami.R
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePassword : AppCompatActivity(), View.OnClickListener, RetrofitListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        bt_change.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (validateForm()) {
            progressBar.visibility = View.VISIBLE
            var changePassword = ChangePasswordData(email.text.toString(), password.text.toString(), varify.text.toString())
            var data = Gson().toJsonTree(changePassword)
            RequestCall().post(Constants.CHANGE_PASS, data, this, 1)
        }
    }

    private fun validateForm(): Boolean {

        return when {
            !CommonUtil.isValidEmail(email.text.toString()) -> {
                email.setError("Invaild email")
                false
            }
            password.text.length <= 0 -> {
                password.setError("password must not be empty")
                false
            }
            varify.text.length <= 0 -> {
                password.setError("Verify password must not be empty")
                false
            }

            !password.text.toString().equals(varify.text.toString()) -> {
                Toast.makeText(this@ChangePassword, "Password do not match", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    override fun onResponse(response: JsonElement, fromCalling: Int) {
        var json = response.asJsonObject
        progressBar.visibility = View.GONE
        if (json.get("status").asInt > 0) {
            CommonUtil.showDialog(this, json.get("message").asString, true)
        } else {
            CommonUtil.showDialog(this, json.get("message").asString, false)
        }
    }

    override fun onError(message: String, fromCalling: Int) {
        progressBar.visibility = View.GONE
    }


}
