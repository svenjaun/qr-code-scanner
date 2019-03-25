package com.example.qr_code_scanner;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.util.ArrayList;

public class QrCodeListAdapter extends RecyclerView.Adapter<ViewHolder> {
    @NonNull
    private Activity activity;
    @NonNull
    private ArrayList<QRCodeModel> qrCodes;


    TextView qrcodeDataTV;

    public QrCodeListAdapter(@NonNull Activity activity, @NonNull ArrayList<QRCodeModel> qrCodes) {
        this.activity = activity;
        this.qrCodes = qrCodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.list_element, viewGroup, false);
        qrcodeDataTV = qrcodeDataTV.findViewById(R.id.list_element_qr_code_date);
        ViewHolder viewHolder = new ViewHolder(activity, v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        QRCodeModel qrCode = qrCodes.get(i);
        setViews(viewHolder, qrCode);
    }

    @Override
    public int getItemCount() {
        return qrCodes.size();
    }

    private void setViews(@NonNull ViewHolder viewHolder, @NonNull QRCodeModel qrCode) {
        viewHolder.getListElementNameTextView().setText(qrCode.getName());
        viewHolder.getListEllementDateTextView().setText(qrCode.getDate().toString());
    }

    @NonNull
    public ArrayList<QRCodeModel> getQrCodes() {
        return qrCodes;
    }
}
