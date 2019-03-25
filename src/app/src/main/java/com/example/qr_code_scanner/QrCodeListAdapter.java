package com.example.qr_code_scanner;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qr_code_scanner.Activities.HistoryActivity;
import com.example.qr_code_scanner.Fragments.DetailFragment;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
        qrcodeDataTV = activity.findViewById(R.id.list_element_qr_code_date);
        ViewHolder viewHolder = new ViewHolder(activity, v);


        v.findViewById(R.id.list_element).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getFragmentManager().beginTransaction().replace(R.id.history_fragment, new DetailFragment()).addToBackStack(null).commit();
            }
        });

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
        Calendar cal = qrCode.getDate();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.getListEllementDateTextView().setText(cal.getTime().toString());
    }

    @NonNull
    public ArrayList<QRCodeModel> getQrCodes() {
        return qrCodes;
    }
}
