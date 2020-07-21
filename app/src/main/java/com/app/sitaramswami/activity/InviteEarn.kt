package com.app.sitaramswami.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.app.sitaramswami.R
import kotlinx.android.synthetic.main.activity_invite_earn.*


class InviteEarn : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_invite_earn
    }

    override fun initialize() {
        toolbar.title = "Invite & Discount"

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        share()

        appurl.setOnClickListener(View.OnClickListener {
          share()
        })
    }



    fun share(){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "https://play.google.com/store/apps/details?id=com.app.sitaramswami"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)

        startActivity(Intent.createChooser(sharingIntent, "invite friends using"))
//        val intent = intent
//        if (intent != null) {
//            val action = intent.action
//            val type = intent.type
//
//            if (Intent.ACTION_SEND == action && type != null) {
//                if ("text/plain" == type) {
//                    handleReceivedText(intent) // Handle text being sent
//                }
//                else{
//                    Toast.makeText(this, "Sending Failed! Please try again ", Toast.LENGTH_LONG).show()
//                    Log.d("Invite", "Sending Failed! Please try again")
//                }
//            }
//        }
//        else{
//            Toast.makeText(this, "Sending Failed! Please try again ", Toast.LENGTH_LONG).show()
//            Log.d("Invite", "Sending Failed! Please try again")
//        }
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
