package com.app.sitaramswami.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.sitaramswami.R
import kotlinx.android.synthetic.main.activity_image_detail.*


class ImageDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
        var Images  = intent.getStringArrayListExtra("Images")

       slider1.setSliderImages(Images)
    }


}
