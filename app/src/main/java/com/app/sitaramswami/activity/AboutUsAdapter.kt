package com.app.sitaramswami.activity

import android.support.v7.widget.RecyclerView
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.app.sitaramswami.R
import com.app.sitaramswami.SessionManager

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.about_us.view.*



/**
 * Created by kartik on 21-May-18.
 */
class AboutUsAdapter(lists: ArrayList<String>) : RecyclerView.Adapter<AboutUsAdapter.ViewHolder>() {
    var aboutList = lists

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.about_us, parent, false)
        return AboutUsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return aboutList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val exp = aboutList[position]

        val mimeType = "text/html"
        val encoding = "UTF-8"
        holder.exp.getSettings().setJavaScriptEnabled(true);
  //      holder.exp.setWebViewClient(CustomWebViewClient())
        holder.exp.loadDataWithBaseURL("", exp, mimeType, encoding, "");

        var sessionManager: SessionManager
//            Log.d("Image", shopListResponse.images[0])
        sessionManager = SessionManager(holder.adviewabt.context)
        if (sessionManager.getIds() != null) {
            var mobileAdId = sessionManager.getIds()!!.mobile_add_id

            Log.d("mobileId", mobileAdId)
            MobileAds.initialize(holder.adviewabt.context, mobileAdId)
            val adView = AdView(holder.adviewabt.context)
            adView.adSize = AdSize.BANNER
            adView.adUnitId = mobileAdId
            holder.adviewabt.addView(adView);
            if (adView != null) {
                val adRequest = AdRequest.Builder().build()
                adView.loadAd(adRequest)

            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var exp = view.about_exp
        var adviewabt=view.adviewabout
    }

//    private inner class CustomWebViewClient : WebViewClient() {
//
//        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//
//            view.loadUrl(url)
//
//            return true
//        }

 //   }

}
