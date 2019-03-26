package com.example.qr_code_scanner.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qr_code_scanner.R;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;
import com.google.zxing.WriterException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class DetailFragment extends Fragment {

    ImageView detailQRCode;
    QRCodeModel qrCode;
    QRCodeData qrcodeData;
    int qrCodeId;
    public static final String URL_REGEX = "^((https?|http)://|(www)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    public DetailFragment() {
        // Required empty public constructor
        qrCodeId = 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrcodeData = new QRCodeData(getContext());
        qrCode = qrcodeData.getQRCode(qrCodeId);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        detailQRCode = view.findViewById(R.id.detail_fragment_qr_code);
        detailQRCode.setImageBitmap(qrCode.getQRCode());
        view.findViewById(R.id.detail_fragment_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qrCode.getValue())));
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
