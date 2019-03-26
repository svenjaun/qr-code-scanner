package com.example.qr_code_scanner;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qr_code_scanner.Fragments.DetailFragment;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class QrCodeListAdapter extends RecyclerView.Adapter<ViewHolder> {
    @NonNull
    private Activity activity;
    @NonNull
    private ArrayList<QRCodeModel> qrCodes;
    private QRCodeData qrCodeData;
    private QRCodeModel qrCode;

    public QrCodeListAdapter(@NonNull Activity activity, @NonNull ArrayList<QRCodeModel> qrCodes, Context context) {
        this.activity = activity;
        this.qrCodes = qrCodes;
       qrCodeData = new QRCodeData(context.getApplicationContext());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.list_element, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(activity, v);
        v.findViewById(R.id.list_element).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView qrCodeName = view.findViewById(R.id.list_element_qr_code_name);
                qrCode = qrCodeData.getQRCodeByName(String.valueOf(qrCodeName.getText()));
                activity.getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
                        .replace(R.id.history_fragment, new DetailFragment(qrCode.getID())).addToBackStack(null).commit();
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
        SimpleDateFormat format = new SimpleDateFormat("EEEE dd.MMMM yyyy", Locale.GERMAN);
        viewHolder.getListEllementDateTextView().setText(format.format(cal.getTime()));
        viewHolder.getListElementQrCode().setImageBitmap(qrCode.getQRCode());
    }

    @NonNull
    public ArrayList<QRCodeModel> getQrCodes() {
        return qrCodes;
    }
}
