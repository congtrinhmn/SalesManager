package com.android.salesmanager.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.salesmanager.BanSPActivity;
import com.android.salesmanager.MainActivity;
import com.android.salesmanager.R;
import com.android.salesmanager.ThemSPKhoActivity;
import com.android.salesmanager.adapters.ListKhoAdapter;
import com.android.salesmanager.adapters.ListSTTAdapter;
import com.android.salesmanager.adapters.SearchTypeAdapter;
import com.android.salesmanager.databases.Database;
import com.android.salesmanager.models.SanPham;
import com.android.salesmanager.models.SearchType;
import com.android.salesmanager.utils.ActionSheet;
import com.android.salesmanager.utils.Dialog;
import com.android.salesmanager.utils.ItemClickSupport;
import com.android.salesmanager.utils.NhapThemSPDialog;
import com.android.salesmanager.utils.Utils;

import java.util.ArrayList;


public class KhoFragment extends Fragment implements View.OnClickListener {
    private ListKhoAdapter listKhoAdapter;
    private ListSTTAdapter listSTTAdapter;
    private MainActivity mainActivity;
    private LinearLayout llContent;
    private View rootView;
    private RecyclerView rvContent;
    private RecyclerView rvStt;
    private boolean rvContentScrolling = false;
    private boolean rvSttScrolling = false;
    ArrayList<SanPham> sanPhams = new ArrayList<>();
    private String sortType = " ORDER BY id DESC;";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_kho, container, false);
        this.mainActivity = (MainActivity) getActivity();
        initView();
        this.mainActivity.setOnSearchListener(new MainActivity.OnSearchListener() {
            @Override
            public void onTextChanged(EditText editText) {
                KhoFragment.this.search();
            }

            @Override
            public void onSpinnerChanged(SearchType searchType) {
                KhoFragment.this.search();
            }
        });

        ItemClickSupport.addTo(rvContent).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showActionSheet(position);
            }
        });
        ItemClickSupport.addTo(rvStt).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showActionSheet(position);
            }
        });
        return rootView;
    }

    private void initView() {
        this.llContent = this.rootView.findViewById(R.id.ll_content);
        TextView tvStt = this.rootView.findViewById(R.id.tv_stt_header);
        TextView tvMaSP = this.rootView.findViewById(R.id.tv_ma_sp_header);
        TextView tvTenSP = this.rootView.findViewById(R.id.tv_ten_sp_header);
        TextView tvSLNhap = this.rootView.findViewById(R.id.tv_sl_nhap_header);
        TextView tvSLTon = this.rootView.findViewById(R.id.tv_sl_ton_header);
        TextView tvGiaDX = this.rootView.findViewById(R.id.tv_gia_dx_header);
        TextView tvGiaNhap = this.rootView.findViewById(R.id.tv_gia_nhap_header);
        tvStt.setOnClickListener(this);
        tvMaSP.setOnClickListener(this);
        tvTenSP.setOnClickListener(this);
        tvSLNhap.setOnClickListener(this);
        tvSLTon.setOnClickListener(this);
        tvGiaDX.setOnClickListener(this);
        tvGiaNhap.setOnClickListener(this);
        this.rvContent = this.rootView.findViewById(R.id.rv_content);
        this.rvStt = this.rootView.findViewById(R.id.rv_stt);
        llContent.setVisibility(View.VISIBLE);


        rvContent.setHasFixedSize(true);
        rvStt.setHasFixedSize(true);
        rvContent.setLayoutManager(new LinearLayoutManager(KhoFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
        rvStt.setLayoutManager(new LinearLayoutManager(KhoFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
        rvContent.setItemAnimator(new DefaultItemAnimator());
        rvStt.setItemAnimator(new DefaultItemAnimator());
        listKhoAdapter = new ListKhoAdapter(sanPhams);
        listSTTAdapter = new ListSTTAdapter(sanPhams);
        rvContent.setAdapter(listKhoAdapter);
        rvStt.setAdapter(listSTTAdapter);

        rvContent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvContentScrolling) {
                    rvSttScrolling = true;
                    rvStt.scrollBy(dx, dy);
                    rvSttScrolling = false;

                }
            }
        });

        rvStt.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvSttScrolling) {
                    rvContentScrolling = true;
                    rvContent.scrollBy(dx, dy);
                    rvContentScrolling = false;

                }
            }
        });
    }

    private void showActionSheet(int position) {
        final SanPham sanPham = this.listKhoAdapter.getItem(position);
        ActionSheet actionSheet = new ActionSheet(getActivity());
        actionSheet.setTitle(sanPham.getTen());
        actionSheet.addItem(0, getResources().getString(R.string.ban_san_pham));
        actionSheet.addItem(1, getResources().getString(R.string.nhap_them_san_pham));
        actionSheet.addItem(2, getResources().getString(R.string.sua_san_pham));
        actionSheet.addItem(3, getResources().getString(R.string.xoa_san_pham));

        actionSheet.setOnActionSheetListener(new ActionSheet.OnActionSheetListener() {
            @Override
            public void onItemClick(ActionSheet actionSheet, ActionSheet.ActionSheetItem actionSheetItem, int i) {
                switch (actionSheetItem.id) {
                    case 0:
                        Intent intent = new Intent(KhoFragment.this.getActivity(), BanSPActivity.class);
                        intent.putExtra(ThemSPKhoActivity.EXTRA_EDITMODE, true);
                        intent.putExtra(ThemSPKhoActivity.EXTRA_SAN_PHAM, sanPham);
                        startActivity(intent);
                        break;
                    case 1:
                        NhapThemSPDialog nhapThemSPDialog = new NhapThemSPDialog(KhoFragment.this.getActivity(), sanPham.getMa());
                        nhapThemSPDialog.setConfirm(KhoFragment.this.getResources().getString(R.string.them), new NhapThemSPDialog.OnClickListener() {
                            public void onClick(NhapThemSPDialog dialog) {
                                dialog.dismiss();
                                Dialog dialogAlert = new Dialog(KhoFragment.this.getActivity());
                                dialogAlert.setMessage(KhoFragment.this.getResources().getString(R.string.thao_tac_thanh_cong));
                                dialogAlert.setConfirm(getResources().getString(R.string.xong), new Dialog.OnClickListener() {
                                    public void onClick(Dialog dialog) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogAlert.showAlert();
                                KhoFragment.this.search();
                            }
                        });
                        nhapThemSPDialog.show();
                        break;
                    case 2:
                        Intent intent2 = new Intent(KhoFragment.this.getActivity(), ThemSPKhoActivity.class);
                        intent2.putExtra(ThemSPKhoActivity.EXTRA_EDITMODE, true);
                        intent2.putExtra(ThemSPKhoActivity.EXTRA_SAN_PHAM, sanPham);
                        KhoFragment.this.startActivity(intent2);
                        break;
                    case 3:
                        Dialog confirmDialog = new Dialog(KhoFragment.this.getActivity());
                        confirmDialog.setMessage(KhoFragment.this.getResources().getString(R.string.ban_co_muon_xoa) + " " + sanPham.getTen());
                        confirmDialog.setCancel(KhoFragment.this.getResources().getString(R.string.huy), new Dialog.OnClickListener() {
                            public void onClick(Dialog dialog) {
                                dialog.dismiss();
                            }
                        });
                        confirmDialog.setConfirm(KhoFragment.this.getResources().getString(R.string.xoa), new Dialog.OnClickListener() {
                            public void onClick(Dialog dialog) {
                                dialog.dismiss();
                                String error = Database.getInstance(KhoFragment.this.getActivity()).xoaSanPhamKho(sanPham.getMa());
                                Dialog dialogAlert;
                                if (error == null) {
                                    dialogAlert = new Dialog(KhoFragment.this.getActivity());
                                    dialogAlert.setMessage(KhoFragment.this.getResources().getString(R.string.thao_tac_thanh_cong));
                                    dialogAlert.setConfirm(getResources().getString(R.string.xong), new Dialog.OnClickListener() {
                                        public void onClick(Dialog dialog) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialogAlert.showAlert();
                                } else {
                                    dialogAlert = new Dialog(KhoFragment.this.getActivity());
                                    dialogAlert.setMessage(error);
                                    dialogAlert.setConfirm(getResources().getString(R.string.xong), new Dialog.OnClickListener() {
                                        public void onClick(Dialog dialog) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialogAlert.showAlert();
                                }
                                KhoFragment.this.search();
                            }
                        });
                        confirmDialog.showConfirm();

                        break;
                }
                actionSheet.dismiss();
            }
        });
        actionSheet.show();

    }


    public void onResume() {
        super.onResume();
        search();

    }


    private void search() {
        Database.getInstance(getActivity()).getSPKho(Utils.replaceSpecialCharacter(this.mainActivity.edtSearch.getText().toString()), ((SearchTypeAdapter) this.mainActivity.spnSearchType.getAdapter()).getItem(this.mainActivity.spnSearchType.getSelectedItemPosition()), sortType, new Database.DatabaseListener() {
            public void callback(Object[] data) {
                KhoFragment.this.listSTTAdapter.notifyDataSetChanged((ArrayList) data[0]);
                KhoFragment.this.listKhoAdapter.notifyDataSetChanged((ArrayList) data[0]);
                if (KhoFragment.this.listKhoAdapter.getItemCount() > 0) {
                    KhoFragment.this.llContent.setVisibility(View.VISIBLE);
                } else {
                    KhoFragment.this.llContent.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_stt_header:
                if (this.sortType.equals(" ORDER BY id DESC;"))
                    this.sortType = " ORDER BY id ASC;";
                else this.sortType = " ORDER BY id DESC;";
                search();
                return;
            case R.id.tv_ma_sp_header:
                if (this.sortType.equals(" ORDER BY ma DESC;"))
                    this.sortType = " ORDER BY ma ASC;";
                else this.sortType = " ORDER BY ma DESC;";
                search();
                return;
            case R.id.tv_ten_sp_header:
                if (this.sortType.equals(" ORDER BY ten DESC;"))
                    this.sortType = " ORDER BY ten ASC;";
                else this.sortType = " ORDER BY ten DESC;";
                search();
                return;
            case R.id.tv_sl_nhap_header:
                if (this.sortType.equals(" ORDER BY sl_nhap DESC;"))
                    this.sortType = " ORDER BY sl_nhap ASC;";
                else this.sortType = " ORDER BY sl_nhap DESC;";
                search();
                return;
            case R.id.tv_sl_ton_header:
                if (this.sortType.equals(" ORDER BY sl_ton DESC;"))
                    this.sortType = " ORDER BY sl_ton ASC;";
                else this.sortType = " ORDER BY sl_ton DESC;";
                search();
                return;
            case R.id.tv_gia_dx_header:
                if (this.sortType.equals(" ORDER BY gia_dx DESC;"))
                    this.sortType = " ORDER BY gia_dx ASC;";
                else this.sortType = " ORDER BY gia_dx DESC;";
                search();
                return;
            case R.id.tv_gia_nhap_header:
                if (this.sortType.equals(" ORDER BY gia_nhap DESC;"))
                    this.sortType = " ORDER BY gia_nhap ASC;";
                else this.sortType = " ORDER BY gia_nhap DESC;";
                search();
                return;
            default:
        }
    }
}
