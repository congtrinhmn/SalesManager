package com.android.salesmanager.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.salesmanager.R;
import com.android.salesmanager.databases.Database;

public class NhapThemSPDialog extends Dialog implements View.OnClickListener {
    private Button btnCancel;
    private Button btnConfirm;
    private EditText edtSoLuong;
    private LinearLayout llContent;
    private String maSP;
    private OnClickListener onCancelClickListener;
    private OnClickListener onConfirmClickListener;
    private TextView tvTitle;
    private View vDivider;

    public NhapThemSPDialog(Context context, String maSP) {
        super(context);
        getWindow().requestFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        this.maSP = maSP;
        setContentView(R.layout.dialog_nhap_them);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
            wlp.width = (int) ((((float) displaymetrics.widthPixels) / 100.0f) * 80.0f);
        } else {
            wlp.width = (int) ((((float) displaymetrics.heightPixels) / 100.0f) * 80.0f);
        }
        getWindow().setAttributes(wlp);
        this.llContent = findViewById(R.id.content);
        this.btnConfirm = findViewById(R.id.btn_confirm);
        this.btnCancel = findViewById(R.id.btn_cancel);
        this.tvTitle = findViewById(R.id.title);
        this.edtSoLuong = findViewById(R.id.edt_sl_nhap);
        this.vDivider = findViewById(R.id.v_divider);
        this.btnConfirm.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
    }

    public void show() {
        super.show();
        this.btnCancel.setVisibility(View.VISIBLE);
        this.vDivider.setVisibility(View.VISIBLE);
        this.btnConfirm.setVisibility(View.VISIBLE);
    }

    public void setConfirm(String title, OnClickListener onClickListener) {
        this.onConfirmClickListener = onClickListener;
        this.btnConfirm.setText(title);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel :
                dismiss();
                return;
            case R.id.btn_confirm :
                Database.getInstance(getContext()).nhapThemSanPham(this.maSP, Utils.tryParseDouble(this.edtSoLuong.getText().toString(), 0).doubleValue());
                if (this.onConfirmClickListener != null) {
                    this.onConfirmClickListener.onClick(this);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public interface OnClickListener {
        void onClick(NhapThemSPDialog nhapThemSPDialog);
    }
}
