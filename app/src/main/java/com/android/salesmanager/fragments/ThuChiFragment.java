package com.android.salesmanager.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.salesmanager.MainActivity;
import com.android.salesmanager.R;
import com.android.salesmanager.adapters.ListSTTAdapter;
import com.android.salesmanager.adapters.ListThuChiAdapter;
import com.android.salesmanager.adapters.SearchTypeAdapter;
import com.android.salesmanager.databases.Database;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.models.SearchType;
import com.android.salesmanager.utils.Utils;

import java.util.ArrayList;


public class ThuChiFragment extends Fragment {
    private ListThuChiAdapter listThuChiAdapter;
    private ListSTTAdapter listSTTAdapter;
    private LinearLayout llContent;
    private LinearLayout llLeftTopHeader;
    private LinearLayout llTopHeader;
    private TextView tvTongLai;
    private TextView tvTongThu;
    private TextView tvTongVon;
    private MainActivity mainActivity;
    private View rootView;
    private RecyclerView rvContent;
    private RecyclerView rvStt;
    private boolean rvContentScrolling = false;
    private boolean rvSttScrolling = false;
    private ArrayList<SanPham> sanPhams = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_thu_chi, container, false);
        this.mainActivity = (MainActivity) getActivity();
        this.mainActivity.setOnSearchListener(new MainActivity.OnSearchListener() {
            @Override
            public void onTextChanged(EditText editText) {
                ThuChiFragment.this.search();
            }

            @Override
            public void onSpinnerChanged(SearchType searchType) {
                ThuChiFragment.this.search();
            }
        });

        initView();

        return rootView;
    }

    public void onResume() {
        super.onResume();
        search();
    }

    private void initView() {
        this.llContent = this.rootView.findViewById(R.id.ll_content);
        this.llLeftTopHeader = this.rootView.findViewById(R.id.ll_left_top_header);
        this.llTopHeader = this.rootView.findViewById(R.id.ll_top_header);
        this.rvContent = this.rootView.findViewById(R.id.rv_content);
        this.rvStt = this.rootView.findViewById(R.id.rv_stt);
        this.tvTongLai = this.rootView.findViewById(R.id.tv_tong_lai);
        this.tvTongThu = this.rootView.findViewById(R.id.tv_tong_thu);
        this.tvTongVon = this.rootView.findViewById(R.id.tv_tong_von);
        llContent.setVisibility(View.VISIBLE);


        rvContent.setHasFixedSize(true);
        rvStt.setHasFixedSize(true);
        rvContent.setLayoutManager(new LinearLayoutManager(ThuChiFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
        rvStt.setLayoutManager(new LinearLayoutManager(ThuChiFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
        rvContent.setItemAnimator(new DefaultItemAnimator());
        rvStt.setItemAnimator(new DefaultItemAnimator());
        listThuChiAdapter=new ListThuChiAdapter(sanPhams);
        listSTTAdapter=new ListSTTAdapter(sanPhams);
        rvContent.setAdapter(listThuChiAdapter);
        rvStt.setAdapter(listSTTAdapter);

        rvContent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvContentScrolling) {
                    rvSttScrolling = true;
                    rvStt.scrollBy(dx, dy);
                    rvSttScrolling = false;
                }
            }
        });

        rvStt.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvSttScrolling) {
                    rvContentScrolling = true;
                    rvContent.scrollBy(dx, dy);
                    rvContentScrolling = false;
                }
            }
        });
    }

    private void search() {
        Database.getInstance(getActivity()).getThuChi(Utils.replaceSpecialCharacter(this.mainActivity.edtSearch.getText().toString()), ((SearchTypeAdapter) this.mainActivity.spnSearchType.getAdapter()).getItem(this.mainActivity.spnSearchType.getSelectedItemPosition()), new Database.DatabaseListener() {
            public void callback(Object[] data) {
                ThuChiFragment.this.listSTTAdapter.notifyDataSetChanged((ArrayList) data[0]);
                ThuChiFragment.this.listThuChiAdapter.notifyDataSetChanged((ArrayList) data[0]);
                ThuChiFragment.this.tvTongThu.setText(Utils.numberFormat(((Double) data[1]).doubleValue()));
                ThuChiFragment.this.tvTongLai.setText(Utils.numberFormat(((Double) data[2]).doubleValue()));
                ThuChiFragment.this.tvTongVon.setText(Utils.numberFormat(((Double) data[3]).doubleValue()));
                if (ThuChiFragment.this.listThuChiAdapter.getItemCount() > 0) {
                    ThuChiFragment.this.llContent.setVisibility(View.VISIBLE);
                } else {
                    ThuChiFragment.this.llContent.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


}
