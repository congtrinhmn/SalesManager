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

public class ListSTTAdapter extends RecyclerView.Adapter {
    private ArrayList<SanPham> sanPhams;

    public ListSTTAdapter(ArrayList<SanPham> sanPhams) {
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
        View itemView = layoutInflater.inflate(R.layout.item_stt, viewGroup, false);
        return new ListSTTAdapter.ViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ListSTTAdapter.ViewHoder hoder = (ListSTTAdapter.ViewHoder) viewHolder;
        hoder.tvSTT.setText(String.format("%s", sanPhams.get(i).getStt()));

    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvSTT;

        ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvSTT = itemView.findViewById(R.id.tv_stt);

        }
    }
}

