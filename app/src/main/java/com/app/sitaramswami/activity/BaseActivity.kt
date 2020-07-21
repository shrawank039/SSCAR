package com.app.sitaramswami.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.app.sitaramswami.SessionManager
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.add_layout.*


/**
 * Created by kartik on 21-May-18.
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd
    abstract fun getLayoutId(): Int
    lateinit var sessionManager: SessionManager

//    lateinit var shopicon: TextView
    abstract fun initialize()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        sessionManager = SessionManager(this)
        if(sessionManager.getIds()!=null) {
            Log.d("Id", sessionManager.getIds().toString())
            var addId = sessionManager.getIds()!!.add_id
            var mobileAdId = sessionManager.getIds()!!.mobile_add_id

//            var addId = "ca-app-pub-6981915153700667/7502686623"
//          var mobileAdId ="ca-app-pub-6981915153700667/1851684948"


            mInterstitialAd = InterstitialAd(this)
            mInterstitialAd.adUnitId = addId
            mInterstitialAd.loadAd(AdRequest.Builder().build())

            mInterstitialAd.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {

                        val handler = Handler()
                        handler.postDelayed(Runnable { // Do something after 5s = 5000ms
                            mInterstitialAd.show();
                        }, 3000)

                    }
                }
            }

            MobileAds.initialize(this, mobileAdId)
            val adview = AdView(this)
            adview.adSize = AdSize.BANNER
            adview.adUnitId = mobileAdId
            adView.addView(adview);
            if (adview != null) {
                val adRequest = AdRequest.Builder().build()
                adview.loadAd(adRequest)

            }
       }
        initialize()
    }
}