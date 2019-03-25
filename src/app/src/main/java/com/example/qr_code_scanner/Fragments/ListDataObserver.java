package com.example.qr_code_scanner.Fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.qr_code_scanner.QrCodeListAdapter;
import com.example.qr_code_scanner.R;

public class ListDataObserver extends RecyclerView.AdapterDataObserver {
    @NonNull
    private QrCodeListAdapter listAdapter;

    RecyclerView recyclerView;
    LinearLayout emptyListView;

    ListDataObserver(@NonNull View view, @NonNull QrCodeListAdapter listAdapter) {
        this.listAdapter = listAdapter;
        recyclerView = view.findViewById(R.id.list_fragment_recycler_view);
        emptyListView = view.findViewById(R.id.list_fragment_empty);
        init(view);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        toggleEmptyView();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        toggleEmptyView();
    }

    private void init(@NonNull View view) {
        toggleEmptyView();
    }

    private void toggleEmptyView() {
        int itemCount = listAdapter.getQrCodes().size();
        emptyListView.setVisibility(itemCount == 0 ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(itemCount == 0 ? View.GONE : View.VISIBLE);
    }
}
