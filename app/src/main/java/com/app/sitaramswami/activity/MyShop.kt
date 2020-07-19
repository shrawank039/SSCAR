package com.app.sitaramswami.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.app.sitaramswami.*
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener

import com.google.gson.Gson
import com.google.gson.JsonElement


import kotlinx.android.synthetic.main.activity_my_shop.*
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import java.util.*


class MyShop : HomeBaseActivity(), RetrofitListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    var filterdNames: ArrayList<ShopListResponse> = ArrayList()


    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_car) {
            progressBar.visibility = View.VISIBLE
            btn_car.setBackgroundColor(Color.parseColor("#FFF56708"))
            btn_car.setTextColor(Color.WHITE)
            btn_bike.setTextColor(Color.BLACK)
            val drawable = ContextCompat.getDrawable(this, R.drawable.whitecar)
            btn_car.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
            val drawable1 = ContextCompat.getDrawable(this, R.drawable.motorbike)
            btn_bike.setCompoundDrawablesWithIntrinsicBounds(drawable1, null, null, null)
            btn_bike.setBackgroundColor(Color.parseColor("#FFFFFF"))
//            Log.d("USerId", user_id)

            if (sessionManager.getUser() != null) {

                var user_id = SessionManager(this).getUser()!!.id.toString()
                Log.d("UserID", user_id)
                var data = UserId(user_id)
                var userId = Gson().toJsonTree(data)

                RequestCall().post(Constants.CAR_LIST, userId, this, 1)

            } else {

//                Log.d("UserID", "text")
                RequestCall().get(Constants.CAR_LIST, this, 1)
            }


        } else if (v?.id == R.id.btn_bike) {
            progressBar.visibility = View.VISIBLE
            btn_car.setBackgroundColor(Color.parseColor("#FFFFFF"))
            btn_bike.setTextColor(Color.WHITE)
            btn_car.setTextColor(Color.BLACK)
            btn_bike.setBackgroundColor(Color.parseColor("#FFF56708"))
            val drawable = ContextCompat.getDrawable(this, R.drawable.whitemotorbike)
            btn_bike.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
            val drawable1 = ContextCompat.getDrawable(this, R.drawable.car)
            btn_car.setCompoundDrawablesWithIntrinsicBounds(drawable1, null, null, null)
            if (sessionManager.getUser() != null) {
                var user_id = SessionManager(this).getUser()!!.id.toString()
                Log.d("UserID", user_id.toString())
                var data = UserId(user_id)
                var userId = Gson().toJsonTree(data)

                RequestCall().post(Constants.BIKE_LIST, userId, this, 1)

            } else {
                RequestCall().get(Constants.BIKE_LIST, this, 1)
            }
//            RequestCall().get(Constants.BIKE_LIST, this, 1)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_my_shop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_bike.setOnClickListener(this)
        btn_car.setOnClickListener(this)
    }

    override fun initialize() {
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var drawertoggel = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.openDrawer, R.string.closeDrawer)
//        sessionManager = SessionManager(this)
//
//        if (sessionManager.getUser() == null) {
//            startActivity(CommonUtil.createIntent(this, Login::class.java, true))
//            finish()
//        }

        drawertoggel.syncState()
        list.itemAnimator = DefaultItemAnimator()
        list.layoutManager = LinearLayoutManager(this)
        val navigationView = findViewById<View>(R.id.navigation) as NavigationView
        val nav_Menu: Menu = navigationView.getMenu()


        navigation.setNavigationItemSelectedListener(this)
        navigation.setItemIconTintList(null);
        sessionManager = SessionManager(this)

        progressBar.visibility = View.VISIBLE


        if (sessionManager.getUser() != null) {
            nav_Menu.findItem(R.id.logout).setVisible(true)
            var user_id = SessionManager(this).getUser()!!.id.toString()

            var data = UserId(user_id)
            var userId = Gson().toJsonTree(data)

            RequestCall().post(Constants.CAR_LIST, userId, this, 1)

        } else {
            nav_Menu.findItem(R.id.logout).setVisible(false)

            RequestCall().get(Constants.CAR_LIST, this, 1)
        }


    }

    override fun onResponse(response: JsonElement?, fromCalling: Int) {
        progressBar.visibility = View.GONE
        Log.d("response", response.toString())
        var shopListResponse = Gson().fromJson<ShopListResponse>(response, ShopListResponse::class.java)
        if (shopListResponse.status == 1 && shopListResponse.data != null && shopListResponse.data.lists != null) {
            Log.d("List", shopListResponse.toString())
            var adapter = ShopListAdapter(shopListResponse.data.lists,this)
//            listcount.text = adapter.itemCount.toString()+" USED CAR IN "

            list.adapter = adapter

            editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun afterTextChanged(editable: Editable) {
                    val text = editTextSearch.getText().toString().toLowerCase(Locale.getDefault())
                    Log.d("text", text)
                    adapter!!.filter.filter(text)

                }
            })

        }
    }

    override fun onError(message: String?, fromCalling: Int) {
        progressBar.visibility = View.GONE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var state = false
        when (item.itemId) {
            R.id.about -> {
                startActivity(Intent(this, AboutUs::class.java))
                state = true
            }

//            R.id.shop -> {
//                startActivity(Intent(this, MyShop::class.java))
////                startActivity(Intent(this, WebViewActivity::class.java)
////                        .putExtra("title", "My Shop")
////                        .putExtra("url", "http://www.sscarbike.com"))
//                state = true
//            }
//            R.id.touch -> {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "sitaram.swami@gmail.com"))
//                intent.putExtra(Intent.EXTRA_SUBJECT, "SS Car App")
//                startActivity(intent);
//                state = true
//            }
//            R.id.facebook -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Facebook")
//                        .putExtra("url", "https://www.facebook.com/SSCarBike"))
//                state = true
//            }
//            R.id.twitter -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Twitter")
//                        .putExtra("url", "https://twitter.com/sscarbike"))
//                state = true
//            }
//            R.id.linkdn -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Linkdin")
//                        .putExtra("url", " https://www.linkedin.com/in/sscarbike/"))
//                state = true
//            }
//            R.id.flickr -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Flickr")
//                        .putExtra("url", "  https://www.flickr.com/photos/sscarbike/"))
//                state = true
//            }
//            R.id.google -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Google")
//                        .putExtra("url", "https://plus.google.com/+SSCarBike"))
//                state = true
//            }
//            R.id.pinterest -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Pinterest")
//                        .putExtra("url", "https://in.pinterest.com/sscarbike"))
//                state = true
//            }
//            R.id.blog -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Blogger")
//                        .putExtra("url", "https://sscarbike.blogspot.in"))
//                state = true
//            }

            R.id.contact -> {
                startActivity(Intent(this, Profile::class.java))
                state = true
            }
            R.id.website -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "WebSite")
                        .putExtra("url", "http://www.sscarbike.com"))
                state = true
            }
            R.id.news -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "News")
                        .putExtra("url", "http://sscarbike.com/news.html"))
                state = true
            }
