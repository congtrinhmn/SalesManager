package com.android.salesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.salesmanager.adapters.ListChonSPAdapter;
import com.android.salesmanager.adapters.SearchTypeAdapter;
import com.android.salesmanager.databases.Database;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.models.SearchType;
import com.android.salesmanager.utils.ItemClickSupport;
import com.android.salesmanager.utils.Keyboard;
import com.android.salesmanager.utils.Utils;

import java.util.ArrayList;

public class ChonSPActivity extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_CODE = 10;
    public static String SAN_PHAM_SELECTED = "SAN_PHAM_SELECTED";
    private EditText edtSearch;
    private ImageView ivBack;
    private ImageView ivSearch;
    private ListChonSPAdapter listChonSPAdapter = new ListChonSPAdapter(new ArrayList());
    private LinearLayout llContent;
    private LinearLayout llSearch;
    private LinearLayout llTitle;
    private RecyclerView rvSanPham;
    private SanPham sanPhamSelected;
    private ArrayList<SearchType> searchTypes;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_sp);
        this.searchTypes = SearchType.genSeachTypes3(this);
        initView();
        this.sanPhamSelected = (SanPham) getIntent().getSerializableExtra(SAN_PHAM_SELECTED);
        this.listChonSPAdapter.setSanPhamSelected(this.sanPhamSelected);
        this.rvSanPham.setAdapter(this.listChonSPAdapter);
        ItemClickSupport.addTo(rvSanPham).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ChonSPActivity.this.listChonSPAdapter.setSanPhamSelected(ChonSPActivity.this.listChonSPAdapter.getItem(position));
                ChonSPActivity.this.selected();
            }
        });

        Database.getInstance(this).getSPKho(new Database.DatabaseListener() {
            public void callback(Object[] data) {
                ChonSPActivity.this.listChonSPAdapter.notifyDataSetChanged((ArrayList) data[0]);
                if (ChonSPActivity.this.listChonSPAdapter.getItemCount() > 0) {
                    ChonSPActivity.this.llContent.setVisibility(View.VISIBLE);
                } else {
                    ChonSPActivity.this.llContent.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void selected() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(SAN_PHAM_SELECTED, this.listChonSPAdapter.getSanPhamSelected());
        setResult(-1, returnIntent);
        finish();
    }

    private void initView() {
        this.ivBack = findViewById(R.id.iv_back);
        this.ivSearch = findViewById(R.id.iv_search);
        this.llSearch = findViewById(R.id.ll_search);
        this.llTitle = findViewById(R.id.ll_title);
        this.llContent = findViewById(R.id.ll_content);
        this.edtSearch = findViewById(R.id.edt_search);
        this.spinner = findViewById(R.id.spn_search_type);
        this.rvSanPham = findViewById(R.id.rv_san_pham);
        this.ivBack.setOnClickListener(this);
        this.ivSearch.setOnClickListener(this);
        this.rvSanPham.setLayoutManager(new LinearLayoutManager(this));
        this.spinner.setAdapter(new SearchTypeAdapter(this.searchTypes));
        this.edtSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ChonSPActivity.this.search();
            }

            public void afterTextChanged(Editable editable) {
            }
        });
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ChonSPActivity.this.search();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void search() {
        Database.getInstance(this).getSPKho(Utils.replaceSpecialCharacter(this.edtSearch.getText().toString()), ((SearchTypeAdapter) this.spinner.getAdapter()).getItem(this.spinner.getSelectedItemPosition()), new Database.DatabaseListener() {
            public void callback(Object[] data) {
                ChonSPActivity.this.listChonSPAdapter.notifyDataSetChanged((ArrayList) data[0]);
                if (ChonSPActivity.this.listChonSPAdapter.getItemCount() > 0) {
                    ChonSPActivity.this.llContent.setVisibility(View.VISIBLE);
                } else {
                    ChonSPActivity.this.llContent.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (this.llSearch.getVisibility() == View.VISIBLE) {
                    this.ivBack.setImageResource(R.drawable.ic_back);
                    this.llSearch.setVisibility(View.GONE);
                    this.llTitle.setVisibility(View.VISIBLE);
                    this.edtSearch.setText(BuildConfig.FLAVOR);
                    Keyboard.hideKeyboard(this, this.edtSearch);
                    return;
                }
                setResult(0, new Intent());
                finish();
                return;
            case R.id.iv_search:
                this.ivBack.setImageResource(R.drawable.ic_close);
                if (this.llSearch.getVisibility() != View.VISIBLE) {
                    this.llSearch.setVisibility(View.VISIBLE);
                    this.llTitle.setVisibility(View.GONE);
                    this.edtSearch.requestFocus();
                    Keyboard.showKeyboard(this, this.edtSearch);
                    return;
                }
                return;
            default:
        }
    }
}

