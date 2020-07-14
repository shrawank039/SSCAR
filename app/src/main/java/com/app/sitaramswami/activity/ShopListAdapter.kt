package com.app.sitaramswami.activity

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.shop_detail_view.view.*


import android.content.Intent
import android.content.Intent.*
import android.util.Log

import com.squareup.picasso.Picasso
import android.icu.util.ULocale.getCountry
import android.text.method.TextKeyListener.clear
import java.util.*
import android.icu.util.ULocale.getCountry
import android.support.v4.content.ContextCompat.startActivity
import android.text.method.TextKeyListener.clear
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.Toast
import com.app.sitaramswami.*
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement


/**
 * Created by kartik on 21-May-18.
 */
class ShopListAdapter(lists: ArrayList<ShopListResponse>,var context: Context) : RecyclerView.Adapter<ShopListAdapter.ViewHolder>() , Filterable{
//private var contex=context
    lateinit var contex: Context
//    override fun onResponse(response: JsonElement?, fromCalling: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        if (response != null) {
//            Log.d("FavResponse",response.toString())
//            var json = response.asJsonObject
//            if (json.get("status").asInt > 0) {
//                Toast.makeText(contex, json.get("data").asString, Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(contex, json.get("data").asString, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onError(message: String?, fromCalling: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


