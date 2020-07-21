package com.app.sitaramswami.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.sitaramswami.*
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity(), View.OnClickListener, RetrofitListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUp.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        if (p0?.id == R.id.signUp)
        {
            if (validateForm()) {
                signUp.isEnabled = false
                progressBar.visibility = View.VISIBLE
                var loginData = SignUpData(name.text.toString(), email.text.toString(), phone.text.toString(), password.text.toString())
                var enquirySting = Gson().toJsonTree(loginData, SignUpData::class.java)
                RequestCall().post(Constants.SIGNUP, enquirySting, this,1)
            }
        }
    }
    fun validateForm(): Boolean {
        return when {
            name.text.length <= 0 -> {
                name.setError("Name must not be empty")
                false
            }

            !CommonUtil.isValidEmail(email.text.toString()) -> {
                email.setError("Invaild email")
                false
            }
            password.text.length <= 0 -> {
                password.setError("password must not be empty")
                false
            }
            else -> true
        }

    }

    override fun onResponse(response: JsonElement, fromCalling: Int) {
        signUp.isEnabled = true
        progressBar.visibility = View.GONE
        if (response != null) {
            var json = response.asJsonObject
            if (json.get("status").asInt > 0) {
                SessionManager(this).saveUser(json.get("data").toString())
                var intent = CommonUtil.createIntent(this, HomePage::class.java, true)
                startActivity(intent)
                finish()
            } else {
                CommonUtil.showDialog(this, json.get("message").asString, false)
            }
        }
    }

    override fun onError(message: String, fromCalling: Int) {
        signUp.isEnabled = true
        progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
