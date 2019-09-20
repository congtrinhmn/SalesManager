package com.android.salesmanager;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.salesmanager.adapters.SearchTypeAdapter;
import com.android.salesmanager.databases.Database;
import com.android.salesmanager.fragments.BangGiaFragment;
import com.android.salesmanager.fragments.DaBanFragment;
import com.android.salesmanager.fragments.KhoFragment;
import com.android.salesmanager.fragments.ThemFragment;
import com.android.salesmanager.fragments.ThuChiFragment;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.models.SearchType;
import com.android.salesmanager.utils.SystemTime;
import com.android.salesmanager.widgets.TabIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llSearch;
    private LinearLayout llTitle;
    private ImageView ivAdd;
    private ImageView ivSearch;
    private ImageView ivClose;
    public Spinner spnSearchType;
    public EditText edtSearch;
    private TextView tvTitle;
    private TabIndicatorView tabIndicatorView;
    private ArrayList<Tab> tabs = new ArrayList<>();
    private ArrayList<SearchType> searchTypes;
    private Resources resources;
    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String language = sp.getString("KEY_LANGUAGE", "en");
        setLanguage(language);

        if (getResources().getConfiguration().orientation == 2) {
            getWindow().requestFeature(1);
            getWindow().setFlags(1024, 1024);
        }
        setContentView(R.layout.activity_main);

        this.resources = getResources();
        this.searchTypes=SearchType.genSeachTypes7(this);
        this.tabs.add(new Tab(R.drawable.ic_da_ban, this.resources.getString(R.string.da_ban), new DaBanFragment()));
        this.tabs.add(new Tab(R.drawable.ic_bang_gia, this.resources.getString(R.string.bang_gia), new BangGiaFragment()));
        this.tabs.add(new Tab(R.drawable.ic_kho, this.resources.getString(R.string.kho), new KhoFragment()));
        this.tabs.add(new Tab(R.drawable.ic_thu_chi, this.resources.getString(R.string.thu_chi), new ThuChiFragment()));
        this.tabs.add(new Tab(R.drawable.ic_nhieu_hon, this.resources.getString(R.string.cai_dat), new ThemFragment()));
        initView();
        this.spnSearchType.setAdapter(new SearchTypeAdapter(this.searchTypes));
        SystemTime.getInstance().getLimYearNow();
    }


    public void initView() {

        this.llTitle = findViewById(R.id.ll_title);
        this.llSearch=findViewById(R.id.ll_search);
        this.tvTitle = findViewById(R.id.title);
        this.ivAdd = findViewById(R.id.iv_add);
        this.ivSearch=findViewById(R.id.iv_search);
        this.ivClose=findViewById(R.id.iv_close);
        this.edtSearch=findViewById(R.id.edt_search);
        this.spnSearchType=findViewById(R.id.spn_search_type);
        this.tabIndicatorView = findViewById(R.id.indicator_view);
        this.tabIndicatorView.setTintListColor(R.drawable.selector_tab_tint_color);
        for (int i = 0; i < this.tabs.size(); i++) {
            Tab tab = this.tabs.get(i);
            if (i == 0) {
                getFragmentManager().beginTransaction().replace(R.id.frame, tab.fragment).commit();
                this.tabIndicatorView.setSelectedIndex(0);
            }
            this.tabIndicatorView.addTab(tab.srcIcon, tab.title);
        }
        this.tabIndicatorView.setOnTabChangeListener(new TabIndicatorView.OnTabChangeListener() {
            @Override
            public void onChanged() {

                if (MainActivity.this.llTitle.getVisibility() != View.VISIBLE) {
                    MainActivity.this.hiddenShowSearch();
                }
                String tabTitle = MainActivity.this.tabs.get(MainActivity.this.tabIndicatorView.getSelectedIndex()).title;
                if (tabTitle.equals(MainActivity.this.resources.getString(R.string.them))) {
                    tabTitle = MainActivity.this.resources.getString(R.string.app_name);
                }
                MainActivity.this.tvTitle.setText(tabTitle);
                MainActivity.this.spnSearchType.setSelection(0);
                MainActivity.this.refreshBtnThem();
                MainActivity.this.refreshBtnSearch();
                MainActivity.this.getFragmentManager().beginTransaction().replace(R.id.frame, (MainActivity.this.tabs.get(MainActivity.this.tabIndicatorView.getSelectedIndex())).fragment).commit();

            }
        });
        this.tvTitle.setText(this.tabs.get(0).title);
        this.ivAdd.setOnClickListener(this);
        this.ivSearch.setOnClickListener(this);
        this.ivClose.setOnClickListener(this);
        this.edtSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                if (MainActivity.this.onSearchListener != null) {
                    MainActivity.this.onSearchListener.onTextChanged(MainActivity.this.edtSearch);
                }
            }
        });
        this.spnSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MainActivity.this.onSearchListener != null) {
                    MainActivity.this.onSearchListener.onSpinnerChanged(MainActivity.this.searchTypes.get(i));
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                String tabTitle = this.tabs.get(this.tabIndicatorView.getSelectedIndex()).title;
                if (tabTitle.equals(this.resources.getString(R.string.kho))) {
                    startActivity(new Intent(MainActivity.this, ThemSPKhoActivity.class));
                    return;
                } else if (tabTitle.equals(this.resources.getString(R.string.da_ban))) {
                    startActivity(new Intent(this, BanSPActivity.class));
                    return;
                }
            case R.id.iv_search :
                hiddenShowSearch();
                return;
            case R.id.iv_close :
                hiddenShowSearch();
                return;
            default:
        }
    }
    public void onBackPressed() {
        if (this.llTitle.getVisibility() != View.VISIBLE) {
            hiddenShowSearch();
        } else {
            super.onBackPressed();
        }
    }
    private void hiddenShowSearch() {
        if (this.llSearch.getVisibility() != View.VISIBLE) {
            this.llSearch.setVisibility(View.VISIBLE);
            this.llTitle.setVisibility(View.GONE);
            this.edtSearch.requestFocus();
            com.android.salesmanager.utils.Keyboard.showKeyboard(this, this.edtSearch);
            return;
        }
        this.llSearch.setVisibility(View.GONE);
        this.llTitle.setVisibility(View.VISIBLE);
        this.edtSearch.setText(BuildConfig.FLAVOR);
        this.spnSearchType.setSelection(0, true);
        com.android.salesmanager.utils.Keyboard.hideKeyboard(this, this.edtSearch);

    }
    private void refreshBtnThem() {
        String tabTitle = this.tabs.get(this.tabIndicatorView.getSelectedIndex()).title;
        if (tabTitle.equals(this.resources.getString(R.string.kho)) || (tabTitle.equals(this.resources.getString(R.string.da_ban)) && Database.getInstance(this).countKho() > 0)) {
            this.ivAdd.setVisibility(View.VISIBLE);
        } else {
            this.ivAdd.setVisibility(View.GONE);
        }
    }

    private void refreshBtnSearch() {
        String tabTitle = this.tabs.get(this.tabIndicatorView.getSelectedIndex()).title;
        if (tabTitle.equals(this.resources.getString(R.string.da_ban))) {
            if (Database.getInstance(this).countDaBan() > 0) {
                this.ivSearch.setVisibility(View.VISIBLE);
            } else {
                this.ivSearch.setVisibility(View.GONE);
            }
        } else if (tabTitle.equals(this.resources.getString(R.string.kho)) || tabTitle.equals(this.resources.getString(R.string.bang_gia))) {
            if (Database.getInstance(this).countKho() > 0) {
                this.ivSearch.setVisibility(View.VISIBLE);
            } else {
                this.ivSearch.setVisibility(View.GONE);
            }
        } else if (!tabTitle.equals(this.resources.getString(R.string.thu_chi)) && !tabTitle.equals(this.resources.getString(R.string.bang_gia))) {
            this.ivSearch.setVisibility(View.GONE);
        } else if (Database.getInstance(this).countKho() > 0) {
            this.ivSearch.setVisibility(View.VISIBLE);
        } else {
            this.ivSearch.setVisibility(View.GONE);
        }
    }




    public interface OnSearchListener {
        void onTextChanged(EditText editText);
        void onSpinnerChanged(SearchType searchType);
    }

    private class Tab implements Serializable {
        Fragment fragment;
        int srcIcon;
        String title;

        Tab(int srcIcon, String title, Fragment fragment) {
            this.srcIcon = srcIcon;
            this.title = title;
            this.fragment = fragment;
        }
    }

    public void showListLanguage(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.language)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "en";
                        switch (which){
                            case 1: //VI
                                language = "vi";
                                break;
                        }

                        setLanguage(language);

                        //save
                        SharedPreferences sp = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("KEY_LANGUAGE", language);
                        editor.commit();

                        //refresh UI
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }).create().show();
    }

    void setLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration con = resources.getConfiguration();
        con.locale = locale;
        resources.updateConfiguration(con, resources.getDisplayMetrics());
    }
}
