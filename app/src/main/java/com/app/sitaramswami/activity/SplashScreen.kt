package com.app.sitaramswami.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.app.sitaramswami.CommonUtil
import com.app.sitaramswami.Constants
import com.app.sitaramswami.R
import com.app.sitaramswami.SessionManager
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.JsonElement

class SplashScreen : AppCompatActivity(), RetrofitListener {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000 //3 seconds
    lateinit var sessionManager: SessionManager
    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            sessionManager = SessionManager(this)
            if(sessionManager.getUser()!=null) {
                val intent = Intent(applicationContext, MyShop::class.java)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(applicationContext, MyShop::class.java) // login
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
        RequestCall().get(Constants.AdsId, this, 1)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
    override fun onResponse(response: JsonElement, fromCalling: Int) {
      //  Log.d("Id",response.asJsonObject.get("status").asString)
        if (response != null) {
            var json = response.asJsonObject
            if (json.get("status").asInt ==1) {
                SessionManager(this).saveIds(json.get("data").toString())
                Log.d("Id",json.get("data").toString())

            } else {
                CommonUtil.showDialog(this, json.get("message").asString, false)
            }
        }
    }


    override fun onError(message: String?, fromCalling: Int) {

    }


}
