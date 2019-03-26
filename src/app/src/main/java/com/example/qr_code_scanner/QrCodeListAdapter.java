package com.example.qr_code_scanner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qr_code_scanner.Activities.HistoryActivity;
import com.example.qr_code_scanner.Fragments.DetailFragment;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrCodeListAdapter extends RecyclerView.Adapter<ViewHolder> {
    @NonNull
    private Activity activity;
    private HistoryActivity historyActivity;
    @NonNull
    private ArrayList<QRCodeModel> qrCodes;



    public QrCodeListAdapter(@NonNull Activity activity, @NonNull ArrayList<QRCodeModel> qrCodes, HistoryActivity historyActivity) {
        this.activity = activity;
        this.historyActivity = historyActivity;
        this.qrCodes = qrCodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.list_element, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(activity, v);


        v.findViewById(R.id.list_element).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            historyActivity.getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment, new DetailFragment(i)).addToBackStack(null).commit();
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
        viewHolder.getListElementQrCode().setImageBitmap(setQRCode(qrCode.getValue()));
    }

    private Bitmap setQRCode(String value) {
        Bitmap qrcode;
        QRGEncoder qrgEncoder = new QRGEncoder(value, null, QRGContents.Type.TEXT, 100);
        try {
            // Getting QR-Code as Bitmap
            qrcode = qrgEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            throw new Error("Error by generation QRCode: " + e);
        }
        return qrcode;
    }

    @NonNull
    public ArrayList<QRCodeModel> getQrCodes() {
        return qrCodes;
    }
}
