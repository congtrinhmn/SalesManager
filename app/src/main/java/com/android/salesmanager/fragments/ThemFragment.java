package com.android.salesmanager.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.salesmanager.R;


public class ThemFragment extends Fragment implements View.OnClickListener {

    private LinearLayout llMatKhau, llDonViTienTe, llGiaoDien, llSanPhamDaAn, llXuatRaTep, llNhapVao;
    private TextView tvMatKhau;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_them, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        llMatKhau = rootView.findViewById(R.id.ll_mat_khau);
        llDonViTienTe = rootView.findViewById(R.id.ll_don_vi_tien_te);
        llGiaoDien = rootView.findViewById(R.id.ll_giao_dien);
        llXuatRaTep = rootView.findViewById(R.id.ll_xuat);
        llNhapVao = rootView.findViewById(R.id.ll_nhap);
        tvMatKhau = rootView.findViewById(R.id.tv_mat_khau);
        this.llMatKhau.setOnClickListener(this);
        this.llDonViTienTe.setOnClickListener(this);
        this.llGiaoDien.setOnClickListener(this);
        this.llXuatRaTep.setOnClickListener(this);
        this.llNhapVao.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mat_khau:
                break;
            case R.id.ll_don_vi_tien_te:
                Toast.makeText(getActivity(), getResources().getString(R.string.chuc_nang_sap_ra_mat), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_giao_dien:
                Toast.makeText(getActivity(), getResources().getString(R.string.chuc_nang_sap_ra_mat), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_san_pham_da_an:
                Toast.makeText(getActivity(), getResources().getString(R.string.chuc_nang_sap_ra_mat), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_nhap:
                Toast.makeText(getActivity(), getResources().getString(R.string.chuc_nang_sap_ra_mat), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_xuat:
                Toast.makeText(getActivity(), getResources().getString(R.string.chuc_nang_sap_ra_mat), Toast.LENGTH_SHORT).show();
        }
    }
}
