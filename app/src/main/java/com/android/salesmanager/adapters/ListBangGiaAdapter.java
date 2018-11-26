package com.android.salesmanager.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.salesmanager.R;
import com.android.salesmanager.models.SanPham;

import java.util.ArrayList;

public class ListBangGiaAdapter extends RecyclerView.Adapter {

    private ArrayList<SanPham> sanPhams;

    public ListBangGiaAdapter(ArrayList<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }
    public void notifyDataSetChanged(ArrayList<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
        super.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_bang_gia, viewGroup, false);
        return new ListBangGiaAdapter.ViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ListBangGiaAdapter.ViewHoder hoder = (ListBangGiaAdapter.ViewHoder) viewHolder;
        hoder.tvMaSP.setText(sanPhams.get(i).getMa());
        hoder.tvTen.setText(sanPhams.get(i).getTen());
        hoder.tvGiaDX.setText(String.format("%.0f", sanPhams.get(i).getGiaDeXuat()));
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvMaSP;
        TextView tvTen;
        TextView tvGiaDX;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvMaSP = itemView.findViewById(R.id.tv_ma_sp);
            tvTen = itemView.findViewById(R.id.tv_ten_sp);
            tvGiaDX = itemView.findViewById(R.id.tv_gia_dx);
        }
    }
}
