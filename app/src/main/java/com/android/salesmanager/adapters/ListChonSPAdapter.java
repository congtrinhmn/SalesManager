package com.android.salesmanager.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.salesmanager.R;
import com.android.salesmanager.models.SanPham;

import java.util.ArrayList;

public class ListChonSPAdapter extends RecyclerView.Adapter {
    private SanPham sanPhamSelected;
    private ArrayList<SanPham> sanPhams;

    public ListChonSPAdapter(ArrayList<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }

    public void notifyDataSetChanged(ArrayList<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
        super.notifyDataSetChanged();
    }

    public void setSanPhamSelected(SanPham sanPhamSelected) {
        this.sanPhamSelected = sanPhamSelected;
    }

    public SanPham getSanPhamSelected() {
        return this.sanPhamSelected;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_chon_sp, viewGroup, false);
        return new ListChonSPAdapter.ViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ListChonSPAdapter.ViewHoder hoder = (ListChonSPAdapter.ViewHoder) viewHolder;
        SanPham sanPham = this.sanPhams.get(position);
        if (this.sanPhamSelected == null || sanPham.getId() != this.sanPhamSelected.getId()) {
            hoder.ivChecked.setImageDrawable(null);
        } else {
            hoder.ivChecked.setImageResource(R.drawable.ic_checked);
        }
        hoder.tvMaSP.setText(sanPham.getMa());
        hoder.tvTen.setText(sanPham.getTen());
        int i;
        if (sanPham.getSlTon() <= 0) {
            hoder.tvHetHang.setVisibility(View.VISIBLE);
            hoder.itemView.setEnabled(false);
            for (i = 0; i < ((ViewGroup) viewHolder.itemView).getChildCount(); i++) {
                ((ViewGroup) viewHolder.itemView).getChildAt(i).setEnabled(false);
            }
            return;
        }
        hoder.tvHetHang.setVisibility(View.GONE);
        hoder.itemView.setEnabled(true);
        for (i = 0; i < ((ViewGroup) viewHolder.itemView).getChildCount(); i++) {
            ((ViewGroup) viewHolder.itemView).getChildAt(i).setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView ivChecked;
        TextView tvHetHang;
        TextView tvMaSP;
        TextView tvTen;

        ViewHoder(@NonNull View itemView) {
            super(itemView);
            this.ivChecked = itemView.findViewById(R.id.iv_checked);
            this.tvTen = itemView.findViewById(R.id.tv_ten_sp);
            this.tvMaSP = itemView.findViewById(R.id.tv_ma_sp);
            this.tvHetHang = itemView.findViewById(R.id.tv_het_hang);

        }
    }

    public SanPham getItem(int position) {
        return this.sanPhams.get(position);
    }
}
