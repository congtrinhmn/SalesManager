package com.android.salesmanager.models;

import android.content.Context;

import com.android.salesmanager.R;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchType {
    public static int TYPE_ALL = 0;
    public static int TYPE_MA = 1;
    public static int TYPE_TEN = 2;
    public static int TYPE_NGAY = 3;
    public static int TYPE_TUAN = 4;
    public static int TYPE_THANG = 5;
    public static int TYPE_NAM = 6;
    public String title;
    public int value;

    public SearchType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public static ArrayList<SearchType> genSeachTypes3(Context context) {
        ArrayList<SearchType> searchTypes = new ArrayList();
        searchTypes.add(new SearchType(TYPE_ALL, context.getResources().getString(R.string.tat_ca)));
        searchTypes.add(new SearchType(TYPE_MA, context.getResources().getString(R.string.ma_sp)));
        searchTypes.add(new SearchType(TYPE_TEN, context.getResources().getString(R.string.ten_sp_2)));
        return searchTypes;
    }

    public static ArrayList<SearchType> genSeachTypes7(Context context) {
        Calendar calendar = Calendar.getInstance();
        ArrayList<SearchType> searchTypes = new ArrayList();
        searchTypes.add(new SearchType(TYPE_ALL, context.getResources().getString(R.string.tat_ca)));
        searchTypes.add(new SearchType(TYPE_MA, context.getResources().getString(R.string.ma_sp)));
        searchTypes.add(new SearchType(TYPE_TEN, context.getResources().getString(R.string.ten_sp_2)));
        searchTypes.add(new SearchType(TYPE_NGAY, context.getResources().getString(R.string.hom_nay)));
        searchTypes.add(new SearchType(TYPE_TUAN, context.getResources().getString(R.string.tuan)));
        searchTypes.add(new SearchType(TYPE_THANG, context.getResources().getStringArray(R.array.ten_thang)[calendar.get(Calendar.MONTH)]));
        searchTypes.add(new SearchType(TYPE_NAM, context.getResources().getString(R.string.nam) + calendar.get(Calendar.YEAR)));
        return searchTypes;
    }
}
