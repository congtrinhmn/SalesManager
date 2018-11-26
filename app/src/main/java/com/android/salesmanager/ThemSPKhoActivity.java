package com.android.salesmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.salesmanager.databases.Database;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.utils.Dialog;
import com.android.salesmanager.utils.Utils;

public class ThemSPKhoActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_EDITMODE = "editmode";
    public static final String EXTRA_SAN_PHAM = "SP";
    private boolean editMode = false;
    private Database database;
    private Button btnThem;
    private EditText edtMaSP;
    private EditText edtTenSP;
    private EditText edtGiaNhap;
    private EditText edtGiaDX;
    private EditText edtSLNhap;
    private ImageView ivBack;
    private ImageView ivMaSPInfo;
    private ImageView ivTenSPInfo;
    private TextView tvTenSPInfo;
    private TextView tvMaSPInfo;
    private LinearLayout llMaSP;
    private LinearLayout llSoLuong;
    private SanPham sanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_spkho);
        database = new Database(this);
        //database.themSPKho(new SanPham(1, 1, "M01", "Test", 1300, 680, 400043500, 4003000));

        this.editMode = getIntent().getBooleanExtra(EXTRA_EDITMODE, false);
        if (this.editMode) {
            this.sanPham = (SanPham) getIntent().getSerializableExtra(EXTRA_SAN_PHAM);
        }
        initView();
        if (this.editMode) {
            this.edtTenSP.setText(this.sanPham.getTen());
            this.edtGiaNhap.setText(String.format("%.0f",this.sanPham.getGiaNhap()));
            this.edtGiaDX.setText(String.format("%.0f",this.sanPham.getGiaDeXuat()));
        }

    }

    private void initView() {
        this.ivBack = findViewById(R.id.iv_back);
        this.btnThem = findViewById(R.id.btn_them);
        this.edtMaSP = findViewById(R.id.edt_ma_sp);
        this.edtTenSP = findViewById(R.id.edt_ten_sp);
        this.edtGiaNhap = findViewById(R.id.edt_gia_nhap);
        this.edtGiaDX = findViewById(R.id.edt_gia_de_xuat);
        this.edtSLNhap = findViewById(R.id.edt_sl_nhap);
        this.ivMaSPInfo = findViewById(R.id.iv_ma_sp_info);
        this.ivTenSPInfo = findViewById(R.id.iv_ten_sp_info);
        this.tvMaSPInfo = findViewById(R.id.tv_ma_sp_info);
        this.tvTenSPInfo = findViewById(R.id.tv_ten_sp_info);
        this.llMaSP = findViewById(R.id.ll_ma_sp);
        this.llSoLuong = findViewById(R.id.ll_so_luong);
        ivMaSPInfo.setVisibility(View.INVISIBLE);
        ivTenSPInfo.setVisibility(View.INVISIBLE);
        tvTenSPInfo.setVisibility(View.INVISIBLE);
        tvMaSPInfo.setVisibility(View.INVISIBLE);
        if (this.editMode) {
            this.llMaSP.setVisibility(View.GONE);
            this.llSoLuong.setVisibility(View.GONE);
            this.btnThem.setText(getResources().getString(R.string.luu_lai));
        } else {
            this.llMaSP.setVisibility(View.VISIBLE);
            this.llSoLuong.setVisibility(View.VISIBLE);
            this.btnThem.setText(getResources().getString(R.string.them));
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.btn_them:
                final String error;
                final Dialog alert;
                if (this.editMode) {
                    error = this.database.suaSanPhamKho(new SanPham(this.sanPham.getMa(), this.edtTenSP.getText().toString().trim(), 0, Utils.tryParseDouble(this.edtGiaNhap.getText().toString(), -1), Utils.tryParseDouble(this.edtGiaDX.getText().toString(), -1).doubleValue()), this.sanPham.getTen());
                    alert = new Dialog(this);
                    alert.setTitle(getResources().getString(R.string.app_name));
                    if (error != null) {
                        alert.setMessage(error);
                    } else {
                        alert.setMessage(getResources().getString(R.string.thao_tac_thanh_cong));
                        this.edtMaSP.setText(BuildConfig.FLAVOR);
                        this.edtTenSP.setText(BuildConfig.FLAVOR);
                        this.edtGiaDX.setText(BuildConfig.FLAVOR);
                        this.edtGiaNhap.setText(BuildConfig.FLAVOR);
                        this.edtSLNhap.setText(BuildConfig.FLAVOR);
                        this.ivMaSPInfo.setVisibility(View.GONE);
                        this.tvMaSPInfo.setVisibility(View.GONE);
                        this.ivTenSPInfo.setVisibility(View.GONE);
                        this.tvTenSPInfo.setVisibility(View.GONE);
                        this.edtMaSP.requestFocus();
                    }
                    alert.setConfirm(getResources().getString(R.string.xong), new Dialog.OnClickListener() {
                        public void onClick(Dialog dialog) {
                            if (error == null) {
                                ThemSPKhoActivity.this.finish();
                            }
                            alert.dismiss();
                        }
                    });
                    alert.showAlert();
                    return;
                }

                alert = new Dialog(this);
                alert.setTitle(getResources().getString(R.string.app_name));
                error = this.database.themSPKho(new SanPham(this.edtMaSP.getText().toString().trim(),
                        this.edtTenSP.getText().toString().trim(),
                        Double.valueOf(this.edtSLNhap.getText().toString()),
                        Double.valueOf(this.edtGiaNhap.getText().toString()),
                        Double.valueOf(this.edtGiaDX.getText().toString())));
                if (error != null) {
                    alert.setMessage(error);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.them_sp_thanh_cong), Toast.LENGTH_SHORT).show();
                    finish();
                    return;


                }
        }
    }
}
