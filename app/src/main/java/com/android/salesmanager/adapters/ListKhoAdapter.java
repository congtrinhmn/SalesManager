package com.android.salesmanager.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.salesmanager.R;
import com.android.salesmanager.models.SanPham;

import java.util.ArrayList;

public class ListKhoAdapter extends RecyclerView.Adapter {

    private ArrayList<SanPham> sanPhams;

    public ListKhoAdapter(ArrayList<SanPham> sanPhams) {
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
        View itemView = layoutInflater.inflate(R.layout.item_kho, viewGroup, false);
        return new ViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHoder hoder = (ViewHoder) viewHolder;
        hoder.tvMaSP.setText(sanPhams.get(i).getMa());
        hoder.tvTen.setText(sanPhams.get(i).getTen());
        hoder.tvSLNhap.setText(String.format("%.0f", sanPhams.get(i).getSlNhap()));
        hoder.tvSLTon.setText(String.format("%.0f", sanPhams.get(i).getSlTon()));
        hoder.tvGiaNhap.setText(String.format("%.0f", sanPhams.get(i).getGiaNhap()));
        hoder.tvGiaDX.setText(String.format("%.0f", sanPhams.get(i).getGiaDeXuat()));
        if(sanPhams.get(i).getSlTon()<=0){
            for(int j=0;j< ((ViewGroup) hoder.itemView).getChildCount();j++){
                ((ViewGroup) hoder.itemView).getChildAt(j).setEnabled(false);
            }
            return;
        }
        for(int j=0;j< ((ViewGroup) hoder.itemView).getChildCount();j++){
            ((ViewGroup) hoder.itemView).getChildAt(j).setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }
    public SanPham getItem(int position) { return (SanPham) this.sanPhams.get(position); }
    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvMaSP;
        TextView tvTen;
        TextView tvSLNhap;
        TextView tvSLTon;
        TextView tvGiaDX;
        TextView tvGiaNhap;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvMaSP = itemView.findViewById(R.id.tv_ma_sp);
            tvMaSP = itemView.findViewById(R.id.tv_ma_sp);
            tvTen = itemView.findViewById(R.id.tv_ten_sp);
            tvSLNhap = itemView.findViewById(R.id.tv_sl_nhap);
            tvSLTon = itemView.findViewById(R.id.tv_sl_ton);
            tvGiaNhap = itemView.findViewById(R.id.tv_gia_nhap);
            tvGiaDX = itemView.findViewById(R.id.tv_gia_dx);
        }
    }

}
