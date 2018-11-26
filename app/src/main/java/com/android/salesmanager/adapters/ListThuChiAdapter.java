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

public class ListThuChiAdapter extends RecyclerView.Adapter {

    private ArrayList<SanPham> sanPhams;

    public ListThuChiAdapter(ArrayList<SanPham> sanPhams) {
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
        View itemView = layoutInflater.inflate(R.layout.item_thu_chi, viewGroup, false);
        return new ListThuChiAdapter.ViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ListThuChiAdapter.ViewHoder hoder = (ListThuChiAdapter.ViewHoder) viewHolder;
        hoder.tvMaSP.setText(sanPhams.get(i).getMa());
        hoder.tvTen.setText(sanPhams.get(i).getTen());
        hoder.tvSLBanRa.setText(String.format("%.0f", sanPhams.get(i).getSl()));
        hoder.tvLai.setText(String.format("%.0f", sanPhams.get(i).getLai()));
        hoder.tvVon.setText(String.format("%.0f", sanPhams.get(i).getVon()));
        hoder.tvTongThu.setText(String.format("%.0f", sanPhams.get(i).getTongThu()));
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvMaSP;
        TextView tvTen;
        TextView tvSLBanRa;
        TextView tvLai;
        TextView tvVon;
        TextView tvTongThu;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvMaSP = itemView.findViewById(R.id.tv_ma_sp);
            tvTen = itemView.findViewById(R.id.tv_ten_sp);
            tvSLBanRa = itemView.findViewById(R.id.tv_sl_ban_ra);
            tvLai = itemView.findViewById(R.id.tv_lai);
            tvVon = itemView.findViewById(R.id.tv_von);
            tvTongThu = itemView.findViewById(R.id.tv_tong_thu);
        }
    }
}
