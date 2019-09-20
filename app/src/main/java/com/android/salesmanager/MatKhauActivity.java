package com.android.salesmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.salesmanager.R;
import com.android.salesmanager.utils.SharedPrefUtils;

public class MatKhauActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_OLD_TYPE = "OLD_TYPE";
    public static final String EXTRA_PW = "PW";
    public static final String EXTRA_TYPE = "TYPE";
    public static final int REQUEST_CODE_BAT_MK = 1;
    public static final int REQUEST_CODE_DOI_MK = 2;
    public static final int REQUEST_CODE_MK_MOI = 3;
    public static final int VAL_EXTRA_BAT_MK = -3;
    public static final int VAL_EXTRA_DOI_MK = -5;
    public static final int VAL_EXTRA_NHAP_LAI_MK = -7;
    public static final int VAL_EXTRA_NHAP_MK_MOI = -6;
    public static final int VAL_EXTRA_OPEN_APP = -1;
    public static final int VAL_EXTRA_OPEN_PW = -2;
    public static final int VAL_EXTRA_TAT_MK = -4;
    private ImageView ivBack;
    private LinearLayout llBackspace;
    private LinearLayout llKeyboard;
    private LinearLayout llPassword;
    private String oldPassword;
    private int oldType;
    private String password = BuildConfig.FLAVOR;
    private int passwordLength = 4;
    private TextView tvError;
    private TextView tvTitle;
    private int type;
    private View vEmpty;

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_khau);
        initView();
        Intent intent = getIntent();
        this.type = intent.getIntExtra(EXTRA_TYPE, VAL_EXTRA_OPEN_APP);
        switch (this.type) {
            case VAL_EXTRA_NHAP_LAI_MK /*-7*/:
                this.tvTitle.setText(getResources().getString(R.string.nhap_lai_mat_khau_moi_cua_ban));
                this.ivBack.setVisibility(0);
                this.oldPassword = intent.getStringExtra(EXTRA_PW);
                this.oldType = intent.getIntExtra(EXTRA_OLD_TYPE, VAL_EXTRA_OPEN_APP);
                return;
            case VAL_EXTRA_NHAP_MK_MOI /*-6*/:
                this.tvTitle.setText(getResources().getString(R.string.nhap_mat_khau_moi_cua_ban));
                this.ivBack.setVisibility(0);
                return;
            case VAL_EXTRA_DOI_MK /*-5*/:
                this.tvTitle.setText(getResources().getString(R.string.nhap_mat_khau_cu_cua_ban));
                this.ivBack.setVisibility(0);
                return;
            case VAL_EXTRA_TAT_MK /*-4*/:
                this.tvTitle.setText(getResources().getString(R.string.nhap_mat_khau_cua_ban));
                this.ivBack.setVisibility(0);
                return;
            case VAL_EXTRA_BAT_MK /*-3*/:
                this.tvTitle.setText(getResources().getString(R.string.nhap_mat_khau_moi_cua_ban));
                this.ivBack.setVisibility(0);
                return;
            case VAL_EXTRA_OPEN_PW /*-2*/:
                this.tvTitle.setText(getResources().getString(R.string.nhap_mat_khau_cua_ban));
                this.ivBack.setVisibility(0);
                if (SharedPrefUtils.getInstance(this).getPassword() == null) {
                    openPW();
                    return;
                }
                return;
            case VAL_EXTRA_OPEN_APP /*-1*/:
                this.tvTitle.setText(getResources().getString(R.string.nhap_mat_khau_cua_ban));
                this.ivBack.setVisibility(8);
                if (SharedPrefUtils.getInstance(this).getPassword() == null) {
                    openApp();
                    return;
                }
                return;
            default:
        }
    }

    protected void onResume() {
        super.onResume();
        this.password = BuildConfig.FLAVOR;
        updateLLPassword();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BAT_MK && resultCode == VAL_EXTRA_OPEN_APP) {
            finish();
        } else if (requestCode == REQUEST_CODE_DOI_MK && resultCode == VAL_EXTRA_OPEN_APP) {
            finish();
        } else if (requestCode == REQUEST_CODE_MK_MOI && resultCode == VAL_EXTRA_OPEN_APP) {
            setResult(VAL_EXTRA_OPEN_APP);
            finish();
        }
    }

    private void initView() {
        this.ivBack = findViewById(R.id.iv_back);
        this.llKeyboard = findViewById(R.id.ll_keyboard);
        this.llBackspace = findViewById(R.id.ll_backspace);
        this.llPassword = findViewById(R.id.ll_mat_khau);
        this.tvError = findViewById(R.id.tv_error);
        this.tvTitle = findViewById(R.id.tv_title);
        this.vEmpty = findViewById(R.id.v_empty);
        this.vEmpty.setActivated(true);
        this.llBackspace.setActivated(true);
        this.ivBack.setVisibility(View.GONE);
        this.tvError.setText(BuildConfig.FLAVOR);
        for (int i = 0; i < this.llKeyboard.getChildCount(); i += REQUEST_CODE_BAT_MK) {
            if (this.llKeyboard.getChildAt(i) instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) this.llKeyboard.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j += REQUEST_CODE_BAT_MK) {
                    if (linearLayout.getChildAt(j) instanceof TextView) {
                        final TextView textView = (TextView) linearLayout.getChildAt(j);
                        textView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                if (MatKhauActivity.this.password.length() < MatKhauActivity.this.passwordLength) {
                                    MatKhauActivity.this.tvError.setText(BuildConfig.FLAVOR);
                                    MatKhauActivity.this.password = MatKhauActivity.this.password + textView.getText();
                                    MatKhauActivity.this.updateLLPassword();
                                    if (MatKhauActivity.this.password.length() == MatKhauActivity.this.passwordLength) {
                                        MatKhauActivity.this.finishPassword();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
        this.llBackspace.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
    }

    private void finishPassword() {
        switch (this.type) {
            case VAL_EXTRA_NHAP_LAI_MK /*-7*/:
                switch (this.oldType) {
                    case VAL_EXTRA_NHAP_MK_MOI /*-6*/:
                        if (this.password.equals(this.oldPassword)) {
                            SharedPrefUtils.getInstance(this).setPassword(this.password);
                            setResult(VAL_EXTRA_OPEN_APP);
                            finish();
                            return;
                        }
                        showError(getResources().getString(R.string.hai_mat_khau_khong_khop));
                        return;
                    case VAL_EXTRA_BAT_MK /*-3*/:
                        if (this.password.equals(this.oldPassword)) {
                            SharedPrefUtils.getInstance(this).setPassword(this.password);
                            setResult(VAL_EXTRA_OPEN_APP);
                            finish();
                            return;
                        }
                        showError(getResources().getString(R.string.hai_mat_khau_khong_khop));
                        return;
                    default:
                        return;
                }
            case VAL_EXTRA_NHAP_MK_MOI /*-6*/:
                Intent intentDOIMK = new Intent(this, MatKhauActivity.class);
                intentDOIMK.putExtra(EXTRA_TYPE, VAL_EXTRA_NHAP_LAI_MK);
                intentDOIMK.putExtra(EXTRA_OLD_TYPE, VAL_EXTRA_NHAP_MK_MOI);
                intentDOIMK.putExtra(EXTRA_PW, this.password);
                startActivityForResult(intentDOIMK, REQUEST_CODE_MK_MOI);
                return;
            case VAL_EXTRA_DOI_MK /*-5*/:
                if (this.password.equals(SharedPrefUtils.getInstance(this).getPassword())) {
                    Intent intentMKMoi = new Intent(this, MatKhauActivity.class);
                    intentMKMoi.putExtra(EXTRA_TYPE, VAL_EXTRA_NHAP_MK_MOI);
                    startActivityForResult(intentMKMoi, REQUEST_CODE_DOI_MK);
                    return;
                }
                showError(getResources().getString(R.string.sai_mat_khau));
                return;
            case VAL_EXTRA_TAT_MK /*-4*/:
                if (this.password.equals(SharedPrefUtils.getInstance(this).getPassword())) {
                    SharedPrefUtils.getInstance(this).setPassword(null);
                    finish();
                    return;
                }
                showError(getResources().getString(R.string.sai_mat_khau));
                return;
            case VAL_EXTRA_BAT_MK /*-3*/:
                Intent intentBatMK = new Intent(this, MatKhauActivity.class);
                intentBatMK.putExtra(EXTRA_TYPE, VAL_EXTRA_NHAP_LAI_MK);
                intentBatMK.putExtra(EXTRA_OLD_TYPE, VAL_EXTRA_BAT_MK);
                intentBatMK.putExtra(EXTRA_PW, this.password);
                startActivityForResult(intentBatMK, REQUEST_CODE_BAT_MK);
                return;
            case VAL_EXTRA_OPEN_PW /*-2*/:
                if (this.password.equals(SharedPrefUtils.getInstance(this).getPassword())) {
                    openPW();
                    return;
                } else {
                    showError(getResources().getString(R.string.sai_mat_khau));
                    return;
                }
            case VAL_EXTRA_OPEN_APP /*-1*/:
                if (this.password.equals(SharedPrefUtils.getInstance(this).getPassword())) {
                    openApp();
                    return;
                } else {
                    showError(getResources().getString(R.string.sai_mat_khau));
                    return;
                }
            default:
        }
    }

    private void showError(String error) {
        this.tvError.setText(error);
        this.llPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_error_password));
        this.password = BuildConfig.FLAVOR;
        updateLLPassword();
    }

    private void openApp() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void openPW() {
        finish();
        startActivity(new Intent(this, SetMatKhauActivity.class));
    }

    private void updateLLPassword() {
        for (int i = 0; i < this.llPassword.getChildCount(); i += REQUEST_CODE_BAT_MK) {
            if (i < this.password.length()) {
                this.llPassword.getChildAt(i).setSelected(true);
            } else {
                this.llPassword.getChildAt(i).setSelected(false);
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back :
                finish();
                return;
            case R.id.ll_backspace :
                if (this.password.length() > 0) {
                    this.password = this.password.substring(0, this.password.length() + VAL_EXTRA_OPEN_APP);
                    updateLLPassword();
                    return;
                }
                return;
            default:

        }
    }
}
