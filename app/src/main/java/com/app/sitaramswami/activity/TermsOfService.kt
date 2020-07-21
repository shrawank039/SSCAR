package com.app.sitaramswami.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import com.app.sitaramswami.CommonUtil
import com.app.sitaramswami.Constants
import com.app.sitaramswami.R
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_terms_of_service.*

class TermsOfService : BaseActivity(),RetrofitListener {

    override fun onError(message: String?, fromCalling: Int) {
        Log.d("response", message)
    }

    override fun onResponse(response: JsonElement?, fromCalling: Int) {
        if (response != null) {

            var json = response.asJsonObject
            if (json.get("status").asInt == 1) {

                val mimeType = "text/html"
                val encoding = "UTF-8"
                terms.getSettings().setJavaScriptEnabled(true);

                terms.loadDataWithBaseURL("",json.get("data").asString, mimeType, encoding, "");
           //     terms.text= Html.fromHtml(json.get("data").asString)
                Log.d("response", json.get("data").asString)

            } else {
                CommonUtil.showDialog(this, json.get("message").asString, false)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_terms_of_service
    }

    override fun initialize() {
        toolbar.title = "Terms & Conditions"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        RequestCall().get(Constants.Terms_Condition, this, 1)
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
}
