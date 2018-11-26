package com.android.salesmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.salesmanager.R;
import com.android.salesmanager.models.SearchType;

import java.util.ArrayList;

public class SearchTypeAdapter extends BaseAdapter {
    private ArrayList<SearchType> searchTypes;

    public SearchTypeAdapter(ArrayList<SearchType> searchTypes) {
        this.searchTypes = searchTypes;
    }

    public int getCount() {
        return this.searchTypes.size();
    }

    public SearchType getItem(int i) {
        return (SearchType) this.searchTypes.get(i);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_spinner_search_type, viewGroup, false);
        ((TextView) view).setText(this.searchTypes.get(i).title);
        return view;
    }
}