    var SearchList=lists
    var shopList = lists


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                Log.d("list",charString)
                if (charString.isEmpty()) {
                    shopList = SearchList

                } else {
                    val filteredList = ArrayList<ShopListResponse>()
                    for (row in SearchList) {
                        if (row.make.toLowerCase()!!.contains(charString.toLowerCase()) ||row.city.toLowerCase()!!.contains(charString.toLowerCase()) ||row.car_model.toLowerCase()!!.contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    shopList = filteredList

                }
                val filterResults = Filter.FilterResults()
                filterResults.values = shopList
                Log.d("listresult",shopList.toString())

                return filterResults
            }
            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                shopList = filterResults.values as ArrayList<ShopListResponse>
                notifyDataSetChanged()
            }
        }
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.shop_detail_view, parent, false)

        return ShopListAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, shopList[position])

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){


        var make = view.make
        var address = view.address
        var card=view.card_view
        var fav=view.fav
        var fav_fill=view.fav_fill
       var date = view.date
//        var call = view.call
        var model = view.model
      //  var whatsapp = view.whatsapp
        var slider = view.slider_car
        var price = view.price
        var share = view.share
        var video = view.viewVideo
        var layout=view.layout_Click





        fun bind(pos: Int, shopListResponse: ShopListResponse) {
            make.text = shopListResponse.make +" "+shopListResponse.car_model
            model.text = shopListResponse.year +" | "+shopListResponse.km_drive +" Km | "+shopListResponse.fuel
            address.text = shopListResponse.city
           date.text = shopListResponse.updated_on

            price.text = "\u20B9 " + shopListResponse.price
            Log.d("fav",shopListResponse.fav)
            if(shopListResponse.fav=="0"){
                fav.visibility=View.VISIBLE
                fav_fill.visibility=View.GONE
            }
            else{
                fav.visibility=View.GONE
                fav_fill.visibility=View.VISIBLE

            }
            Log.d("Size",shopListResponse.images.size.toString())
            if(shopListResponse.images.size!=0) {


                Picasso.with(slider.context).load(shopListResponse.images[0]).fit()
                        .placeholder(R.drawable.car)
                        .error(R.drawable.car)
                        .into(slider);
            }



            var sessionManager: SessionManager
//            Log.d("Image", shopListResponse.images[0])
            sessionManager = SessionManager(fav.context)
//            if (sessionManager.getIds() != null) {
//                var mobileAdId = sessionManager.getIds()!!.mobile_add_id
//
//                Log.d("mobileId", mobileAdId)
//            MobileAds.initialize(adviewShop.context, mobileAdId)
//                val adView = AdView(adviewShop.context)
//                adView.adSize = AdSize.BANNER
//                adView.adUnitId = mobileAdId
//                adviewShop.addView(adView);
//                if (adView != null) {
//                    val adRequest = AdRequest.Builder().build()
//                    adView.loadAd(adRequest)
//
//                }
//        }
        card.setOnClickListener(View.OnClickListener {
            val intent = Intent(card.getContext(), ProductDetail::class.java)
            intent.putExtra("Id",shopListResponse.id.toString())
            card.getContext().startActivity(intent);

        })
//            fav_fill.setOnClickListener(View.OnClickListener {
//
//                if (sessionManager.getUser() != null) {
//                    fav.visibility=ImageView.VISIBLE
//                    fav_fill.visibility=ImageView.GONE
//                    var user_id = SessionManager(fav_fill.context).getUser()!!.id.toString()
//                    Log.d("UserID",user_id.toString())
//                    var data = FavCar(user_id,shopListResponse.id.toString())
//                    var FavId = Gson().toJsonTree(data)
//
//                    RequestCall().post(Constants.FavProduct, FavId, ,2)
//
//                }
//                else {
//                    val intent = Intent(fav_fill.context, Login::class.java)
//                    fav_fill.context.startActivity(intent)
//
//                }
//
//            })
//            fav.setOnClickListener(View.OnClickListener {
//
//                if (sessionManager.getUser() != null) {
//                    fav.visibility=ImageView.GONE
//                    fav_fill.visibility=ImageView.VISIBLE
//                    var user_id = SessionManager(fav.context).getUser()!!.id.toString()
//                    Log.d("UserID",user_id.toString())
//                    var data = FavCar(user_id,shopListResponse.id.toString())
//                    var FavId = Gson().toJsonTree(data)
//
//                    RequestCall().post(Constants.FavProduct, FavId,this, 2)
//
//                }
//                else {
//                    val intent = Intent(fav_fill.context, Login::class.java)
//                    fav_fill.context.startActivity(intent)
//                }
//
//            })


            share.setOnClickListener(View.OnClickListener {

                val shareIntent = Intent()
                shareIntent.action = ACTION_SEND
                shareIntent.putExtra(EXTRA_TEXT, shopListResponse.name + "\n" + shopListResponse.full_title
                        + " " + shopListResponse.make + "\n" + shopListResponse.price + "\n" + shopListResponse.whatsapp_no
                        + "\n" + shopListResponse.address_first + "\n" + shopListResponse.images[0])

//                shareIntent.putExtra(EXTRA_STREAM, uri)
                shareIntent.type = "text/html"
                shareIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
                share.context.startActivity(createChooser(shareIntent, "Share detail..."))

            })
            video.setOnClickListener(View.OnClickListener {
                video.context.startActivity(Intent(video.context, WebViewActivity::class.java)
                        .putExtra("title", "Youtube")
                        .putExtra("url", shopListResponse.video_url))
            })


//            call.setOnClickListener(View.OnClickListener {
//                if (shopListResponse.phone != null) {
//                    val i = Intent(ACTION_DIAL)
//                    val p = "tel:" + shopListResponse.phone
//                    i.data = Uri.parse(p)
//                    call.context.startActivity(i)
//                } else {
//                    Toast.makeText(call.context, "Phone number not available", Toast.LENGTH_SHORT).show()
//                }
//
//            })

//            whatsapp.setOnClickListener(View.OnClickListener {
//                if (shopListResponse.whatsapp_no != null) {
//
//                    val uri = Uri.parse("https://api.whatsapp.com/send?phone=" + shopListResponse.whatsapp_no + "&text=Hello,%20from%20my%20Apps")
//                    val intent = Intent(ACTION_VIEW, uri)
//                    whatsapp.context.startActivity(intent)
//                } else {
//                    Toast.makeText(call.context, "Phone number not available", Toast.LENGTH_SHORT).show()
//                }
//            })


        }
    }



}