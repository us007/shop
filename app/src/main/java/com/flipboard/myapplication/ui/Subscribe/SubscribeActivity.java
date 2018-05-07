package com.flipboard.myapplication.ui.Subscribe;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.myapplication.R;
import com.flipboard.myapplication.adapter.CartProductSpinnerAdapter;
import com.flipboard.myapplication.data.api.apihelper.ApiConfig;
import com.flipboard.myapplication.data.api.apihelper.AppConfig;
import com.flipboard.myapplication.data.api.model.Category.Category;
import com.flipboard.myapplication.data.api.model.Country.Country;
import com.flipboard.myapplication.data.api.model.SubscribeRequest;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.events.EventNotification;
import com.flipboard.myapplication.ui.LocateStore.LocateStoreActivity;
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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribeActivity extends AppCompatActivity {

    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_sub_name)
    EditText edtSubName;
    @BindView(R.id.edt_sub_contact)
    EditText edtSubContact;
    @BindView(R.id.edt_sub_email)
    EditText edtSubEmail;
    @BindView(R.id.edt_sub_company)
    EditText edtSubCompany;
    @BindView(R.id.spin_sub_country)
    Spinner spinSubCountry;
    @BindView(R.id.btn_subscribe)
    TextView btnSubscribe;
    @BindView(R.id.edt_search)
    TextView edtSearch;
    @BindView(R.id.layout_search_main)
    LinearLayout layoutSearchMain;
    @BindView(R.id.chk_adv_company)
    CheckBox chkAdvCompany;
    @BindView(R.id.chk_event_company)
    CheckBox chkEventCompany;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.navigation_drawer)
    BlurDrawerLayout navigationDrawer;

    private RealmHelper realmHelper;
    TextView textCartItemCount;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition enter_transition =
                    TransitionInflater.from(this).
                            inflateTransition(R.transition.trns_contact_us);
            getWindow().setAllowEnterTransitionOverlap(true);
            //getWindow().setExitTransition(exit_transition);
            getWindow().setEnterTransition(enter_transition);
        }

        setContentView(R.layout.activity_subscribe);
        ButterKnife.bind(this);

        realmHelper = new RealmHelper(this);

        SetHomeButton();
        SetProgress();
        InitNavigationDrawer();

        CountryList();

        layoutSearchMain.setVisibility(View.VISIBLE);

        setNavItemCount(R.id.menu_notification_history,new RealmHelper(getApplicationContext()).GetUnreadNotification().size());

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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) SubscribeActivity.this).toBundle());
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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) SubscribeActivity.this).toBundle());
                        } else {
                            startActivity(i);
                        }
                        break;
                    case R.id.menu_about_us:
                        navigationDrawer.closeDrawers();
                        Intent in = new Intent(getApplicationContext(), AboutUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(in,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) SubscribeActivity.this).toBundle());
                        } else {
                            startActivity(in);
                        }
                        break;
                    case R.id.menu_product_pdf:
                        navigationDrawer.closeDrawers();
                        Intent it = new Intent(getApplicationContext(), PdfActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(it,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) SubscribeActivity.this).toBundle());
                        } else {
                            startActivity(it);
                        }
                        break;
                    case R.id.menu_store_location:
                        navigationDrawer.closeDrawers();
                        Intent ls = new Intent(getApplicationContext(), LocateStoreActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(ls,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) SubscribeActivity.this).toBundle());
                        } else {
                            startActivity(ls);
                        }
                        break;
                    case R.id.menu_subscribe:
                        navigationDrawer.closeDrawers();
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

    private void SetProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
    }

    public void ShowProgress() {
        progressDialog.show();
    }

    private void HideProgress() {
        progressDialog.dismiss();
    }

    private void ResponseError(String title, String message,int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.setPositiveButton("RETRY",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (i==1){
                            Validation();
                        }
                        if (i==2){
                            CountryList();
                        }
                    }
                });
        builder.show();
    }

    private void Validation() {
        boolean failFlag = false;
        String event = "n";
        String advertising = "n";
        if (edtSubName.getText().toString().trim().length() == 0) {
            failFlag = true;
            edtSubName.setError("Name Cannot be Empty");
        }
        if (edtSubContact.getText().toString().trim().length() == 0) {
            failFlag = true;
            edtSubContact.setError("Contact No. Cannot be Empty");
        }
        if (!isValidEmail(edtSubEmail.getText().toString())) {
            failFlag = true;
            edtSubEmail.setError("Email is invalid");
        }
        if (chkAdvCompany.isChecked()) {
            event = "y";
        }
        if (chkEventCompany.isChecked()) {
            advertising = "y";
        }
        if (!failFlag) {
            String name = edtSubName.getText().toString();
            String email = edtSubEmail.getText().toString();
            String contact = edtSubContact.getText().toString();
            String company = edtSubCompany.getText().toString();
            String country = spinSubCountry.getSelectedItem().toString();

            Subscribe(company, country, email, 0, name, contact, event, advertising);
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void Subscribe(String mCompany, String mCountry, String mEmail, int mId, String mName, String mTelephone, String event, String advertising) {
        ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<String> call = getResponse.Subscribe(new SubscribeRequest(mCompany, mCountry, mEmail,
                mId, mName, mTelephone, event, advertising));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String serverResponse = response.body();
                HideProgress();
                if (response.isSuccessful()) {
                    if (Integer.parseInt(serverResponse) == 1) {
                        Toast.makeText(getApplicationContext(), "Successfully Subscribe", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error, while Subscribing you", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ResponseError("Try Again", "Something went wrong!",1);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof ConnectException) {
                    HideProgress();
                    ResponseError("Internet Error", "Please, Check your Internet Connection!",1);
                } else if (t instanceof SocketTimeoutException) {
                    call.clone().enqueue(this);
                   // ResponseError("Timeout Error", "Please, Try again!",1);
                } else {
                    HideProgress();
                    ResponseError("Internet Error", "Please, Check your Internet Connection!",1);
                }
            }
        });
    }

    public void CountryList(){
        ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<List<Country>> call = getResponse.GetCountry();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, retrofit2.Response<List<Country>> response) {
                List<Country> serverResponse = response.body();
                HideProgress();
                if (response.isSuccessful()) {
                    SetSpinnerCountry(serverResponse);
                } else {
                    ResponseError("Try Again","Something went wrong!",2);
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
               // HideProgress();
                if (t instanceof java.net.ConnectException) {
                    HideProgress();
                    ResponseError("Internet Error", "Please, Check your Internet Connection!",2);
                } else if (t instanceof SocketTimeoutException) {
                    call.clone().enqueue(this);
                  //  ResponseError("Timeout Error", "Please, Try again!",2);
                } else {
                    HideProgress();
                    ResponseError("Internet Error", "Please, Check your Internet Connection!",2);
                }
            }
        });
    }

    private void SetSpinnerCountry(List<Country> countryList) {
        CartProductSpinnerAdapter productSpinnerAdapter = new CartProductSpinnerAdapter(this, Country(countryList));
        spinSubCountry.setAdapter(productSpinnerAdapter);
    }

    private List<String> Country(List<Country> countryList) {
        List<String> list = new ArrayList<>();
        for (int i=0;i<countryList.size();i++){
            list.add(countryList.get(i).getName());
        }
        return list;
    }

    private void SetHomeButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        realmHelper.RealmClose();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        setNavItemCount(R.id.menu_notification_history,new RealmHelper(getApplicationContext()).GetUnreadNotification().size());
        super.onResume();
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
                            ActivityOptions.makeSceneTransitionAnimation((Activity) SubscribeActivity.this).toBundle());
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

    @OnClick(R.id.btn_subscribe)
    public void onClick() {
        Validation();
    }

    @OnClick(R.id.edt_search)
    public void SearchClick() {
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) SubscribeActivity.this).toBundle());
        } else {
            startActivity(i);
        }
        //startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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
}
