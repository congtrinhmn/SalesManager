package com.android.salesmanager.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.salesmanager.R;

import java.util.ArrayList;

public class ActionSheet extends Dialog {
    private ArrayList<ActionSheetItem> actionSheetItems = new ArrayList();
    private Button btnClose;
    private Context context;
    private OnActionSheetListener onActionSheetListener;
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private ActionSheetAdapter actionSheetAdapter;

    public ActionSheet(Context context) {
        super(context);
        this.context = context;
        getWindow().requestFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.gravity = 80;
        getWindow().setAttributes(wlp);
        setContentView(R.layout.dialog_action_sheet);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        wlp.width = displaymetrics.widthPixels;
        getWindow().setAttributes(wlp);
        initView();
    }

    public void setOnActionSheetListener(OnActionSheetListener onActionSheetListener) {
        this.onActionSheetListener = onActionSheetListener;
    }

    private void initView() {
        this.tvTitle = findViewById(R.id.tv_title);
        this.recyclerView = findViewById(R.id.recycler_view);
        this.btnClose = findViewById(R.id.btn_cancel);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        actionSheetAdapter = new ActionSheetAdapter(actionSheetItems);
        this.recyclerView.setAdapter(actionSheetAdapter);
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ActionSheet.this.dismiss();
            }
        });
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setCancel(String title) {
        this.btnClose.setText(title);
    }

    public void addItem(int id, String title) {
        this.actionSheetItems.add(new ActionSheetItem(id, title));
    }

    public interface OnActionSheetListener {
        void onItemClick(ActionSheet actionSheet, ActionSheetItem actionSheetItem, int i);
    }

    public static class ActionSheetItem {
        public int id;
        public String title;

        public ActionSheetItem(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    public class ActionSheetAdapter extends RecyclerView.Adapter {

        private ArrayList<ActionSheetItem> actionSheetItems;

        public ActionSheetAdapter(ArrayList<ActionSheetItem> actionSheetItems) {
            this.actionSheetItems = actionSheetItems;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_sheet, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            if (position < this.actionSheetItems.size() - 1) {
                holder.divider.setVisibility(View.VISIBLE);
            } else {
                holder.divider.setVisibility(View.INVISIBLE);
            }

            holder.textView.setText(this.actionSheetItems.get(position).title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (ActionSheet.this.onActionSheetListener != null) {
                        ActionSheet.this.onActionSheetListener.onItemClick(ActionSheet.this, ActionSheetAdapter.this.actionSheetItems.get(position), position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.actionSheetItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View divider;
            public TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.textView = itemView.findViewById(R.id.text_view);
                this.divider = itemView.findViewById(R.id.divider);
            }
        }
    }


}