package com.app.sitaramswami.activity

import android.content.Intent
import android.net.Uri

import android.support.design.widget.AppBarLayout
import android.view.MenuItem
import android.view.View
import com.app.sitaramswami.CommonUtil
import com.app.sitaramswami.Constants

import com.app.sitaramswami.R
import com.app.sitaramswami.apiservice.RequestCall
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import kotlinx.android.synthetic.main.activity_profile.*

import android.util.Log
import com.app.sitaramswami.ProfileResponse
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement



class Profile : BaseActivity(), View.OnClickListener, AppBarLayout.OnOffsetChangedListener, RetrofitListener {

  lateinit var phone_number:String;
    lateinit var watsapp_number:String;
    override fun getLayoutId(): Int {
        return R.layout.activity_profile
    }

    override fun initialize() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        appBar.addOnOffsetChangedListener(this)
        msg_icon.setOnClickListener(this)
        call.setOnClickListener(this)
        fav_icon.setOnClickListener(this)
        gmail.setOnClickListener(this)
        weblink.setOnClickListener(this)
        follow.setOnClickListener(this)
        whatsapp.setOnClickListener(this)
        progressBar.visibility=View.VISIBLE
        RequestCall().get(Constants.Profile, this, 1)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.msg_icon -> {
                val uri = Uri.parse("smsto:+919950466818")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
              //  intent.putExtra("sms_body", smsText)
                startActivity(intent)
            }
            R.id.fav_icon -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Facebook")
                        .putExtra("url", "https://www.facebook.com/sscarbike"))
            }
            R.id.gmail -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "sitaram.swami@gmail.com"))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "SS Car App")
                    startActivity(intent);
            }
            R.id.weblink -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Website")
                        .putExtra("url"," https://sscarbike.blogspot.in"))
            }
            R.id.follow ->{
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Facebook")
                        .putExtra("url", "https://www.facebook.com/SSCarBike"))
            }
            R.id.call -> {
                val i = Intent(Intent.ACTION_DIAL)
                val p = "tel:" + phone_number
                i.data = Uri.parse(p)
                call.context.startActivity(i)
            }
            R.id.whatsapp -> {
                val uri = Uri.parse("https://api.whatsapp.com/send?phone="+watsapp_number+"&text=Hello,%20from%20my%20Apps")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                whatsapp.context.startActivity(intent)
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

    var isShow = true
    var scrollRange = -1
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout!!.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset < 100) {
            collapsing_toolbar.setTitle("Profile & Contact");

            isShow = true;
        } else if(isShow) {
            collapsing_toolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
            isShow = false;
        }
    }
    override fun onResponse(response: JsonElement, fromCalling: Int) {
        progressBar.visibility=View.GONE
        if (response != null) {



                var ProfileResponse = Gson().fromJson<ProfileResponse>(response, ProfileResponse::class.java)
                Log.d("City",ProfileResponse.data.city.toString())
                Glide.with(applicationContext).load(ProfileResponse.data.image).
                        apply(RequestOptions.circleCropTransform()).into(image)
                phone_number=ProfileResponse.data.phone
                watsapp_number=ProfileResponse.data.w_number
                name.text=ProfileResponse.data.name
                city.text=ProfileResponse.data.city
                gmail.text=ProfileResponse.data.email
                weblink.text=ProfileResponse.data.website
                call.text=ProfileResponse.data.phone
                whatsapp.text=ProfileResponse.data.w_number


            val mimeType = "text/html"
            val encoding = "UTF-8"
            profile_txt.getSettings().setJavaScriptEnabled(true);
            profile_txt.loadDataWithBaseURL("",ProfileResponse.data.text, mimeType, encoding, "");
            } else {
              //  CommonUtil.showDialog(this, json.get("message").asString, false)
            }
        }


    override fun onError(message: String?, fromCalling: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
