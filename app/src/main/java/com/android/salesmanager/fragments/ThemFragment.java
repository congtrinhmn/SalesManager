package com.android.salesmanager.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.salesmanager.MatKhauActivity;
import com.android.salesmanager.R;
import com.android.salesmanager.databases.Database;
import com.android.salesmanager.utils.Dialog;


public class ThemFragment extends Fragment implements View.OnClickListener {

    private LinearLayout llMatKhau;
    private LinearLayout llDonViTienTe;
    private LinearLayout llGiaoDien;
    private LinearLayout llXuatRaTep;
    private LinearLayout llNhapVao;
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
                Intent intent = new Intent(getActivity(), MatKhauActivity.class);
                intent.putExtra(MatKhauActivity.EXTRA_TYPE, -2);
                startActivity(intent);
                break;
            case R.id.ll_don_vi_tien_te:
                Toast.makeText(getActivity(), getResources().getString(R.string.chuc_nang_sap_ra_mat), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_giao_dien:
                Toast.makeText(getActivity(), getResources().getString(R.string.chuc_nang_sap_ra_mat), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_languages:
                Toast.makeText(getActivity(), getResources().getString(R.string.chuc_nang_sap_ra_mat), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_xuat:



                final Dialog alert = new Dialog(getActivity());
                alert.setTitle(R.string.app_name);
                alert.setMessage(getResources().getString(R.string.ban_co_muon_sao_luu));
                alert.setCancel(getResources().getString(R.string.huy), new Dialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        alert.dismiss();
                    }
                });
                alert.setConfirm(getResources().getString(R.string.dong_y), new Dialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        Database.getInstance(getActivity()).exportDB();
                        Toast.makeText(getActivity(), getResources().getString(R.string.xong), Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                    }
                });
                alert.showAlert();

                break;
            case R.id.ll_nhap:
                final Dialog alert2 = new Dialog(getActivity());
                alert2.setTitle(R.string.app_name);
                alert2.setMessage(getResources().getString(R.string.ban_co_muon_thay_the_bang_du_lieu_cu));
                alert2.setCancel(getResources().getString(R.string.huy), new Dialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        alert2.dismiss();
                    }
                });
                alert2.setConfirm(getResources().getString(R.string.dong_y), new Dialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        Database.getInstance(getActivity()).importDB();
                        Toast.makeText(getActivity(), getResources().getString(R.string.xong), Toast.LENGTH_SHORT).show();
                        alert2.dismiss();
                    }
                });
                alert2.showAlert();

        }
    }
}
