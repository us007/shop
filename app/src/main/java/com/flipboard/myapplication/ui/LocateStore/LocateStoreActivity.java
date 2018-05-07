package com.flipboard.myapplication.ui.LocateStore;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.flipboard.myapplication.R;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.events.EventNotification;
import com.flipboard.myapplication.ui.Subscribe.SubscribeActivity;
import com.flipboard.myapplication.ui.aboutus.AboutUsActivity;
import com.flipboard.myapplication.ui.cart.CartActivity;
import com.flipboard.myapplication.ui.contactus.ContactUsActivity;
import com.flipboard.myapplication.ui.home.HomeActivity;
import com.flipboard.myapplication.ui.notification.NotificationActivity;
import com.flipboard.myapplication.ui.pdf.PdfActivity;
import com.flipboard.myapplication.ui.search.SearchActivity;
import com.flipboard.myapplication.utils.BlurNavigationDrawer.BlurDrawerLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocateStoreActivity extends AppCompatActivity {

    @BindView(R.id.edt_search)
    TextView edtSearch;
    @BindView(R.id.layout_search_main)
    LinearLayout layoutSearchMain;

    SupportMapFragment map1, map2;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.navigation_drawer)
    BlurDrawerLayout navigationDrawer;
    @BindView(R.id.txt_locate_store_1)
    TextView txtLocateStore1;
    @BindView(R.id.txt_locate_store_2)
    TextView txtLocateStore2;
    private RealmHelper realmHelper;
    TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition enter_transition =
                    TransitionInflater.from(this).
                            inflateTransition(R.transition.trns_store);
            getWindow().setAllowEnterTransitionOverlap(true);
            //getWindow().setExitTransition(exit_transition);
            getWindow().setEnterTransition(enter_transition);
        }

        setContentView(R.layout.activity_locate_store);
        ButterKnife.bind(this);

        realmHelper = new RealmHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        InitNavigationDrawer();

        map1 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        map2 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);

        layoutSearchMain.setVisibility(View.VISIBLE);

        setNavItemCount(R.id.menu_notification_history,new RealmHelper(getApplicationContext()).GetUnreadNotification().size());

        SetMap1();
        SetMap2();
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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) LocateStoreActivity.this).toBundle());
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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) LocateStoreActivity.this).toBundle());
                        } else {
                            startActivity(i);
                        }
                        break;
                    case R.id.menu_about_us:
                        navigationDrawer.closeDrawers();
                        Intent in = new Intent(getApplicationContext(), AboutUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(in,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) LocateStoreActivity.this).toBundle());
                        } else {
                            startActivity(in);
                        }
                        break;
                    case R.id.menu_product_pdf:
                        navigationDrawer.closeDrawers();
                        Intent it = new Intent(getApplicationContext(), PdfActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(it,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) LocateStoreActivity.this).toBundle());
                        } else {
                            startActivity(it);
                        }
                        break;
                    case R.id.menu_store_location:
                        navigationDrawer.closeDrawers();
                        break;
                    case R.id.menu_subscribe:
                        navigationDrawer.closeDrawers();
                        Intent sub = new Intent(getApplicationContext(), SubscribeActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(sub,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) LocateStoreActivity.this).toBundle());
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

    private void SetMap1() {
        map1.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng sydney = new LatLng(25.2611314, 55.2867322);
                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("SilverPixelz Advertising")
                );
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
            }
        });
    }

    private void SetMap2() {
        map2.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng sydney = new LatLng(25.2718489, 55.2999383);
                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("Index Hotel")
                );
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
            }
        });
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
                            ActivityOptions.makeSceneTransitionAnimation((Activity) LocateStoreActivity.this).toBundle());
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
                Intent i = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.edt_search)
    public void SearchClick() {
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) LocateStoreActivity.this).toBundle());
        } else {
            startActivity(i);
        }
        //startActivity(new Intent(getApplicationContext(), SearchActivity.class));
    }

    @OnClick({R.id.txt_locate_store_1, R.id.txt_locate_store_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_locate_store_1:
                Uri uri = Uri.parse("https://goo.gl/maps/919JTfrA78w");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.txt_locate_store_2:
                Uri uri2 = Uri.parse("https://goo.gl/maps/YHkUUfXcC5H2");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                break;

        }
    }

    private void setNavItemCount(@IdRes int itemId, int count) {
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView();
        if (count>0){
            view.setText(String.valueOf(count));
        }else {
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
        setNavItemCount(R.id.menu_notification_history,new RealmHelper(getApplicationContext()).GetUnreadNotification().size());
        super.onResume();
    }
}
