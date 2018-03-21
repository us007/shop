package com.silverpixelz.myapplication.ui.search;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.silverpixelz.myapplication.R;
import com.silverpixelz.myapplication.adapter.ProductAdapter;
import com.silverpixelz.myapplication.adapter.ProductInterface;
import com.silverpixelz.myapplication.data.api.model.Product.Product;
import com.silverpixelz.myapplication.utils.GridDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchView, ProductInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar_search)
    ProgressBar progressBarSearch;
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.textView_search)
    TextView textViewSearch;
    @BindView(R.id.searchView)
    EditText searchView;

    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition enter_transition =
                    TransitionInflater.from(this).
                            inflateTransition(R.transition.trns_search);
            getWindow().setAllowEnterTransitionOverlap(true);
            //getWindow().setExitTransition(exit_transition);
            getWindow().setEnterTransition(enter_transition);
        }

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        InitPresenter();
        onAttach();
        SetHomeButton();
        SetupRecyclerView();

        SetSearchView();
    }

    private void SetSearchView() {
       /* AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
        }
        try {
            Field searchField = SearchView.class.getDeclaredField("mCloseButton");
            searchField.setAccessible(true);
            ImageView mSearchCloseButton = (ImageView) searchField.get(searchView);
            if (mSearchCloseButton != null) {
                mSearchCloseButton.setEnabled(false);
                mSearchCloseButton.setAlpha(0);
            }
        } catch (Exception e) {
            // Log.e(TAG, "Error finding close button", e);
        }

        try {
            Field searchField = SearchView.class.getDeclaredField("mCloseButton");
            searchField.setAccessible(true);
            ImageView mSearchCloseButton = (ImageView) searchField.get(searchView);
            if (mSearchCloseButton != null) {
                mSearchCloseButton.setEnabled(false);
                mSearchCloseButton.setAlpha(0);
            }
        } catch (Exception e) {
            // Log.e(TAG, "Error finding close button", e);
        }

        searchView.onActionViewExpanded();
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                final Handler handler = new Handler();
                Runnable runnable = null;
                handler.removeCallbacks(runnable);

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        // Your code here.
                    }
                };
                handler.postDelayed(runnable, 500);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!newText.isEmpty()) {
                            Search(newText);
                        }
                    }
                }, 500);
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showInputMethod(view.findFocus());
                }
            }
        });*/

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    GetText();
                    return true;
                }
                return false;
            }
        });

        /*searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    final Handler handler = new Handler();
                    Runnable runnable = null;
                    handler.removeCallbacks(runnable);

                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            // Your code here.
                        }
                    };
                    handler.postDelayed(runnable, 500);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!charSequence.toString().isEmpty()) {
                                Search(charSequence.toString());
                            }
                        }
                    }, 500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
    }

    private void GetText(){
        String text = searchView.getText().toString();
        if (text.length() == 0) {

        } else {
            Search(searchView.getText().toString());
        }
    }

    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    private void SetupRecyclerView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.product_recyclerview);
        recyclerViewSearch.addItemDecoration(new GridDividerItemDecoration(spacingInPixels, 2));
    }

    private void Search(String string) {
        searchPresenter.Product(string);
    }

    private void SetHomeButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void InitPresenter() {
        searchPresenter = new SearchPresenter();
    }

    @Override
    public void onAttach() {
        searchPresenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        searchPresenter.onDetach();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ShowProgress() {
        progressBarSearch.setVisibility(View.VISIBLE);
        recyclerViewSearch.setVisibility(View.GONE);
        textViewSearch.setVisibility(View.GONE);
    }

    @Override
    public void HideProgress() {
        progressBarSearch.setVisibility(View.GONE);
        recyclerViewSearch.setVisibility(View.VISIBLE);
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
                        GetText();
                    }
                });
        builder.show();
    }

    @Override
    public void ProductResponse(List<Product> productList) {
        if (productList.size() > 0) {
            //textViewSearch.setVisibility(View.INVISIBLE);
            SetProductAdapter(productList);
        } else {
            // textViewSearch.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"No Product Available",Toast.LENGTH_LONG).show();
        }
    }

    private void SetProductAdapter(List<Product> productList) {
        ProductAdapter subCategoryAdapter = new ProductAdapter(productList, this, this);
        recyclerViewSearch.setAdapter(subCategoryAdapter);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void CartResponse(Boolean flag) {

    }
}
