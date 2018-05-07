package com.flipboard.myapplication.ui.cart;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.flipboard.myapplication.R;
import com.flipboard.myapplication.adapter.CartProductAdapter;
import com.flipboard.myapplication.adapter.CartProductInterface;
import com.flipboard.myapplication.data.api.ProductList;
import com.flipboard.myapplication.data.api.apihelper.ApiConfig;
import com.flipboard.myapplication.data.api.apihelper.AppConfig;
import com.flipboard.myapplication.data.api.model.CartRequest;
import com.flipboard.myapplication.data.api.model.Quote.QuoteResponse;
import com.flipboard.myapplication.data.api.model.printOption.PrintOption;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.data.db.model.ProductDB;
import com.flipboard.myapplication.events.EventNotification;
import com.flipboard.myapplication.ui.LocateStore.LocateStoreActivity;
import com.flipboard.myapplication.ui.Subscribe.SubscribeActivity;
import com.flipboard.myapplication.ui.aboutus.AboutUsActivity;
import com.flipboard.myapplication.ui.contactus.ContactUsActivity;
import com.flipboard.myapplication.ui.home.HomeActivity;
import com.flipboard.myapplication.ui.notification.NotificationActivity;
import com.flipboard.myapplication.ui.pdf.PdfActivity;
import com.flipboard.myapplication.ui.search.SearchActivity;
import com.flipboard.myapplication.utils.BlurNavigationDrawer.BlurDrawerLayout;
import com.flipboard.myapplication.utils.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartView, CartProductInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_cart)
    RecyclerView recyclerViewCart;
    @BindView(R.id.txt_no_cart)
    TextView txtNoCart;
    @BindView(R.id.lyt_cart)
    LinearLayout lytCart;
    @BindView(R.id.cart_enquiry_edt_name)
    EditText cartEnquiryEdtName;
    @BindView(R.id.cart_enquiry_edt_contact)
    EditText cartEnquiryEdtContact;
    @BindView(R.id.cart_enquiry_edt_email)
    EditText cartEnquiryEdtEmail;
    @BindView(R.id.cart_enquiry_edt_company)
    EditText cartEnquiryEdtCompany;
    @BindView(R.id.cart_enquiry_edt_message)
    EditText cartEnquiryEdtMessage;
    @BindView(R.id.product_request_quote_txt)
    TextView productRequestQuoteTxt;
    @BindView(R.id.txt_empty_cart)
    TextView txtEmptyCart;
    @BindView(R.id.txt_save_enquiry)
    TextView txtSaveEnquiry;
    @BindView(R.id.txt_continue_shopping)
    TextView txtContinueShopping;
    @BindView(R.id.edt_search)
    TextView edtSearch;
    @BindView(R.id.layout_search_main)
    LinearLayout layoutSearchMain;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.navigation_drawer)
    BlurDrawerLayout navigationDrawer;
    @BindView(R.id.txt_go_continue)
    TextView txtGoContinue;
    @BindView(R.id.layout_no_cart)
    LinearLayout layoutNoCart;
    @BindView(R.id.cart_scroll)
    NestedScrollView cartScroll;
    @BindView(R.id.lyt_scroll_cart)
    RelativeLayout lytScrollCart;

    private CartPresenter cartPresenter;
    private ProgressDialog progressDialog;
    private RealmHelper realmHelper;
    TextView textCartItemCount;
    CartProductAdapter cartProductAdapter;
    List<ProductList> productLists = new ArrayList<ProductList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        setContentView(R.layout.activity_cart);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ButterKnife.bind(this);
        realmHelper = new RealmHelper(this);

        SetHomeButton();
        InitPresenter();
        onAttach();

        SetupRecyclerView();
        SetProgress();
        InitNavigationDrawer();
        PrintOptionList();

        if (realmHelper.GetProductsCount() > 0) {
            lytCart.setVisibility(View.VISIBLE);
            layoutNoCart.setVisibility(View.GONE);
        } else {
            layoutNoCart.setVisibility(View.VISIBLE);
            lytCart.setVisibility(View.GONE);
        }

        layoutSearchMain.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition enter_transition =
                    TransitionInflater.from(this).
                            inflateTransition(R.transition.main_enter);
            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(enter_transition);
        }

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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) CartActivity.this).toBundle());
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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) CartActivity.this).toBundle());
                        } else {
                            startActivity(i);
                        }
                        break;
                    case R.id.menu_about_us:
                        navigationDrawer.closeDrawers();
                        Intent in = new Intent(getApplicationContext(), AboutUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(in,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) CartActivity.this).toBundle());
                        } else {
                            startActivity(in);
                        }
                        break;
                    case R.id.menu_product_pdf:
                        navigationDrawer.closeDrawers();
                        Intent it = new Intent(getApplicationContext(), PdfActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(it,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) CartActivity.this).toBundle());
                        } else {
                            startActivity(it);
                        }
                        break;
                    case R.id.menu_store_location:
                        navigationDrawer.closeDrawers();
                        Intent ls = new Intent(getApplicationContext(), LocateStoreActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(ls,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) CartActivity.this).toBundle());
                        } else {
                            startActivity(ls);
                        }
                        break;
                    case R.id.menu_subscribe:
                        navigationDrawer.closeDrawers();
                        Intent sub = new Intent(getApplicationContext(), SubscribeActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(sub,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) CartActivity.this).toBundle());
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

    private void SetupRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCart.setLayoutManager(mLayoutManager);
        recyclerViewCart.setNestedScrollingEnabled(false);
        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.addItemDecoration(new DividerItemDecoration(this));
    }

    public void PrintOptionList() {
        ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<List<PrintOption>> call = getResponse.GetPrintOption();
        call.enqueue(new Callback<List<PrintOption>>() {
            @Override
            public void onResponse(Call<List<PrintOption>> call, retrofit2.Response<List<PrintOption>> response) {
                List<PrintOption> serverResponse = response.body();
                HideProgress();
                if (response.isSuccessful()) {
                    SetUpData(serverResponse);
                } else {
                    ResponseError("Try Again", "Something went wrong!", 2);
                }
            }

            @Override
            public void onFailure(Call<List<PrintOption>> call, Throwable t) {
              //  HideProgress();
                if (t instanceof java.net.ConnectException) {
                    HideProgress();
                    ResponseError("Internet Error", "Please, Check your Internet Connection!", 2);
                } else if (t instanceof SocketTimeoutException) {
                    call.clone().enqueue(this);
                  //  ResponseError("Timeout Error", "Please, Try again!", 2);
                } else {
                    HideProgress();
                    ResponseError("Internet Error", "Please, Check your Internet Connection!", 2);
                }
            }
        });
    }

    private void SetUpData(List<PrintOption> printOptions) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < printOptions.size(); i++) {
            list.add(printOptions.get(i).getName());
        }
        cartProductAdapter = new CartProductAdapter(realmHelper.GetProduct(), list, this, this);
        recyclerViewCart.setAdapter(cartProductAdapter);
    }

    private void InitPresenter() {
        cartPresenter = new CartPresenter();
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
        cartPresenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        cartPresenter.onDetach();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        onDetach();
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
        recyclerViewCart.setVisibility(View.GONE);
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        textCartItemCount.setText(String.valueOf(Math.min(realmHelper.GetProductsCount(), 99)));
        if (realmHelper.GetProductsCount() > 0) {
            //SetUpData();
            lytCart.setVisibility(View.VISIBLE);
            layoutNoCart.setVisibility(View.GONE);
        } else {
            layoutNoCart.setVisibility(View.VISIBLE);
            lytCart.setVisibility(View.GONE);
        }
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
        progressDialog.show();
    }

    @Override
    public void HideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void ResponseError(String title, String message, int i) {
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
                        if (i == 1) {
                            Validation();
                        }
                        if (i == 2) {
                            PrintOptionList();
                        }
                    }
                });
        builder.show();
    }

    @Override
    public void Delete() {
        invalidateOptionsMenu();
    }

    private void DeleteAll() {
        realmHelper.DeleteAllProduct();
        invalidateOptionsMenu();
    }

    @OnClick({R.id.txt_go_continue, R.id.product_request_quote_txt, R.id.txt_empty_cart, R.id.txt_save_enquiry, R.id.txt_continue_shopping})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_request_quote_txt:
                SaveType();
                Validation();
                break;
            case R.id.txt_empty_cart:
                DeleteAll();
                break;
            case R.id.txt_save_enquiry:
                takeScreenShot();
                break;
            case R.id.txt_continue_shopping:
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.txt_go_continue:
                Intent ih = new Intent(getApplicationContext(), HomeActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(ih,
                            ActivityOptions.makeSceneTransitionAnimation((Activity) CartActivity.this).toBundle());
                } else {
                    startActivity(ih);
                }
                break;
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void Validation() {
        boolean failFlag = false;
        if (cartEnquiryEdtName.getText().toString().trim().length() == 0) {
            failFlag = true;
            cartEnquiryEdtName.setError("Name Cannot be Empty");
        }
        if (cartEnquiryEdtContact.getText().toString().trim().length() == 0) {
            failFlag = true;
            cartEnquiryEdtContact.setError("Contact No. Cannot be Empty");
        }
        if (cartEnquiryEdtEmail.getText().toString().trim().length() == 0) {
            failFlag = true;
            cartEnquiryEdtEmail.setError("Email Cannot be Empty");
        }
        if (!isValidEmail(cartEnquiryEdtEmail.getText().toString())) {
            failFlag = true;
            cartEnquiryEdtEmail.setError("Email is invalid");
        }
        if (cartEnquiryEdtCompany.getText().toString().trim().length() == 0) {
            failFlag = true;
            cartEnquiryEdtCompany.setError("Company Cannot be Empty");
        }
        if (!failFlag) {
            String name = cartEnquiryEdtName.getText().toString();
            String email = cartEnquiryEdtEmail.getText().toString();
            String contact = cartEnquiryEdtContact.getText().toString();
            String company = cartEnquiryEdtCompany.getText().toString();
            String message = cartEnquiryEdtMessage.getText().toString();

            // Subscribe(company,country,email,0,name,contact);
            Save(contact, email, message, name,company, GetType());
        }
    }

    public void Save(String contact, String email, String message, final String name,String companyName, String type) {
        ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient1().create(ApiConfig.class);
        Call<QuoteResponse> call = getResponse.Quote(new CartRequest(contact,
                "C000000008", "", email, "", "", message, name, "", type, "", "",companyName));
        call.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                QuoteResponse serverResponse = response.body();
                HideProgress();
                if (response.isSuccessful()) {
                    productLists.clear();
                    if (serverResponse.getTable().get(0).getSuccess() == 1) {
                        Toast.makeText(getApplicationContext(), serverResponse.getTable().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getTable().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ResponseError("Try Again", "Something went wrong!",1);
                }
            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {
               // HideProgress();
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

    @OnClick(R.id.edt_search)
    public void OnClick() {
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) CartActivity.this).toBundle());
        } else {
            startActivity(i);
        }
    }

    private void SaveType() {
        RealmResults<ProductDB> realmResults = realmHelper.GetProduct();
        for (int i = 0; i < realmResults.size(); i++) {
            ProductList productList = new ProductList();
            productList.setSrno("");
            productList.setId(String.valueOf(i + 1));
            productList.setProduct_id(String.valueOf(realmResults.get(i).getProduct_id()));
            productList.setProduct_name(realmResults.get(i).getProduct_name());
            productList.setProduct_no(realmResults.get(i).getProduct_no());
            productList.setProduct_qty(realmResults.get(i).getProduct_qty());
            productList.setProduct_print_location(realmResults.get(i).getProduct_print_location());
            productList.setProduct_printing_option(realmResults.get(i).getProduct_printing_option());
            productList.setProduct_image(realmResults.get(i).getProduct_image());
            productLists.add(productList);
        }
    }

    public class ProductJsonSerializer implements JsonSerializer<ProductList> {

        @Override
        public JsonElement serialize(ProductList foo, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("Srno", context.serialize(foo.getSrno()));
            object.add("product_id", context.serialize(foo.getProduct_id()));
            object.add("product_name", context.serialize(foo.getProduct_name()));
            object.add("product_no", context.serialize(foo.getProduct_no()));
            object.add("product_qty", context.serialize(foo.getProduct_qty()));
            object.add("product_printing_option", context.serialize(foo.getProduct_printing_option()));
            object.add("product_print_location", context.serialize(foo.getProduct_print_location()));

            object.add("image", context.serialize(foo.getProduct_image()));
            return object;
        }

    }

    private String GetType() {
        Gson gson = new GsonBuilder().registerTypeAdapter(ProductList.class, new ProductJsonSerializer()).create();
        return gson.toJson(productLists);
        /*Gson gson = new Gson();
        String listString = gson.toJson(
                addressCheckoutList,
                new TypeToken<ArrayList<AddressCheckout>>() {
                }.getType());
        try {
            JSONArray jsonArray = new JSONArray(listString);
            return jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }*/
    }

    private void takeScreenShot() {
        View u = cartScroll;

        NestedScrollView z = (NestedScrollView) findViewById(R.id.cart_scroll);
        int totalHeight = z.getChildAt(0).getHeight();
        int totalWidth = z.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(u, totalHeight, totalWidth);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Silvergiftz/Cart";
        File dir = new File(extr);
        if (!dir.exists())
            dir.mkdirs();
        String fileName = "Cart " + getCurrentDate() + ".png";
        File myPath = new File(extr, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");
            Toast.makeText(getApplicationContext(), "Image Successfully Save in " + extr, Toast
                    .LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return mdformat.format(calendar.getTime());
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
