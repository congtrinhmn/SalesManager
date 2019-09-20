package com.android.salesmanager.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.salesmanager.R;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.utils.Utils;

import java.util.ArrayList;

public class ListDaBanAdapter extends RecyclerView.Adapter {

    private ArrayList<SanPham> sanPhams;

    public ListDaBanAdapter(ArrayList<SanPham> sanPhams) {
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
        View itemView = layoutInflater.inflate(R.layout.item_da_ban, viewGroup, false);
        return new ListDaBanAdapter.ViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ListDaBanAdapter.ViewHoder hoder = (ListDaBanAdapter.ViewHoder) viewHolder;
        hoder.tvMaSP.setText(sanPhams.get(i).getMa());
        hoder.tvTen.setText(sanPhams.get(i).getTen());
        hoder.tvGiaBanRa.setText(Utils.numberFormat( sanPhams.get(i).getBanVoiGia()));
        hoder.tvSL.setText(Utils.numberFormat( sanPhams.get(i).getSl()));
        hoder.tvThoiGian.setText(sanPhams.get(i).getThoiGian());
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvMaSP;
        TextView tvTen;
        TextView tvGiaBanRa;
        TextView tvSL;
        TextView tvThoiGian;

        ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvMaSP = itemView.findViewById(R.id.tv_ma_sp);
            tvTen = itemView.findViewById(R.id.tv_ten_sp);
            tvGiaBanRa = itemView.findViewById(R.id.tv_gia_ban_ra);
            tvSL = itemView.findViewById(R.id.tv_sl);
            tvThoiGian = itemView.findViewById(R.id.tv_thoi_gian);
        }
    }
}
