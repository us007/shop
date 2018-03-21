package com.silverpixelz.myapplication.utils.BlurNavigationDrawer;

/**
 * Created by Dharmik Patel on 28-Jul-17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

import com.silverpixelz.myapplication.R;


public class BlurDrawerLayout extends DrawerLayout {

    private BlurActionBarDrawerToggle blurActionBarDrawerToggle;

    public BlurDrawerLayout(Context context) {
        super(context);
    }

    public BlurDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BlurDrawerLayout, 0, 0);
        try {

            int drawerUpImage = ta.getResourceId(R.styleable.BlurDrawerLayout_drawerUpImageId, 0);
            int openDescription = ta.getResourceId(R.styleable.BlurDrawerLayout_openDescription, 0);
            int closeDescription = ta.getResourceId(R.styleable.BlurDrawerLayout_closeDescription, 0);

            int blurRadius = ta.getInteger(R.styleable.BlurDrawerLayout_blurRadius,
                    BlurActionBarDrawerToggle.DEFAULT_BLUR_RADIUS);

            float downScaleFactor = ta.getFloat(R.styleable.BlurDrawerLayout_downScaleFactor,
                    BlurActionBarDrawerToggle.DEFAULT_DOWNSCALEFACTOR);

            blurActionBarDrawerToggle = new BlurActionBarDrawerToggle(
                    (Activity) context,
                    this,
                    drawerUpImage,//R.drawable.ic_menu
                    openDescription,
                    closeDescription);

            blurActionBarDrawerToggle.setRadius(blurRadius);
            blurActionBarDrawerToggle.setDownScaleFactor(downScaleFactor);

            setDrawerListener(blurActionBarDrawerToggle);

            post(new Runnable() {
                @Override
                public void run() {
                    blurActionBarDrawerToggle.syncState();
                }
            });

        } finally {
            ta.recycle();
        }


    }

    public BlurDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BlurActionBarDrawerToggle getBlurActionBarDrawerToggle() {
        return blurActionBarDrawerToggle;
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }*/
}

