package com.flipboard.myapplication.ui.pdf;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.myapplication.R;
import com.flipboard.myapplication.adapter.PdfAdapter;
import com.flipboard.myapplication.adapter.PdfAdapterInterface;
import com.flipboard.myapplication.data.api.model.Pdf.Pdf;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.events.EventNotification;
import com.flipboard.myapplication.ui.LocateStore.LocateStoreActivity;
import com.flipboard.myapplication.ui.Subscribe.SubscribeActivity;
import com.flipboard.myapplication.ui.aboutus.AboutUsActivity;
import com.flipboard.myapplication.ui.cart.CartActivity;
import com.flipboard.myapplication.ui.contactus.ContactUsActivity;
import com.flipboard.myapplication.ui.home.HomeActivity;
import com.flipboard.myapplication.ui.notification.NotificationActivity;
import com.flipboard.myapplication.ui.search.SearchActivity;
import com.flipboard.myapplication.utils.BlurNavigationDrawer.BlurDrawerLayout;
import com.flipboard.myapplication.utils.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class PdfActivity extends AppCompatActivity implements PdfActivityView,PdfAdapterInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_pdf)
    RecyclerView recyclerViewPdf;
    @BindView(R.id.edt_search)
    TextView edtSearch;
    @BindView(R.id.layout_search_main)
    LinearLayout layoutSearchMain;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.navigation_drawer)
    BlurDrawerLayout navigationDrawer;

    private PdfPresenter pdfPresenter;
    private ProgressDialog progressDialog;
    private RealmHelper realmHelper;
    TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition enter_transition =
                    TransitionInflater.from(this).
                            inflateTransition(R.transition.trns_pdf);
            /*Transition fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);*/
            //getWindow().setExitTransition(fade);

            getWindow().setEnterTransition(enter_transition);
            //getWindow().setExitTransition(null);
        }

        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        realmHelper = new RealmHelper(this);

        SetHomeButton();
        InitPresenter();
        onAttach();

        SetupRecyclerView();
        SetProgress();
        InitNavigationDrawer();

        LoadPdf();

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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) PdfActivity.this).toBundle());
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
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) PdfActivity.this).toBundle());
                        } else {
                            startActivity(i);
                        }
                        break;
                    case R.id.menu_about_us:
                        navigationDrawer.closeDrawers();
                        Intent in = new Intent(getApplicationContext(), AboutUsActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(in,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) PdfActivity.this).toBundle());
                        } else {
                            startActivity(in);
                        }
                        break;
                    case R.id.menu_product_pdf:
                        navigationDrawer.closeDrawers();
                        break;
                    case R.id.menu_store_location:
                        navigationDrawer.closeDrawers();
                        Intent ls = new Intent(getApplicationContext(), LocateStoreActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(ls,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) PdfActivity.this).toBundle());
                        } else {
                            startActivity(ls);
                        }
                        break;
                    case R.id.menu_subscribe:
                        navigationDrawer.closeDrawers();
                        Intent sub = new Intent(getApplicationContext(), SubscribeActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(sub,
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) PdfActivity.this).toBundle());
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

    private void LoadPdf() {
        pdfPresenter.Pdf();
    }

    private void SetupRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewPdf.setLayoutManager(mLayoutManager);
        recyclerViewPdf.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.product_recyclerview);
        recyclerViewPdf.addItemDecoration(new DividerItemDecoration(getApplicationContext()));
    }

    private void InitPresenter() {
        pdfPresenter = new PdfPresenter();
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
        pdfPresenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        pdfPresenter.onDetach();
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
        recyclerViewPdf.setVisibility(View.GONE);
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

        FrameLayout cart = (FrameLayout) actionView.findViewById(R.id.btn_cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), CartActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(i,
                            ActivityOptions.makeSceneTransitionAnimation((Activity) PdfActivity.this).toBundle());
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
    public void ResponseError(String title, String message) {
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
                        LoadPdf();

                    }
                });
        builder.show();
    }

    @Override
    public void DownloadResponseError(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.show();
    }

    @Override
    public void PdfResponse(List<Pdf> productList) {
        if (productList.size() > 0) {
            layoutSearchMain.setVisibility(View.VISIBLE);
            SetPdfAdapter(productList);
        } else {
            layoutSearchMain.setVisibility(View.GONE);
        }
    }

    @Override
    public void DownloadResponse(ResponseBody responseBody,String name,int id) {

        SavePdf(responseBody,name,id);

    }

    private void SavePdf(ResponseBody responseBody,String name,int id){
        String extr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Silvergiftz/Pdf";
        File dir = new File(extr);
        if (!dir.exists())
            dir.mkdirs();
        File myPath = new File(extr, name+".pdf");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            fos.write(responseBody.bytes());
            fos.close();
            Toast.makeText(getApplicationContext(), "Pdf Successfully Save in " + extr,
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void SetPdfAdapter(List<Pdf> pdfList) {
        PdfAdapter subCategoryAdapter = new PdfAdapter(pdfList, this,this);
        recyclerViewPdf.setAdapter(subCategoryAdapter);
    }

    @OnClick(R.id.edt_search)
    public void SearchClick() {
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) PdfActivity.this).toBundle());
        } else {
            startActivity(i);
        }
        //startActivity(new Intent(getApplicationContext(), SearchActivity.class));
    }

    @Override
    public void pdf(Pdf pdf) {
        String url = "http://silvergiftz.com/pdf/" + pdf.getDocument_upload();
        String pdfName = pdf.getPdf_name();
        int id = pdf.getId();

        String extr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Silvergiftz/Pdf";
        File dir = new File(extr);
        if (!dir.exists())
            dir.mkdirs();
        File myPath = new File(extr, pdfName + ".pdf");
        if (myPath.exists()) {
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(myPath),"application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            }
        } else {
            pdfPresenter.DownloadPdf(url, pdfName, id);
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
}

