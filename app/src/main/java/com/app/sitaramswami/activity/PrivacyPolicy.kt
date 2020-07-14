package com.app.sitaramswami.activity

import android.text.Html
import android.util.Log
import android.view.MenuItem
import com.app.sitaramswami.*
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyPolicy : BaseActivity(),RetrofitListener {

    override fun onError(message: String?, fromCalling: Int) {
        Log.d("response", message)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_privacy_policy
    }

    override fun initialize() {
        toolbar.title = "Privacy Policy"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        RequestCall().get(Constants.Privacy_Policy, this, 1)

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
    override fun onResponse(response: JsonElement, fromCalling: Int) {
        if (response != null) {

            var json = response.asJsonObject
            if (json.get("status").asInt == 1) {
                val mimeType = "text/html"
                val encoding = "UTF-8"
                content.getSettings().setJavaScriptEnabled(true);
                content.loadDataWithBaseURL("", json.get("data").asString, mimeType, encoding, "");
              //  content.text=Html.fromHtml(json.get("data").asString)

            } else {
                CommonUtil.showDialog(this, json.get("message").asString, false)
            }
        }

        }


}
