package com.app.sitaramswami.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.sitaramswami.CommonUtil
import com.app.sitaramswami.Constants
import com.app.sitaramswami.Email
import com.app.sitaramswami.R
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity(), View.OnClickListener, RetrofitListener {
    var process = 1
    override fun onResponse(response: JsonElement, fromCalling: Int) {
        progressBar.visibility = View.GONE
        var json = response.asJsonObject
        when (fromCalling) {
            1 -> {

                if (json.get("status").asInt > 0) {
                    email.text.clear()
                    forgot.text="Verify"
                    email.hint = "Enter Varification Code"
                    process = 2
                }
                CommonUtil.showDialog(this, json.get("message").asString, false)
            }

            2 -> {
                if (json.get("status").asInt > 0) {
                    var intent=CommonUtil.createIntent(this,ChangePassword::class.java,false)
                    startActivity(intent)
                    finish()
                } else {
                    CommonUtil.showDialog(this, json.get("message").asString, false)
                }
            }
        }
    }

    override fun onError(message: String, fromCalling: Int) {
        progressBar.visibility = View.GONE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        forgot.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.forgot) {
            when (process) {
                1 -> {
                    if (email.text.length > 0 && CommonUtil.isValidEmail(email.text.toString())) {
                        progressBar.visibility = View.VISIBLE
                        var emailString= Gson().toJsonTree(Email(email.text.toString()))
                      //  var emailString = Gson().toJsonTree("\"email\":"+email.text.toString()+"}")
                        RequestCall().post(Constants.FORGOT_PASSWORD, emailString, this, 1)
                    } else {
                        email.setError("Email is not vaild")
                    }
                }

                2 -> {
                    if (email.text.length > 0) {
                        progressBar.visibility = View.VISIBLE
                        var emailString = Gson().toJsonTree(email.text.toString())
                        RequestCall().post(Constants.CHECK_OTP, emailString, this, 2)
                    } else {
                        email.setError("Not Empty")
                    }

                }

            }

        }
    }
}
