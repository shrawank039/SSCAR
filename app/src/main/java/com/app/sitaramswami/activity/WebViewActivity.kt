package com.app.sitaramswami.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import com.app.sitaramswami.R
import com.app.sitaramswami.component.AppWebClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun initialize() {
        var title = intent.extras.getString("title", "")
        var url = intent.extras.getString("url", "")
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initWebSettings()
        web.webViewClient = AppWebClient()
        web.loadUrl(url)
    }



    private fun initWebSettings() {

        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setUseWideViewPort(true);
        web.setFocusableInTouchMode(true);


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
