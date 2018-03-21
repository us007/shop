package com.silverpixelz.myapplication.ui.ProductDetail;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.silverpixelz.myapplication.R;
import com.silverpixelz.myapplication.data.api.apihelper.ApiConfig;
import com.silverpixelz.myapplication.data.api.apihelper.AppConfig;
import com.silverpixelz.myapplication.data.api.model.Product.Product;
import com.silverpixelz.myapplication.data.db.apihelper.RealmHelper;
import com.silverpixelz.myapplication.events.EventNotification;
import com.silverpixelz.myapplication.events.EventProduct;
import com.silverpixelz.myapplication.ui.LocateStore.LocateStoreActivity;
import com.silverpixelz.myapplication.ui.Subscribe.SubscribeActivity;
import com.silverpixelz.myapplication.ui.aboutus.AboutUsActivity;
import com.silverpixelz.myapplication.ui.cart.CartActivity;
import com.silverpixelz.myapplication.ui.contactus.ContactUsActivity;
import com.silverpixelz.myapplication.ui.home.HomeActivity;
import com.silverpixelz.myapplication.ui.imagezoom.ImageZoomActivity;
import com.silverpixelz.myapplication.ui.notification.NotificationActivity;
import com.silverpixelz.myapplication.ui.pdf.PdfActivity;
import com.silverpixelz.myapplication.ui.search.SearchActivity;
import com.silverpixelz.myapplication.utils.BlurNavigationDrawer.BlurDrawerLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.product_no_txt)
    TextView productNoTxt;
    @BindView(R.id.product_name_txt)
    TextView productNameTxt;
    @BindView(R.id.product_img)
    ImageView productImg;
    @BindView(R.id.product_description_txt)
    TextView productDescriptionTxt;
    @BindView(R.id.product_request_quote_txt)
    TextView productRequestQuoteTxt;
    @BindView(R.id.product_back_category_txt)
    TextView productBackCategoryTxt;
    @BindView(R.id.product_share_txt)
    TextView productShareTxt;
    @BindView(R.id.edt_search)
    TextView edtSearch;
    @BindView(R.id.layout_search_main)
    LinearLayout layoutSearchMain;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.navigation_drawer)
    BlurDrawerLayout navigationDrawer;
    @BindView(R.id.txt_lyt)
    RelativeLayout txtLyt;

    private ProductDetailPresenter productDetailPresenter;
    private ProgressDialog progressDialog;
    private RealmHelper realmHelper;
    TextView textCartItemCount;

    int id;
    String name, no, description, image;
    private Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition enter_transition =
                    TransitionInflater.from(this).
                            inflateTransition(R.transition.trns_product_detail);
            getWindow().setAllowEnterTransitionOverlap(true);
            //getWindow().setExitTransition(exit_transition);
            getWindow().setEnterTransition(enter_transition);
        }


        realmHelper = new RealmHelper(this);

        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        InitPresenter();
        onAttach();
        SetHomeButton();
        SetProgress();
        InitNavigationDrawer();

        if (getIntent().getExtras() != null) {
            LoadProductById(getIntent().getExtras().getString("id"));
        }

        layoutSearchMain.setVisibility(View.VISIBLE);
        productImg.setDrawingCacheEnabled(true);

        productImg.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ProductDetailActivity.this,
                        productImg, "product_img");
                Intent i = new Intent(getApplicationContext(), ImageZoomActivity.class);
                i.putExtra("img", image);
                startActivity(i, options.toBundle());
            } else {
                Intent i = new Intent(getApplicationContext(), ImageZoomActivity.class);
                i.putExtra("img", image);
                startActivity(i);
            }
        });

        setNavItemCount(R.id.menu_notification_history, new RealmHelper(getApplicationContext()).GetUnreadNotification().size());

    }

    private void LoadProductById(String idd) {
        ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<Product> call = getResponse.ProductById(idd);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                p = response.body();
                HideProgress();
                if (response.isSuccessful()) {
                    id = p.getId();
                    name = p.getProdname();
                    no = p.getProdNum();
                    description = p.getBriefDesc();
                    image = "http://www.silvergiftz.com/images/product/" + p.getProdImage();

                    productNameTxt.setText(name);
                    productNoTxt.setText(no);
                    productDescriptionTxt.setText(description);
                    Glide.with(getApplicationContext())
                            .load(image)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .dontTransform()
                            .dontAnimate()
                            .fitCenter()
                            .placeholder(R.drawable.placeholder)
                            .into(productImg);
                } else {
                    Toast.makeText(getApplicationContext(), "Try Again!!! Something went wrong!", Toast.LENGTH_LONG).show();
                    // productView.ResponseError("Try Again","Something went wrong!",3);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                if (t instanceof ConnectException) {
                    HideProgress();
                    Toast.makeText(getApplicationContext(), "Internet Error!!! Please, Check your Internet Connection!", Toast.LENGTH_LONG).show();
                } else if (t instanceof SocketTimeoutException) {
                    call.clone().enqueue(this);
                    //productView.ResponseError("Timeout Error", "Please, Try again!",3);
                } else {
                    HideProgress();
                    Toast.makeText(getApplicationContext(), "Internet Error!!! Please, Check your Internet Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Navigation Drawer Method
    public void InitNavigationDrawer() {
        navigationDrawer.setScrimColor(Color.parseColor("#33000000"));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.menu_home:
                        navigationDrawer.closeDrawers();
                        Intent ih = new Intent(getApplicationContext(), HomeActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(ih,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductDetailActivity.this).toBundle());
                        } else {
                            startActivity(ih);
                        }
                        break;
                    case R.id.menu_notification_history:
                        navigationDrawer.closeDrawers();
                        Intent intt = new Intent(getApplicationContext(), NotificationActivity.class);
                        startActivity(intt);
                        break;
                    case R.id.menu_contact_us:
                        navigationDrawer.closeDrawers();
                        Intent i = new Intent(getApplicationContext(), ContactUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(i,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductDetailActivity.this).toBundle());
                        } else {
                            startActivity(i);
                        }
                        break;
                    case R.id.menu_about_us:
                        navigationDrawer.closeDrawers();
                        Intent in = new Intent(getApplicationContext(), AboutUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(in,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductDetailActivity.this).toBundle());
                        } else {
                            startActivity(in);
                        }
                        break;
                    case R.id.menu_product_pdf:
                        navigationDrawer.closeDrawers();
                        Intent it = new Intent(getApplicationContext(), PdfActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(it,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductDetailActivity.this).toBundle());
                        } else {
                            startActivity(it);
                        }
                        break;
                    case R.id.menu_store_location:
                        navigationDrawer.closeDrawers();
                        Intent ls = new Intent(getApplicationContext(), LocateStoreActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(ls,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductDetailActivity.this).toBundle());
                        } else {
                            startActivity(ls);
                        }
                        break;
                    case R.id.menu_subscribe:
                        navigationDrawer.closeDrawers();
                        Intent sub = new Intent(getApplicationContext(), SubscribeActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(sub,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductDetailActivity.this).toBundle());
                        } else {
                            startActivity(sub);
                        }
                        break;
                    case R.id.menu_catalouge:
                        navigationDrawer.closeDrawers();
                        Uri uri2 = Uri.parse("http://silvergiftz.com/ecatalogue/");
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
       /* //  R.drawable.hamburger_button
        badgeToggle = new BadgeDrawerToggle(this,navigationDrawer,toolbar,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        badgeToggle.setDrawerIndicatorEnabled(false);
        //Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getApplicationContext().getTheme());
        badgeToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        //badgeToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        //badgeToggle.setDrawerIndicatorEnabled(false);
        //Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getApplicationContext().getTheme());
        //badgeToggle.setHomeAsUpIndicator(drawable);
        badgeToggle.setBadgeText("1");
        badgeToggle.syncState();*/
    }

    private void InitPresenter() {
        productDetailPresenter = new ProductDetailPresenter();
    }

    private void SetHomeButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void SetProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onAttach() {
        productDetailPresenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        productDetailPresenter.onDetach();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        textCartItemCount.setText(String.valueOf(Math.min(realmHelper.GetProductsCount(), 99)));

        FrameLayout cart = (FrameLayout) actionView.findViewById(R.id.btn_cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), CartActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(i,
                            ActivityOptions.makeSceneTransitionAnimation((Activity) ProductDetailActivity.this).toBundle());
                } else {
                    startActivity(i);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (navigationDrawer.isDrawerOpen(Gravity.START)) {
                    navigationDrawer.closeDrawer(Gravity.START);
                } else {
                    navigationDrawer.openDrawer(Gravity.START);
                }
                break;
            case R.id.menu_cart:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ShowProgress() {
        txtLyt.setVisibility(View.INVISIBLE);
        progressDialog.show();
    }

    @Override
    public void HideProgress() {
        progressDialog.dismiss();
        txtLyt.setVisibility(View.VISIBLE);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnProductEvent(EventProduct response) {
        p = response.getProduct();
        id = p.getId();
        name = p.getProdname();
        no = p.getProdNum();
        description = p.getBriefDesc();
        image = "http://www.silvergiftz.com/images/product/" + p.getProdImage();

        productNameTxt.setText(name);
        productNoTxt.setText(no);
        productDescriptionTxt.setText(description);
        Glide.with(getApplicationContext())
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .dontAnimate()
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(productImg);
    }

    @OnClick({R.id.product_request_quote_txt, R.id.product_back_category_txt, R.id.product_share_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_request_quote_txt:
                Boolean flag = realmHelper.SaveProduct(id, name, no, image, "", "", "");
                if (flag) {
                    invalidateOptionsMenu();
                }
                break;
            case R.id.product_back_category_txt:
                onBackPressed();
                break;
            case R.id.product_share_txt:
                String content = "Product No. : " + p.getProdNum() + "\n" +
                        "Product Name : " + p.getProdname()/* + "\n" +
                        "Product Description : " + p.getBriefDesc()*/;
                Uri bmpUri = Uri.fromFile(getLocalBitmapUri(productImg));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, content);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                break;
        }
    }

    @OnClick(R.id.edt_search)
    public void SearchClick() {
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductDetailActivity.this).toBundle());
        } else {
            startActivity(i);
        }
        //startActivity(new Intent(getApplicationContext(), SearchActivity.class));
    }

    public File getLocalBitmapUri(ImageView imageView) {

        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    private void setNavItemCount(@IdRes int itemId, int count) {
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView();
        if (count > 0) {
            view.setText(String.valueOf(count));
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnProductEvent(EventNotification response) {
        invalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        setNavItemCount(R.id.menu_notification_history, new RealmHelper(getApplicationContext()).GetUnreadNotification().size());
        super.onResume();
    }
}
