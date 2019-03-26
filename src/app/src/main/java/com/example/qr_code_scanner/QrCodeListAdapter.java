package com.example.qr_code_scanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.FragmentTransitionSupport;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
               // activity.getFragmentManager().beginTransaction().replace(R.id.history_fragment, new DetailFragment()).addToBackStack(null).commit();
               // historyActivity.getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment, new DetailFragment(i)).addToBackStack(null).commit();
                activity.getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left).replace(R.id.history_fragment, new DetailFragment(i)).addToBackStack(null).commit();
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
