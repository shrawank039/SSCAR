package com.app.sitaramswami.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.app.sitaramswami.*
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_feedback.*

class Feedback : BaseActivity(), View.OnClickListener, RetrofitListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_feedback
    }

    override fun initialize() {
        toolbar.title = "Help & Feedback"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      //  Log.d("Id", SessionManager(this).getUser()!!.id.toString());
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

                var feedBackData = FeedBackData(name.text.toString(), email.text.toString(),
                        phone.text.toString(), msg.text.toString(), SessionManager(this).getUser()!!.id.toString())
                var enquirySting = Gson().toJsonTree(feedBackData, FeedBackData::class.java)
                progressBar.visibility = View.VISIBLE
                RequestCall().post(Constants.SUBMIT_FEEDBACK, enquirySting, this, 1)
            }
            else{
            Toast.makeText(this, "All feilds are mendatory", Toast.LENGTH_SHORT).show()

            }
        }else {
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
            msg.text.length == 0 -> false
            else -> true
        }
    }
    override fun onResponse(response: JsonElement, fromCalling: Int) {
        submit.isEnabled=true
        progressBar.visibility=View.GONE
        if (response != null) {
            var json = response.asJsonObject
            if (json.get("status").asInt > 0) {
                CommonUtil.showDialog(this, json.get("message").asString,true)
            } else {
                CommonUtil.showDialog(this, json.get("message").asString,false)
            }
        }
    }

    override fun onError(message: String, fromCalling: Int) {
        submit.isEnabled=true
        progressBar.visibility=View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
