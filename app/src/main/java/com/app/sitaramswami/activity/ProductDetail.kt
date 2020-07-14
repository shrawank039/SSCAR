package com.app.sitaramswami.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.JsonElement
import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.app.sitaramswami.*

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_product_detail.*

import java.text.SimpleDateFormat

import java.util.*


class ProductDetail : AppCompatActivity(), RetrofitListener {

    lateinit var sessionManager: SessionManager
    lateinit var product_id:String
    lateinit var user_id:String
    override fun onResponse(response: JsonElement?, fromCalling: Int) {
        when(fromCalling)
        {
            1-> {
                progressBar.visibility=View.INVISIBLE
                Log.d("response", response.toString())
                if (response != null) {
                    var json = response.asJsonObject
                    if (json.get("status").asInt > 0) {
                        var Product = json.get("data").toString()
                        var ProfileResponse = Gson().fromJson(Product, ProductListResponse::class.java)
                        Log.d("Product", ProfileResponse.toString())



                        price.text = "\u20B9" + ProfileResponse.price

                        make.text = ProfileResponse.make +" "+ProfileResponse.car_model
                        model.text = ProfileResponse.year + " | " + ProfileResponse.km_drive + " Km | " + ProfileResponse.fuel
                        address.text = ProfileResponse.city
                        date.text = ProfileResponse.updated_on

//                        brand_name.text = ProfileResponse.car_make
                        if(ProfileResponse.car_make!=null)  brand_name.text =  ProfileResponse.car_make else brand_layout.visibility=View.GONE

//                        modal_name.text = ProfileResponse.car_model
                        if(ProfileResponse.car_model!=null)  modal_name.text =  ProfileResponse.car_model else modal_layout.visibility=View.GONE

//                        year_name.text = ProfileResponse.year
                        if(ProfileResponse.year!=null)  year_name.text =  ProfileResponse.year ; year_layout.visibility=View.GONE

//                        fuel_name.text = ProfileResponse.fuel
                        if(ProfileResponse.fuel!=null) ; fuel_name.text =  ProfileResponse.fuel ; fuel_layout.visibility=View.GONE

//                        km_driven_name.text = ProfileResponse.km_drive
                        if(ProfileResponse.km_drive!=null)  km_driven_name.text = ProfileResponse.km_drive else kmdriven_layout.visibility=View.GONE

//                        registration_name.text = ProfileResponse.registration_place
                        if(ProfileResponse.registration_place!=null) registration_name.text =   ProfileResponse.registration_place else registration_layout.visibility=View.GONE

//                        owner_name.text = ProfileResponse.owner
                        if(ProfileResponse.owner!=null)  owner_name.text =  ProfileResponse.owner else owner_layout.visibility=View.GONE

//                        seller_type_name.text = ProfileResponse.type
                        if(ProfileResponse.type!="")  seller_type_name.text =   ProfileResponse.type else seller_layout.visibility=View.GONE

//                        transmission_name.text = ProfileResponse.transmission
                        if(ProfileResponse.transmission!=null)  transmission_name.text =  ProfileResponse.transmission else transmission_layout.visibility=View.GONE

//                        color_name.text = ProfileResponse.color
                        if(ProfileResponse.color!=null) ; color_name.text =  ProfileResponse.color ; color_layout.visibility=View.GONE

//                        insurance.text = ProfileResponse.insurance_type
                        if(ProfileResponse.insurance_type!=null)  insurance.text =  ProfileResponse.insurance_type else insurance_layout.visibility=View.GONE
                        // seating_capacity.text = ProfileResponse.updated_on
                        //gear_box.text=ProfileResponse.gear
                        type_car.text = ProfileResponse.type_of_car
                        if(ProfileResponse.type_of_car!=null)  type_car.text = ProfileResponse.type_of_car else type_car_layout.visibility=View.GONE

                        power_steering.text = ProfileResponse.power_steering
                        if(ProfileResponse.power_steering!=null)   power_steering.text = ProfileResponse.power_steering else powersteering_layout.visibility=View.GONE

                        power_windows.text = ProfileResponse.power_windows
                        if(ProfileResponse.power_windows!=null)  power_windows.text = ProfileResponse.power_windows else powerwindows_layout.visibility=View.GONE

                        cruise_control.text = ProfileResponse.cruise_control
                        if( ProfileResponse.cruise_control!=null)  cruise_control.text = ProfileResponse.cruise_control else cruisecontrol_layout.visibility=View.GONE

                        navigation_system.text = ProfileResponse.navigation_system
                        if(ProfileResponse.navigation_system!=null) navigation_system.text = ProfileResponse.navigation_system else navigationsystem_layout.visibility=View.GONE

                        back_camera.text = ProfileResponse.back_camera
                        if(ProfileResponse.back_camera!=null)  brand_name.text =  ProfileResponse.back_camera ; backcamera_layout.visibility=View.GONE

                        adjustable_steering.text = ProfileResponse.adjustable_steering
                        if(ProfileResponse.adjustable_steering!=null)  adjustable_steering.text =  ProfileResponse.adjustable_steering else adjustablesteering_layout.visibility=View.GONE

                        automatic_boot.text = ProfileResponse.automatic_boot
                        if(ProfileResponse.automatic_boot!=null)  automatic_boot.text =  ProfileResponse.automatic_boot else automaticboot_layout.visibility=View.GONE

                        air_conditioning.text = ProfileResponse.air_conditioning
                        if(ProfileResponse.air_conditioning!=null)  air_conditioning.text = ProfileResponse.air_conditioning else airconditioning_layout.visibility=View.GONE


                        description.text = ProfileResponse.discription
                        if(ProfileResponse.discription!="") {
                            description.text = ProfileResponse.discription
                        } else {
                            description.visibility = View.GONE
                            desc.visibility = View.GONE
                            desc_view.visibility = View.GONE

                        }

                        sunroof.text = ProfileResponse.sunroof
                        if(ProfileResponse.sunroof!=null)  sunroof.text = ProfileResponse.sunroof else sunroof_layout.visibility=View.GONE

                        fog_lamps.text = ProfileResponse.fog_lamps
                        if(ProfileResponse.fog_lamps!=null)  fog_lamps.text = ProfileResponse.fog_lamps else foglamps_layout.visibility=View.GONE

                        alloy_wheels.text = ProfileResponse.alloy_wheels
                        if(ProfileResponse.alloy_wheels!=null)  alloy_wheels.text = ProfileResponse.alloy_wheels else alloywheels_layout.visibility=View.GONE

                        rare_window_wiper.text = ProfileResponse.rear_window_wiper
                        if(ProfileResponse.rear_window_wiper!=null)  rare_window_wiper.text = ProfileResponse.rear_window_wiper else rearwindow_layout.visibility=View.GONE

                        adjustable_external.text = ProfileResponse.adjustable_external_mirror
                        if(ProfileResponse.adjustable_external_mirror!=null)  adjustable_external.text = ProfileResponse.adjustable_external_mirror else adjustableexternal_layout.visibility=View.GONE

                        seller_name.text = ProfileResponse.name

                        Log.d("favfirst",ProfileResponse.fav)
                        if (ProfileResponse.fav == "0") {
                            Log.d("fav",ProfileResponse.fav)
                            fav.visibility=View.VISIBLE
                            fav_fill.visibility=View.GONE

                        } else {
                            Log.d("fav1",ProfileResponse.fav)
                            fav.visibility=View.GONE
                            fav_fill.visibility=View.VISIBLE
                        }
                        if (ProfileResponse.images.size != 0) {
                            slider.setSliderImages(ProfileResponse.images)
                        }

                        fav_fill.setOnClickListener(View.OnClickListener {

                            if (sessionManager.getUser() != null) {
                                fav.visibility=ImageView.VISIBLE
                                fav_fill.visibility=ImageView.GONE
                                var user_id = SessionManager(this).getUser()!!.id.toString()
                                Log.d("UserID",user_id.toString())
                                var data = FavCar(user_id,product_id)
                                var FavId = Gson().toJsonTree(data)

                                RequestCall().post(Constants.FavProduct, FavId, this, 2)

                            }
                            else {
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish()
                            }

                        })
                        fav.setOnClickListener(View.OnClickListener {

                            if (sessionManager.getUser() != null) {
                                fav.visibility=ImageView.GONE
                                fav_fill.visibility=ImageView.VISIBLE
                                var user_id = SessionManager(this).getUser()!!.id.toString()
                                Log.d("UserID",user_id.toString())
                                var data = FavCar(user_id,product_id)
                                var FavId = Gson().toJsonTree(data)

                                RequestCall().post(Constants.FavProduct, FavId, this, 2)

                            }
                            else {
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish()
                            }

                        })

                        viewVideo.setOnClickListener(View.OnClickListener {
                            viewVideo.context.startActivity(Intent(viewVideo.context, WebViewActivity::class.java)
                                    .putExtra("title", "Youtube")
                                    .putExtra("url", ProfileResponse.video_url))
                        })

                        if(ProfileResponse.phone=="" || ProfileResponse.phone==null ){
                            call.visibility=View.GONE
                        }
                        else {
                            call.setOnClickListener(View.OnClickListener {
                                if (ProfileResponse.phone != null) {
                                    val i = Intent(ACTION_DIAL)
                                    val p = "tel:" + ProfileResponse.phone
                                    i.data = Uri.parse(p)
                                    call.context.startActivity(i)
                                } else {
                                    Toast.makeText(call.context, "Phone number not available", Toast.LENGTH_SHORT).show()
                                }

                            })
                        }

                        share.setOnClickListener(View.OnClickListener {

                            val shareIntent = Intent()
                            shareIntent.action = Intent.ACTION_SEND
                            shareIntent.putExtra(Intent.EXTRA_TEXT, ProfileResponse.name + "\n" + ProfileResponse.full_title
                                    + " " + ProfileResponse.make + "\n" + ProfileResponse.price + "\n" + ProfileResponse.whatsapp_no
                                    + "\n" + ProfileResponse.address_first + "\n" + ProfileResponse.images[0])

//                shareIntent.putExtra(EXTRA_STREAM, uri)
                            shareIntent.type = "text/html"
                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            share.context.startActivity(Intent.createChooser(shareIntent, "Share detail..."))

                        })
                        if(ProfileResponse.whatsapp_no=="" || ProfileResponse.whatsapp_no==null ){
                            whatsapp.visibility=View.GONE
                        }
                        else {
                            whatsapp.setOnClickListener(View.OnClickListener {
                                if (ProfileResponse.whatsapp_no != null) {

                                    val uri = Uri.parse("https://api.whatsapp.com/send?phone=" + ProfileResponse.whatsapp_no + "&text=Hello,%20from%20my%20Apps")
                                    val intent = Intent(ACTION_VIEW, uri)
                                    whatsapp.context.startActivity(intent)
                                } else {
                                    Toast.makeText(call.context, "Phone number not available", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }

                    } else {
                        CommonUtil.showDialog(this, json.get("message").asString, false)
                    }


                }
            }
            2->{
                progressBar.visibility=View.INVISIBLE
                if (response != null) {
                    Log.d("FavResponse",response.toString())
                    var json = response.asJsonObject
                    if (json.get("status").asInt > 0) {
                        Toast.makeText(this, json.get("data").asString, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, json.get("data").asString, Toast.LENGTH_SHORT).show()
                    }
                }

            }}

    }

    override fun onError(message: String?, fromCalling: Int) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_detail)
        progressBar.visibility=View.VISIBLE
        sessionManager = SessionManager(this)
        if (sessionManager.getUser() != null) {
            user_id = SessionManager(this).getUser()!!.id.toString()
            Log.d("Id",user_id)
            product_id = intent.getStringExtra("Id")
            var data = ProductId(product_id, user_id)
            var productId = Gson().toJsonTree(data)

            RequestCall().post(Constants.ProductDatails, productId, this, 1)
        }
        else{

            product_id = intent.getStringExtra("Id")
            var data = Product(product_id)
            var productId = Gson().toJsonTree(data)

            RequestCall().post(Constants.ProductDatails, productId, this, 1)
        }

        if (sessionManager.getIds() != null) {
            var mobileAdId = sessionManager.getIds()!!.mobile_add_id
            //  var mobileAdId = "ca-app-pub-6981915153700667/2441931636"
            Log.d("mobileId", mobileAdId)
            MobileAds.initialize(this, mobileAdId)
            val adView = AdView(this)
            adView.adSize = AdSize.BANNER
            adView.adUnitId = mobileAdId
            productadView.addView(adView);
            if (adView != null) {
                val adRequest = AdRequest.Builder().build()
                adView.loadAd(adRequest)

            }
        }

    }
}
