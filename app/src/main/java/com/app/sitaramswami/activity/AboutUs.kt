package com.app.sitaramswami.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.app.sitaramswami.*
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUs : BaseActivity(),View.OnClickListener, RetrofitListener  {
    override fun onError(message: String?, fromCalling: Int) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_about_us
    }


    override fun initialize() {
        toolbar.title = "About Us"
        setSupportActionBar(toolbar)
        exp_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        sessionManager = SessionManager(this)
        RequestCall().get(Constants.ABOUT_IMAGES, this, 1)
        RequestCall().get(Constants.ABOUT_TEXT, this, 2)


        facebook.setOnClickListener(this)
        google.setOnClickListener(this)
        twitter.setOnClickListener(this)
//        youtube.setOnClickListener(this)
        linkdin.setOnClickListener(this)
        pinterest.setOnClickListener(this)
        blogger.setOnClickListener(this)
        flickr.setOnClickListener(this)
        instagram.setOnClickListener(this)
        behance.setOnClickListener(this)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (sessionManager.getIds() != null) {
            var mobileAdId = sessionManager.getIds()!!.mobile_add_id
      //  var mobileAdId = "ca-app-pub-6981915153700667/2441931636"
            Log.d("mobileId", mobileAdId)
            MobileAds.initialize(this, mobileAdId)
            val adView = AdView(this)
            adView.adSize = AdSize.BANNER
            adView.adUnitId = mobileAdId
          // adviewaboutus.addView(adView);
            if (adView != null) {
                val adRequest = AdRequest.Builder().build()
                adView.loadAd(adRequest)

            }
        }
    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.google -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Google")
                        .putExtra("url", "https://plus.google.com/+SSCarBike"))
            }
            R.id.facebook -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Facebook")
                        .putExtra("url", "https://www.facebook.com/sscarbike"))
            }
            R.id.twitter -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Twitter")
                        .putExtra("url", "https://twitter.com/sscarbike"))
            }
//            R.id.youtube -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Youtube")
//                        .putExtra("url", "https://www.youtube.com/user/sitaramsawami/videos "))
//            }
            R.id.pinterest -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Pinterest")
                        .putExtra("url", "https://in.pinterest.com/sscarbike"))

            }
            R.id.blogger -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Blogger")
                        .putExtra("url", "https://sscarbike.blogspot.in"))

            }
            R.id.linkdin -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Linkdin")
                        .putExtra("url", " https://www.linkedin.com/in/sscarbike/"))

            }
            R.id.flickr -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Flickr")
                        .putExtra("url", "  https://www.flickr.com/photos/sscarbike/"))

            }
            R.id.instagram -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Instagram")
                        .putExtra("url", "https://www.instagram.com/jaipurphotoshop/"))

            }
            R.id.behance -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Behance")
                        .putExtra("url", "https://www.behance.net/jaipurphotoshop"))

            }
        }

    }


    override fun onResponse(response: JsonElement?, fromCalling: Int) {
        when(fromCalling)
        {
            1->{
                var bannerListResponse = Gson().fromJson<BannerListResponse>(response, BannerListResponse::class.java)
                if (bannerListResponse.status==1) {
                    Log.d("response", bannerListResponse.data.toString())
                    slider.setSliderImages(bannerListResponse.data.images)
                    var adapter = AboutUsAdapter(bannerListResponse.data.exp)
                    exp_list.adapter = adapter
                }


            }
            2->{
                var bannerListResponse = Gson().fromJson<BannerListResponse>(response, BannerListResponse::class.java)
                if (bannerListResponse.status==1) {
                    Log.d("response", bannerListResponse.data.toString())
                    val mimeType = "text/html"
                    val encoding = "UTF-8"
                    tv_about.getSettings().setJavaScriptEnabled(true);
                    tv_about.loadDataWithBaseURL("", bannerListResponse.data.about, mimeType, encoding, "");
                   // tv_about.setText(Html.fromHtml(bannerListResponse.data.about))
                }

            }

        }

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
