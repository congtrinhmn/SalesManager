package com.android.salesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.salesmanager.utils.SharedPrefUtils;

public class SetMatKhauActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private LinearLayout llDoiMatKhau;
    private LinearLayout llMatKhau;
    private TextView tvMatKhau;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_mat_khau);
        initView();
    }

    private void initView() {
        this.ivBack = findViewById(R.id.iv_back);
        this.llMatKhau = findViewById(R.id.ll_mat_khau);
        this.llDoiMatKhau = findViewById(R.id.ll_doi_mat_khau);
        this.tvMatKhau = findViewById(R.id.tv_mat_khau);
        this.ivBack.setOnClickListener(this);
        this.llMatKhau.setOnClickListener(this);
        this.llDoiMatKhau.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        if (SharedPrefUtils.getInstance(this).getPassword() == null) {
            this.llDoiMatKhau.setEnabled(false);
            this.tvMatKhau.setText(getResources().getString(R.string.bat_mat_khau));
            return;
        }
        this.llDoiMatKhau.setEnabled(true);
        this.tvMatKhau.setText(getResources().getString(R.string.tat_mat_khau));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back :
                finish();
                return;
            case R.id.ll_mat_khau :
                Intent intentBatMK;
                if (SharedPrefUtils.getInstance(this).getPassword() == null) {
                    intentBatMK = new Intent(this, MatKhauActivity.class);
                    intentBatMK.putExtra(MatKhauActivity.EXTRA_TYPE, -3);
                    startActivity(intentBatMK);
                    return;
                }
                intentBatMK = new Intent(this, MatKhauActivity.class);
                intentBatMK.putExtra(MatKhauActivity.EXTRA_TYPE, -4);
                startActivity(intentBatMK);
                return;
            case R.id.ll_doi_mat_khau :
                Intent intentDoiMK = new Intent(this, MatKhauActivity.class);
                intentDoiMK.putExtra(MatKhauActivity.EXTRA_TYPE, -5);
                startActivity(intentDoiMK);
                return;
            default:
        }
    }
}
