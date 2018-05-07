package com.flipboard.myapplication.ui.imagezoom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flipboard.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageZoomActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.product_img_zoom)
    ImageView productImgZoom;
    @BindView(R.id.img_zoom_close)
    ImageButton imgZoomClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String img = getIntent().getExtras().getString("img");

        productImgZoom.setOnTouchListener(new ImageMatrixTouchHandler(productImgZoom.getContext()));

        Glide.with(getApplicationContext())
                .load(img)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .placeholder(R.drawable.placeholder)
                .into(productImgZoom);

    }

    @OnClick(R.id.img_zoom_close)
    public void onClick() {
        onBackPressed();
    }
}
