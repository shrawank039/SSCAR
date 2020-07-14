package com.app.sitaramswami.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sitaramswami.R;


/**
 * Created by kartik on 28-Apr-18.
 */

public class PlateWidget extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    private CardView layout;
    private float drawable_height;
    private float drawable_width;
    private String text = "Text";
    private int colorText, background;
    private float textSize;
    private int drawable;

    public PlateWidget(Context context) {
        super(context);
        init(null);
    }

    public PlateWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);

    }


    public PlateWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PlateWidget);
            final int N = a.getIndexCount();
            for (int i = 0; i < N; ++i) {
                int attr = a.getIndex(i);
                switch (attr) {

                    case R.styleable.PlateWidget_plate_background:
                        background = a.getColor(attr, Color.GRAY);
                        break;
                    case R.styleable.PlateWidget_text:
                        text = a.getString(attr);
                        break;
                    case R.styleable.PlateWidget_text_size:
                        textSize = a.getDimension(attr, 5);
                        break;
                    case R.styleable.PlateWidget_text_color:
                        colorText = a.getColor(attr, Color.BLACK);
                        break;
                    case R.styleable.PlateWidget_drawable:
                        drawable = a.getResourceId(attr, 0);
                        break;
                }
            }
            a.recycle();
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.plate_layout, this, false);
        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.text);
        layout = (CardView) view.findViewById(R.id.card_view);
        layout.setCardBackgroundColor(background);
        imageView.setImageResource(drawable);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(colorText);
        addView(view);
    }

    public void setTextValue(String text) {
        textView.setText(text);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setIcon(int icon) {
        imageView.setImageResource(icon);
    }

    public void setBackground(int background) {
        layout.setCardBackgroundColor(ContextCompat.getColor(getContext(), background));

    }
}
