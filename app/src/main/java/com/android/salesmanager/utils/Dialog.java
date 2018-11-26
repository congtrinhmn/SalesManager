package com.android.salesmanager.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.salesmanager.R;

public class Dialog extends android.app.Dialog implements View.OnClickListener {
    private Button btnCancel;
    private Button btnConfirm;
    private LinearLayout llContent;
    private OnClickListener onCancelClickListener;
    private OnClickListener onConfirmClickListener;
    private TextView tvMessage;
    private TextView tvTitle;
    private View vDivider;

    public Dialog(Context context) {
        super(context);
        getWindow().requestFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        setContentView(R.layout.dialog);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
            wlp.width = (int) ((((float) displaymetrics.widthPixels) / 100.0f) * 80.0f);
        } else {
            wlp.width = (int) ((((float) displaymetrics.heightPixels) / 100.0f) * 80.0f);
        }
        this.llContent = findViewById(R.id.content);
        this.btnConfirm = findViewById(R.id.btn_confirm);
        this.btnCancel = findViewById(R.id.btn_cancel);
        this.tvTitle = findViewById(R.id.title);
        this.tvMessage = findViewById(R.id.message);
        this.vDivider = findViewById(R.id.v_divider);
        this.btnConfirm.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
    }

    public void showAlert() {
        this.btnCancel.setVisibility(View.GONE);
        this.vDivider.setVisibility(View.GONE);
        this.btnConfirm.setVisibility(View.VISIBLE);
        show();
    }

    public void showConfirm() {
        this.btnCancel.setVisibility(View.VISIBLE);
        this.vDivider.setVisibility(View.VISIBLE);
        this.btnConfirm.setVisibility(View.VISIBLE);
        show();
    }

    public void setCancel(String title, OnClickListener onClickListener) {
        this.onCancelClickListener = onClickListener;
        this.btnCancel.setText(title);
    }

    public void setConfirm(String title, OnClickListener onClickListener) {
        this.onConfirmClickListener = onClickListener;
        this.btnConfirm.setText(title);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setMessage(String message) {
        this.tvMessage.setText(message);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel :
                if (this.onCancelClickListener != null) {
                    this.onCancelClickListener.onClick(this);
                    return;
                }
                return;
            case R.id.btn_confirm :
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
        void onClick(Dialog dialog);
    }
}
