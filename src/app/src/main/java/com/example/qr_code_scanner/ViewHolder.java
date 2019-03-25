package com.example.qr_code_scanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {
    @NonNull
    private Context context;


    private SurfaceView scanner;
    private FloatingActionButton floatingActionButton;
    private TextView qrCodeTextview;
    private ImageView listElementQrCode;
    private TextView listElementNameTextView;
    private TextView listEllementDateTextView;
    private ImageView detailQrCode;
    private TextView detailCodeName;
    private TextView detailDate;
    private TextView detailCodeContent;
    private EditText editCodeName;
    private TextView editDate;
    private LinearLayout listFragmentempty;
    private TextView editCodeContent;

    public ViewHolder(@NonNull Context context, @NonNull View view) {
        super(view);
        scanner = scanner.findViewById(R.id.qr_code_surfaceView);
        qrCodeTextview = qrCodeTextview.findViewById(R.id.qr_code_no_code_text);
        floatingActionButton = floatingActionButton.findViewById(R.id.qr_code_fab);
        listElementQrCode = listElementQrCode.findViewById(R.id.list_element_qr_code);
        listElementNameTextView = listElementNameTextView.findViewById(R.id.list_element_qr_code_name);
        listEllementDateTextView = listEllementDateTextView.findViewById(R.id.list_element_qr_code_date);
        detailQrCode = detailQrCode.findViewById(R.id.detail_fragment_qr_code);
        detailCodeName = detailCodeName.findViewById(R.id.detail_fragment_code_name);
        detailDate = detailDate.findViewById(R.id.detail_fragment_date);
        detailCodeContent = detailCodeContent.findViewById(R.id.detail_fragment_code_content);
        editCodeName = editCodeName.findViewById(R.id.edit_fragment_code_name);
        editDate = editDate.findViewById(R.id.edit_fragment_date);
        editCodeContent = editCodeContent.findViewById(R.id.edit_fragment_code_content);
        listFragmentempty = listFragmentempty.findViewById(R.id.list_fragment_empty);


        this.context = context;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    public SurfaceView getScanner() {
        return scanner;
    }

    public TextView getQrCodeTextview() {
        return qrCodeTextview;
    }

    public FloatingActionButton getFloatingActionButton() {
        return floatingActionButton;
    }

    public ImageView getListElementQrCode() {
        return listElementQrCode;
    }

    public TextView getListElementNameTextView() {
        return listElementNameTextView;
    }

    public TextView getListEllementDateTextView() {
        return listEllementDateTextView;
    }

    public ImageView getDetailQrCode() {
        return detailQrCode;
    }

    public TextView getDetailCodeName() {
        return detailCodeName;
    }

    public TextView getDetailDate() {
        return detailDate;
    }

    public TextView getDetailCodeContent() {
        return detailCodeContent;
    }

    public EditText getEditCodeName() {
        return editCodeName;
    }

    public TextView getEditDate() {
        return editDate;
    }

    public TextView getEditCodeContent() {
        return editCodeContent;
    }
}
