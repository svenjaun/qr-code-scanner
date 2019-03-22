package com.example.qr_code_scanner.Fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.example.qr_code_scanner.QrCodeListAdapter;
import com.example.qr_code_scanner.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDataObserver extends RecyclerView.AdapterDataObserver {
    @NonNull private QrCodeListAdapter listAdapter;

    @BindView(R.id.list_fragment_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.list_fragment_empty) LinearLayout emptyListView;

    public ListDataObserver(@NonNull View view, @NonNull QrCodeListAdapter listAdapter) {
        this.listAdapter = listAdapter;

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
        ButterKnife.bind(this, view);
        toggleEmptyView();
    }

    private void toggleEmptyView() {
        int itemCount = listAdapter.getQrCodes().size();
        emptyListView.setVisibility(itemCount == 0 ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(itemCount == 0 ? View.GONE : View.VISIBLE);
    }
}
