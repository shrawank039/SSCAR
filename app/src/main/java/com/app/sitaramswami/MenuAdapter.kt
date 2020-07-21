package com.app.sitaramswami

import android.content.Intent
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.sitaramswami.activity.*
import com.app.sitaramswami.activity.AboutUs
import com.app.sitaramswami.component.PlateWidget

/**
 * Created by kartik on 06-May-18.
 */
class MenuAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    var size = 4
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MenuAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }


    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var names = arrayOf("About", "My Shop", "Need Car & Bike", "Profile & Contact")
                var icons = intArrayOf(R.drawable.account,
                R.drawable.store,
                R.drawable.comment_processing_outline, R.drawable.phone_log);
        var colors = intArrayOf(R.color.colorPrimary,  R.color.green,
                android.R.color.holo_purple, R.color.blue_600)
//        var names = arrayOf("About", "Facebook", "Video", "My Shop", "Twitter", "Google", "Website", "My Photo", "Need Car & Bike", "Profile & Contact")
//        var icons = intArrayOf(R.drawable.account, R.drawable.facebook, R.drawable.youtube,
//                R.drawable.store, R.drawable.twitter, R.drawable.google_plus, R.drawable.web,
//                R.drawable.image , R.drawable.comment_processing_outline, R.drawable.phone_log);
//        var colors = intArrayOf(R.color.colorPrimary, R.color.fb_color, R.color.yutb_color, R.color.green, R.color.tw_color,
//                R.color.google, R.color.dark_green, R.color.margenta, android.R.color.holo_purple, R.color.blue_600)
        var item = view.findViewById<PlateWidget>(R.id.item)

        fun bind(pos: Int) {
            item.setTextValue(names[pos])
            item.setIcon(icons[pos])
            item.setBackground(colors[pos])
            item.setOnClickListener(View.OnClickListener {

                when (pos) {
                    0 -> {
                        item.context.startActivity(Intent(item.context, AboutUs::class.java))
                    }
                    1 -> {
//                        item.context.startActivity(Intent(item.context, WebViewActivity::class.java)
//                                .putExtra("title", "My Shop")
//                                .putExtra("url", "http://www.sscarbike.com"))
                        item.context.startActivity(Intent(item.context, MyShop::class.java))

                    }
                    2 -> {
                        item.context.startActivity(Intent(item.context, EnquiryForm::class.java))
                    }
                    3 -> {
                        item.context.startActivity(Intent(item.context, Profile::class.java))
                    }


                }
            })
        }
    }
}