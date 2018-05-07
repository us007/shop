package com.flipboard.myapplication.ui.Products;

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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.myapplication.R;
import com.flipboard.myapplication.adapter.ProductAdapter;
import com.flipboard.myapplication.adapter.ProductInterface;
import com.flipboard.myapplication.adapter.SubCategoryAdapter;
import com.flipboard.myapplication.adapter.SubCategoryInterface;
import com.flipboard.myapplication.data.api.model.Product.Product;
import com.flipboard.myapplication.data.api.model.SubCategory.SubCategory;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.events.EventNotification;
import com.flipboard.myapplication.helper.Config;
import com.flipboard.myapplication.ui.LocateStore.LocateStoreActivity;
import com.flipboard.myapplication.ui.Subscribe.SubscribeActivity;
import com.flipboard.myapplication.ui.aboutus.AboutUsActivity;
import com.flipboard.myapplication.ui.cart.CartActivity;
import com.flipboard.myapplication.ui.contactus.ContactUsActivity;
import com.flipboard.myapplication.ui.home.HomeActivity;
import com.flipboard.myapplication.ui.notification.NotificationActivity;
import com.flipboard.myapplication.ui.pdf.PdfActivity;
import com.flipboard.myapplication.ui.search.SearchActivity;
import com.flipboard.myapplication.utils.BlurNavigationDrawer.BlurDrawerLayout;
import com.flipboard.myapplication.utils.GridDividerItemDecoration;
import com.flipboard.myapplication.utils.LinearSpacingItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductActivity extends AppCompatActivity implements ProductView, SubCategoryInterface, ProductInterface {

    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_sub_category)
    RecyclerView recyclerViewSubCategory;
    @BindView(R.id.recyclerView_product)
    RecyclerView recyclerViewProduct;
    @BindView(R.id.recyclerView_sub_category_next_btn)
    ImageButton recyclerViewSubCategoryNextBtn;
    @BindView(R.id.edt_search)
    TextView edtSearch;
    @BindView(R.id.layout_sub_category)
    LinearLayout layoutSubCategory;
    @BindView(R.id.layout_search_main)
    LinearLayout layoutSearchMain;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.navigation_drawer)
    BlurDrawerLayout navigationDrawer;

    private ProductPresenter productPresenter;
    private ProgressDialog progressDialog;
    private int Category_id,id;
    private RealmHelper realmHelper;
    TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition enter_transition =
                    TransitionInflater.from(this).
                            inflateTransition(R.transition.trns_product);
            getWindow().setAllowEnterTransitionOverlap(true);
            //getWindow().setExitTransition(exit_transition);
            getWindow().setEnterTransition(enter_transition);
        }

        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        realmHelper = new RealmHelper(this);

        SetHomeButton();
        InitPresenter();
        onAttach();

        SetupRecyclerView();
        SetProgress();
        InitNavigationDrawer();

        Category_id = GetCategoryId();

        LoadSubCategory();

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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductActivity.this).toBundle());
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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductActivity.this).toBundle());
                        } else {
                            startActivity(i);
                        }
                        break;
                    case R.id.menu_about_us:
                        navigationDrawer.closeDrawers();
                        Intent in = new Intent(getApplicationContext(), AboutUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(in,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductActivity.this).toBundle());
                        } else {
                            startActivity(in);
                        }
                        break;
                    case R.id.menu_product_pdf:
                        navigationDrawer.closeDrawers();
                        Intent it = new Intent(getApplicationContext(), PdfActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(it,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductActivity.this).toBundle());
                        } else {
                            startActivity(it);
                        }
                        break;
                    case R.id.menu_store_location:
                        navigationDrawer.closeDrawers();
                        Intent ls = new Intent(getApplicationContext(), LocateStoreActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(ls,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductActivity.this).toBundle());
                        } else {
                            startActivity(ls);
                        }
                        break;
                    case R.id.menu_subscribe:
                        navigationDrawer.closeDrawers();
                        Intent sub = new Intent(getApplicationContext(), SubscribeActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(sub,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductActivity.this).toBundle());
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

    private int GetCategoryId() {
        return getIntent().getIntExtra(Config.CATEGORY_ID, 0);
    }

    private void SetupRecyclerView() {
        SetupSubCategory();
        SetupProduct();
    }

    private void SetupSubCategory() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSubCategory.setLayoutManager(mLayoutManager);
        recyclerViewSubCategory.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.subcategory_recyclerView);
        recyclerViewSubCategory.addItemDecoration(new LinearSpacingItemDecoration(this, spacingInPixels, spacingInPixels));
    }

    private void SetupProduct() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.product_recyclerview);
        recyclerViewProduct.addItemDecoration(new GridDividerItemDecoration(spacingInPixels, 2));
    }

    private void LoadSubCategory() {
        productPresenter.SubCategory(Category_id);
    }

    private void LoadProduct() {
        productPresenter.Product(Category_id);
    }

    private void InitPresenter() {
        productPresenter = new ProductPresenter();
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
        productPresenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        productPresenter.onDetach();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        onDetach();
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
                            ActivityOptions.makeSceneTransitionAnimation((Activity) ProductActivity.this).toBundle());
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

    @Override
    public void ShowProgress() {
        progressDialog.show();
    }

    @Override
    public void HideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void ResponseError(String title, String message,int ref) {
        if (ref==1){
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
                            LoadSubCategory();

                        }
                    });
            builder.show();
        }
        if (ref==2){
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
                            LoadProduct();

                        }
                    });
            builder.show();
        }
        if (ref==3){
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
                            LoadProductSubCategory();

                        }
                    });
            builder.show();
        }
    }

    @Override
    public void ProductResponse(List<Product> productList) {
        if (productList.size() > 0) {
            layoutSearchMain.setVisibility(View.VISIBLE);
            recyclerViewProduct.setVisibility(View.VISIBLE);
            SetProductAdapter(productList);
        } else {
            layoutSearchMain.setVisibility(View.GONE);
            recyclerViewProduct.setVisibility(View.GONE);
        }
    }

    @Override
    public void ProductResponseFromSubCategory(List<Product> productList) {
        if (productList.size() > 0) {
            SetProductAdapter(productList);
            recyclerViewProduct.setVisibility(View.VISIBLE);
        } else {
            recyclerViewProduct.setVisibility(View.GONE);
        }
    }

    private void SetProductAdapter(List<Product> productList) {
        ProductAdapter subCategoryAdapter = new ProductAdapter(productList, this, this);
        recyclerViewProduct.setAdapter(subCategoryAdapter);
    }

    @Override
    public void SubCategoryResponse(List<SubCategory> subCategoryList) {
        if (subCategoryList.size() > 0) {
            SetSubCategoryAdapter(subCategoryList);
            recyclerViewSubCategoryNextBtn.setVisibility(View.VISIBLE);
            recyclerViewSubCategory.setVisibility(View.VISIBLE);
            LoadProduct();
        } else {
            recyclerViewSubCategoryNextBtn.setVisibility(View.GONE);
            recyclerViewSubCategory.setVisibility(View.GONE);
        }
    }

    private void SetSubCategoryAdapter(List<SubCategory> subCategoryList) {
        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(subCategoryList, this, this);
        recyclerViewSubCategory.setAdapter(subCategoryAdapter);
    }

    @OnClick(R.id.recyclerView_sub_category_next_btn)
    public void onClick() {
        LinearLayoutManager llm = (LinearLayoutManager) recyclerViewSubCategory.getLayoutManager();
        recyclerViewSubCategory.getLayoutManager().scrollToPosition(llm.findLastVisibleItemPosition() + 1);
    }

    @Override
    public void ProductFromSubCategory(int id1) {
        id = id1;
        LoadProductSubCategory();
    }

    private void LoadProductSubCategory(){
        productPresenter.ProductSubCategory(id);
    }

    @Override
    public void CartResponse(Boolean flag) {
        if (flag) {
            invalidateOptionsMenu();
            Toast.makeText(getApplicationContext(),"Product Successfully added in Cart",Toast.LENGTH_SHORT).show();

            //textCartItemCount.setText(String.valueOf(Math.min(realmHelper.GetProductsCount(), 99)));
        }
    }

    @OnClick(R.id.edt_search)
    public void SearchClick() {
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) ProductActivity.this).toBundle());
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
