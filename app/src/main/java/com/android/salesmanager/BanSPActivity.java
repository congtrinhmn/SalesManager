package com.android.salesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.salesmanager.databases.Database;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.utils.Dialog;
import com.android.salesmanager.utils.SystemTime;
import com.android.salesmanager.utils.Utils;

public class BanSPActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private Button btnBanRa;
    private TextView tvSanPham;
    private TextView tvThoiGian;
    private TextView tvGioiHanSL;
    private EditText edtGiaBanRa;
    private EditText edtSoLuong;
    private SanPham sanPhamSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_sp);
        initView();
    }

    private void initView() {
        this.ivBack = findViewById(R.id.iv_back);
        this.btnBanRa = findViewById(R.id.btn_ban_ra);
        this.tvSanPham = findViewById(R.id.tv_sp);
        this.tvThoiGian = findViewById(R.id.tv_thoi_gian);
        this.tvGioiHanSL = findViewById(R.id.tv_gioi_han_sl);
        this.edtGiaBanRa = findViewById(R.id.edt_gia_ban_ra);
        this.edtSoLuong = findViewById(R.id.edt_sl);
        this.tvSanPham.setOnClickListener(this);
        this.tvThoiGian.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
        this.btnBanRa.setOnClickListener(this);
        this.tvThoiGian.setEnabled(false);
        this.edtGiaBanRa.setEnabled(false);
        this.edtSoLuong.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.btn_ban_ra:
                ban();
                return;
            case R.id.tv_sp:
                Intent intent = new Intent(this, ChonSPActivity.class);
                intent.putExtra(ChonSPActivity.SAN_PHAM_SELECTED, this.sanPhamSelected);
                startActivityForResult(intent, ChonSPActivity.REQUEST_CODE);
                return;
            case R.id.tv_thoi_gian:
                return;
            default:
                return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChonSPActivity.REQUEST_CODE && resultCode == -1) {
            this.sanPhamSelected = (SanPham) data.getSerializableExtra(ChonSPActivity.SAN_PHAM_SELECTED);
            this.tvSanPham.setText(this.sanPhamSelected.getMa() + " - " + this.sanPhamSelected.getTen());
            this.tvGioiHanSL.setText("1-" + Utils.numberFormat(this.sanPhamSelected.getSlTon()));
            this.edtGiaBanRa.setText(String.format("%.0f", this.sanPhamSelected.getGiaDeXuat()));
            this.edtSoLuong.setText("1");
            this.edtGiaBanRa.setEnabled(true);
            this.edtSoLuong.setEnabled(true);
            this.tvThoiGian.setEnabled(true);


        }
    }

    private void ban() {
        final com.android.salesmanager.utils.Dialog alert = new com.android.salesmanager.utils.Dialog(this);
        alert.setTitle(R.string.app_name);
        alert.setConfirm(getResources().getString(R.string.xong), new Dialog.OnClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                alert.dismiss();
            }
        });
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        if(this.sanPhamSelected==null){
            alert.setMessage(getResources().getString(R.string.chua_chon_sam_pham));
        }else {
            double giaBanRa=Double.valueOf(this.edtGiaBanRa.getText().toString());
            double sl=Double.valueOf(this.edtSoLuong.getText().toString());
            this.sanPhamSelected.setBanVoiGia(giaBanRa);
            this.sanPhamSelected.setSl(sl);
            String error=Database.getInstance(this).themSPDaBan(this.sanPhamSelected);
            if(error!=null){
                alert.setMessage(error);
            }else {
                alert.setMessage(getResources().getString(R.string.them_sp_thanh_cong));
                alert.setConfirm(getResources().getString(R.string.xong), new Dialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        alert.dismiss();
                        BanSPActivity.this.finish();
                    }
                });
            }
        }
        alert.showAlert();









    }
}