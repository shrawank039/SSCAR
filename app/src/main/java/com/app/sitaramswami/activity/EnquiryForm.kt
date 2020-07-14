package com.app.sitaramswami.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.app.sitaramswami.*
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_enquiry_form.*

class EnquiryForm : BaseActivity(), View.OnClickListener, RetrofitListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_enquiry_form
    }

    override fun initialize() {
        toolbar.title = "Required Car & Bike"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        submit.setOnClickListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(p0: View?) {
        if (sessionManager.getUser() != null) {
            if (p0?.id == R.id.submit && validation()) {
                submit.isEnabled = false

                var enquiryData = EnquiryData(name.text.toString(), email.text.toString(),
                        phone.text.toString(), location.text.toString(), city.text.toString(), state.text.toString(),
                        vehicle.text.toString(), msg.text.toString(), SessionManager(this).getUser()!!.id.toString())
                var enquirySting = Gson().toJsonTree(enquiryData, EnquiryData::class.java)
                progressBar.visibility = View.VISIBLE
                RequestCall().post(Constants.SUBMIT_ENQUIRY, enquirySting, this, 1)
            } else {
                Toast.makeText(this, "All feilds are mendatory", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun validation(): Boolean {
        return when {
            name.text.length == 0 -> false
            email.text.length == 0 -> false
            phone.text.length == 0 -> false
            location.text.length == 0 -> false
            city.text.length == 0 -> false
            state.text.length == 0 -> false
            vehicle.text.length == 0 -> false
            msg.text.length == 0 -> false
            else -> true
        }
    }

    override fun onResponse(response: JsonElement?, fromCalling: Int) {
        submit.isEnabled = true
        progressBar.visibility = View.GONE
        if (response != null) {
            var json = response.asJsonObject
            if (json.get("status").asInt > 0) {
                CommonUtil.showDialog(this, json.get("message").asString, true)
            } else {
                Toast.makeText(this, json.get("message").asString, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onError(message: String, fromCalling: Int) {
        submit.isEnabled = true
        progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
