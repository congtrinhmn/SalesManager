package com.android.salesmanager.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabIndicatorView extends FrameLayout implements View.OnClickListener {

    private ColorStateList colorStateList;
    private LinearLayout contentLayout;
    private Context context;
    private OnTabChangeListener onTabChangeListener;
    private int selectedIndex = -1;
    private int ID_ICON = Integer.valueOf(String.valueOf(1), 16).intValue();
    private int ID_TITLE = Integer.valueOf(String.valueOf(2), 16).intValue();


    public TabIndicatorView(Context context) {
        super(context);
        initView(context);
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.contentLayout = new LinearLayout(context);
        this.contentLayout.setLayoutParams(layoutParams);
        this.contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(this.contentLayout);
        LayoutParams dividerParams = new LayoutParams(-1, 2);
        View dividerView = new View(context);
        dividerView.setBackgroundColor(Color.parseColor("#B1B1B1"));
        dividerView.setLayoutParams(dividerParams);
        addView(dividerView);
    }

    public void addTab(int srcIcon, String title) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.weight = 1.0f;
        FrameLayout frameLayout = new FrameLayout(this.context);
        frameLayout.setPadding((int) convertDpToPixel(8.0f, this.context), 0, (int) convertDpToPixel(8.0f, this.context), 0);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setTag(this.contentLayout.getChildCount());
        LayoutParams iconParams = new LayoutParams((int) convertDpToPixel(24.0f, this.context), (int) convertDpToPixel(24.0f, this.context));
        iconParams.topMargin = (int) convertDpToPixel(8.0f, this.context);
        iconParams.gravity = 49;
        ImageView iconImageView = new ImageView(this.context);
        iconImageView.setImageResource(srcIcon);
        iconImageView.setLayoutParams(iconParams);
        iconImageView.setId(this.ID_ICON);
        frameLayout.addView(iconImageView);
        LayoutParams titleParams = new LayoutParams(-2, -2);
        titleParams.bottomMargin = (int) convertDpToPixel(8.0f, this.context);
        titleParams.gravity = 81;
        TextView titleTextView = new TextView(this.context);
        titleTextView.setText(title);
        titleTextView.setTextSize(11.0f);
        titleTextView.setSingleLine(true);
        titleTextView.setLayoutParams(titleParams);
        titleTextView.setId(this.ID_TITLE);
        frameLayout.addView(titleTextView);
        this.contentLayout.addView(frameLayout);
        frameLayout.setOnClickListener((OnClickListener) this);
        updateView();
    }

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        this.onTabChangeListener =  onTabChangeListener;
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        updateView();
    }

    public void setTintListColor(int src) {
        this.colorStateList = this.context.getResources().getColorStateList(src);
    }

    private void updateView() {
        for (int i = 0; i < this.contentLayout.getChildCount(); i++) {
            FrameLayout item = (FrameLayout) this.contentLayout.getChildAt(i);
            TextView tv = item.findViewById(this.ID_TITLE);
            ImageView imageView = item.findViewById(this.ID_ICON);
            LayoutParams titleParams = (LayoutParams) tv.getLayoutParams();
            LayoutParams iconParams = (LayoutParams) imageView.getLayoutParams();
            if (this.colorStateList != null) {
                tv.setTextColor(this.colorStateList);
                //imageView.setColorFilter(colorStateList.getColorForState(getDrawableState(), 0));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageView.setImageTintList(colorStateList);
                }
            }
            if (i == this.selectedIndex) {
                iconParams.topMargin = (int) convertDpToPixel(6.0f, this.context);
                titleParams.bottomMargin = (int) convertDpToPixel(10.0f, this.context);
                item.setSelected(true);
            } else {
                iconParams.topMargin = (int) convertDpToPixel(8.0f, this.context);
                titleParams.bottomMargin = (int) convertDpToPixel(8.0f, this.context);
                item.setSelected(false);
            }
            tv.setLayoutParams(titleParams);
            imageView.setLayoutParams(iconParams);
        }
    }

    public void onClick(View view) {
        int index = ((Integer) view.getTag()).intValue();
        if (this.selectedIndex != index) {
            this.selectedIndex = index;
            updateView();
            if (this.onTabChangeListener != null) {
                this.onTabChangeListener.onChanged();
            }
        }
    }

    private float convertDpToPixel(float dp, Context context) {
        return dp * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    private float convertPixelsToDp(float px, Context context) {
        return px / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public interface OnTabChangeListener {
        void onChanged();
    }
}
