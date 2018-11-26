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

import com.android.salesmanager.MainActivity;
import com.android.salesmanager.R;
import com.android.salesmanager.adapters.ListDaBanAdapter;
import com.android.salesmanager.adapters.ListSTTAdapter;
import com.android.salesmanager.adapters.SearchTypeAdapter;
import com.android.salesmanager.databases.Database;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.models.SearchType;
import com.android.salesmanager.utils.Utils;

import java.util.ArrayList;


public class DaBanFragment extends Fragment {
    private ListDaBanAdapter listDaBanAdapter;
    private ListSTTAdapter listSTTAdapter;
    private MainActivity mainActivity;
    private LinearLayout llContent;
    private LinearLayout llLeftTopHeader;
    private LinearLayout llTopHeader;
    private View rootView;
    private RecyclerView rvContent;
    private RecyclerView rvStt;
    private boolean rvContentScrolling = false;
    private boolean rvSttScrolling = false;
    ArrayList<SanPham> sanPhams = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_da_ban, container, false);
        this.mainActivity = (MainActivity) getActivity();

        initView();
        this.mainActivity.setOnSearchListener(new MainActivity.OnSearchListener() {
            @Override
            public void onTextChanged(EditText editText) {
                DaBanFragment.this.search();
            }

            @Override
            public void onSpinnerChanged(SearchType searchType) {
                DaBanFragment.this.search();
            }
        });


        return rootView;
    }

    private void initView() {
        this.llContent = this.rootView.findViewById(R.id.ll_content);
        this.llLeftTopHeader = this.rootView.findViewById(R.id.ll_left_top_header);
        this.llTopHeader = this.rootView.findViewById(R.id.ll_top_header);
        this.rvContent = this.rootView.findViewById(R.id.rv_content);
        this.rvStt = this.rootView.findViewById(R.id.rv_stt);
        llContent.setVisibility(View.VISIBLE);


        rvContent.setHasFixedSize(true);
        rvStt.setHasFixedSize(true);
        rvContent.setLayoutManager(new LinearLayoutManager(DaBanFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
        rvStt.setLayoutManager(new LinearLayoutManager(DaBanFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
        rvContent.setItemAnimator(new DefaultItemAnimator());
        rvStt.setItemAnimator(new DefaultItemAnimator());
        listDaBanAdapter = new ListDaBanAdapter(sanPhams);
        listSTTAdapter = new ListSTTAdapter(sanPhams);
        rvContent.setAdapter(listDaBanAdapter);
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

    public void onResume() {
        super.onResume();
        search();

    }

    private void search() {
        Database.getInstance(getActivity()).getSPDaBan(Utils.replaceSpecialCharacter(this.mainActivity.edtSearch.getText().toString()), ((SearchTypeAdapter) this.mainActivity.spnSearchType.getAdapter()).getItem(this.mainActivity.spnSearchType.getSelectedItemPosition()), new Database.DatabaseListener() {
            public void callback(Object[] data) {
                DaBanFragment.this.listSTTAdapter.notifyDataSetChanged((ArrayList) data[0]);
                DaBanFragment.this.listDaBanAdapter.notifyDataSetChanged((ArrayList) data[0]);
                if (DaBanFragment.this.listDaBanAdapter.getItemCount() > 0) {
                    DaBanFragment.this.llContent.setVisibility(View.VISIBLE);
                } else {
                    DaBanFragment.this.llContent.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



}
