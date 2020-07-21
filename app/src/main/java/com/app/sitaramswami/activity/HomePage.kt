package com.app.sitaramswami.activity

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.app.sitaramswami.*


import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.add_layout.*
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth



class HomePage : HomeBaseActivity(), NavigationView.OnNavigationItemSelectedListener,  RetrofitListener {


    lateinit var mAuth: FirebaseAuth;
    override fun getLayoutId(): Int {
        return R.layout.activity_home_page
    }

    override fun initialize() {

        sessionManager = SessionManager(this)

//        if (sessionManager.getUser() == null) {
//            startActivity(CommonUtil.createIntent(this, Login::class.java, true))
//            finish()
//        }
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
        var drawertoggel = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.openDrawer, R.string.closeDrawer)

        drawertoggel.syncState()
        list1.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        list1.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
         progressBar.visibility=View.VISIBLE
       // RequestCall().get(Constants.SHOP_LIST, this, 1)

//        RequestCall().get(Constants.HOME_IMAGES,  this, 1)

        /* slider.setSliderImages(sliderImages)*/
        navigation.setNavigationItemSelectedListener(this)
        navigation.setItemIconTintList(null);
//        shopicon = (MenuItemCompat.getActionView(navigation.getMenu().findItem(R.id.shop)) as TextView)
//        shopicon.setGravity(Gravity.CENTER_HORIZONTAL);
//        shopicon.setTypeface(null, Typeface.BOLD);
//        shopicon.setTextColor(getResources().getColor(R.color.white));
//        shopicon.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        shopicon.height=30
//        shopicon.text="New"


        list1.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        list1.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        mAuth = FirebaseAuth.getInstance();
        //  adapter = MenuAdapter()
        //list.adapter = adapter
        //online_click.setOnClickListener(this)
        // more.setOnClickListener(this)
    }

    // lateinit var adapter: MenuAdapter
    //  lateinit var sessionManager: SessionManager
//    override fun onClick(p0: View?) {
//        when (p0?.id) {
////            R.id.more -> {
////                adapter.size = 10
////                adapter.notifyDataSetChanged()
////                more.visibility = View.GONE
////            }
//            R.id.online_click -> {
//                startActivity(Intent(this, MyShop::class.java))
//            }
//        }
//    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var state = false
        when (item.itemId) {
            R.id.about -> {
                startActivity(Intent(this, AboutUs::class.java))
                state = true
            }
//            R.id.shop -> {
//              startActivity(Intent(this, MyShop::class.java))
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
//            R.id.instagrm -> {
//                startActivity(Intent(this, WebViewActivity::class.java)
//                        .putExtra("title", "Instagram")
//                        .putExtra("url", "https://www.instagram.com/sscarbike/"))
//                state = true
//            }
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
        }
        drawer_layout.closeDrawer(Gravity.LEFT)
        return state
    }

    override fun onError(message: String, fromCalling: Int) {
        Log.d("response", message)
    }

    override fun onResponse(response: JsonElement, fromCalling: Int) {
//        var bannerListResponse = Gson().fromJson<BannerListResponse>(response, BannerListResponse::class.java)
//        if (bannerListResponse.status==1) {
//          //  slider.setSliderImages(bannerListResponse.data.images)
//        }
//    }
        var shopListResponse = Gson().fromJson<ShopListResponse>(response, ShopListResponse::class.java)
        if (shopListResponse.status == 1 && shopListResponse.data != null && shopListResponse.data.lists != null) {
            var adapter = ShopListAdapter(shopListResponse.data.lists,this)
//            listcount.text = adapter.itemCount.toString()+" USED CAR IN "
            list1.adapter = adapter
        }
    }
}
