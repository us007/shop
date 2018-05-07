package com.flipboard.myapplication.ui.notification;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flipboard.myapplication.R;
import com.flipboard.myapplication.adapter.NotificationAdapter;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.events.EventNotification;
import com.flipboard.myapplication.ui.LocateStore.LocateStoreActivity;
import com.flipboard.myapplication.ui.Subscribe.SubscribeActivity;
import com.flipboard.myapplication.ui.aboutus.AboutUsActivity;
import com.flipboard.myapplication.ui.contactus.ContactUsActivity;
import com.flipboard.myapplication.ui.home.HomeActivity;
import com.flipboard.myapplication.ui.pdf.PdfActivity;
import com.flipboard.myapplication.utils.BlurNavigationDrawer.BlurDrawerLayout;
import com.flipboard.myapplication.utils.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_notification)
    RecyclerView recyclerViewNotification;
    @BindView(R.id.txt_notification_no_data)
    TextView txtNotificationNoData;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.navigation_drawer)
    BlurDrawerLayout navigationDrawer;

    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        recyclerViewNotification.setHasFixedSize(true);
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewNotification.addItemDecoration(new DividerItemDecoration(getApplicationContext()));

        notificationAdapter = new NotificationAdapter(new RealmHelper(this).GetAllNotification(), this);
        recyclerViewNotification.setAdapter(notificationAdapter);

        if (new RealmHelper(this).GetAllNotification().size()>0){
            recyclerViewNotification.setVisibility(View.VISIBLE);
            txtNotificationNoData.setVisibility(View.INVISIBLE);
        }else {
            recyclerViewNotification.setVisibility(View.GONE);
            txtNotificationNoData.setVisibility(View.VISIBLE);
        }

        InitNavigationDrawer();

        setNavItemCount(R.id.menu_notification_history,new RealmHelper(getApplicationContext()).GetUnreadNotification().size());

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnProductEvent(EventNotification response) {
        notificationAdapter = new NotificationAdapter(new RealmHelper(this).GetAllNotification(), this);
        recyclerViewNotification.setAdapter(notificationAdapter);
        if (new RealmHelper(this).GetAllNotification().size()>0){
            recyclerViewNotification.setVisibility(View.VISIBLE);
            txtNotificationNoData.setVisibility(View.INVISIBLE);
        }else {
            recyclerViewNotification.setVisibility(View.GONE);
            txtNotificationNoData.setVisibility(View.VISIBLE);
        }
        invalidateOptionsMenu();
        //setNavItemCount(R.id.menu_notification_history,new RealmHelper(getApplicationContext()).GetUnreadNotification().size());
    }

    //Navigation Drawer Method
    public void InitNavigationDrawer() {
        //  mDrawerToggle = ((BlurDrawerLayout) navigationDrawer).getBlurActionBarDrawerToggle();
        navigationDrawer.setScrimColor(Color.parseColor("#33000000"));
        navigationDrawer.setDrawerShadow(null, GravityCompat.START);
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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) NotificationActivity.this).toBundle());
                        } else {
                            startActivity(ih);
                        }
                        break;
                    case R.id.menu_notification_history:
                        navigationDrawer.closeDrawers();
                        break;
                    case R.id.menu_contact_us:
                        navigationDrawer.closeDrawers();
                        Intent i = new Intent(getApplicationContext(), ContactUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(i,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) NotificationActivity.this).toBundle());
                        } else {
                            startActivity(i);
                        }
                        break;
                    case R.id.menu_about_us:
                        navigationDrawer.closeDrawers();
                        Intent in = new Intent(getApplicationContext(), AboutUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(in,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) NotificationActivity.this).toBundle());
                        } else {
                            startActivity(in);
                        }
                        break;
                    case R.id.menu_product_pdf:
                        navigationDrawer.closeDrawers();
                        Intent it = new Intent(getApplicationContext(), PdfActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(it,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) NotificationActivity.this).toBundle());
                        } else {
                            startActivity(it);
                        }
                        break;
                    case R.id.menu_store_location:
                        navigationDrawer.closeDrawers();
                        Intent ls = new Intent(getApplicationContext(), LocateStoreActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(ls,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) NotificationActivity.this).toBundle());
                        } else {
                            startActivity(ls);
                        }
                        break;
                    case R.id.menu_subscribe:
                        navigationDrawer.closeDrawers();
                        Intent sub = new Intent(getApplicationContext(), SubscribeActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(sub,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) NotificationActivity.this).toBundle());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);

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
                /*if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }*/
                break;
            case R.id.menu_notification_clear_all:
                new RealmHelper(getApplicationContext()).DeleteAllNotification();
                notificationAdapter = new NotificationAdapter(new RealmHelper(this).GetAllNotification(), this);
                recyclerViewNotification.setAdapter(notificationAdapter);
                if (new RealmHelper(this).GetAllNotification().size()>0){
                    recyclerViewNotification.setVisibility(View.VISIBLE);
                    txtNotificationNoData.setVisibility(View.INVISIBLE);
                }else {
                    recyclerViewNotification.setVisibility(View.GONE);
                    txtNotificationNoData.setVisibility(View.VISIBLE);
                }
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        setNavItemCount(R.id.menu_notification_history,new RealmHelper(getApplicationContext()).GetUnreadNotification().size());
        notificationAdapter = new NotificationAdapter(new RealmHelper(this).GetAllNotification(), this);
        recyclerViewNotification.setAdapter(notificationAdapter);

        if (new RealmHelper(this).GetAllNotification().size()>0){
            recyclerViewNotification.setVisibility(View.VISIBLE);
            txtNotificationNoData.setVisibility(View.INVISIBLE);
        }else {
            recyclerViewNotification.setVisibility(View.GONE);
            txtNotificationNoData.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }
}
//mihir.kadve@prudenttechno.com