//            R.id.behanc -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Behance")
//                        .putExtra("url", "https://www.behance.net/jaipurphotoshop"))
//                state = true
//            }

            R.id.rate -> {
                val appPackageName = packageName // getPackageName() from Context or Activity object
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.app.sitaramswami")))
                } catch (anfe: android.content.ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.app.sitaramswami")))
                }
                state = true
            }
            R.id.car_bike -> {
                startActivity(Intent(this, EnquiryForm::class.java))
                state = true
            }
            R.id.help -> {
                startActivity(Intent(this, Feedback::class.java))
                state = true
            }
            R.id.logout -> {
                // mAuth.signOut();
                sessionManager.logout()
                Toast.makeText(this, "You are successfully logout", Toast.LENGTH_SHORT).show()
                state = true
                finish()
            }
            R.id.privacy -> {
                startActivity(Intent(this, PrivacyPolicy::class.java))
                state = true
            }
            R.id.invite -> {
                startActivity(Intent(this, InviteEarn::class.java))
                state = true
            }
            R.id.terms -> {
                startActivity(Intent(this, TermsOfService::class.java))
                state = true
            }
            R.id.career -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Career")
                        .putExtra("url", "http://sscarbike.com/careers.html"))
                state = true
            }
            R.id.advertise -> {
                startActivity(Intent(this, WebViewActivity::class.java)
                        .putExtra("title", "Advertise")
                        .putExtra("url", "http://sscarbike.com/advertise_with_us.html"))
                state = true
            }
        }
        drawer_layout.closeDrawer(Gravity.LEFT)
        return state
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
