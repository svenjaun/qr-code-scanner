package com.example.qr_code_scanner.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qr_code_scanner.R;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


@SuppressLint("ValidFragment")
public class DetailFragment extends Fragment {
	int qrCodeId;
	QRCodeModel qrCode;
	TextView detailCodeName;
	TextView detailCodeDate;
	TextView detailCodeValue;
	Button detailDelete;
	QRCodeData qrcodeData;
    ImageView detailQRCode;

    public static final String URL_REGEX = "^((https?|http)://|(www)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    @SuppressLint("ValidFragment")
    public DetailFragment(int qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrcodeData = new QRCodeData(getContext());
        qrCode = qrcodeData.getQRCodeById(qrCodeId);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        detailQRCode = view.findViewById(R.id.detail_fragment_qr_code);
        detailQRCode.setImageBitmap(qrCode.getQRCode());
        view.findViewById(R.id.detail_fragment_code_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qrCode.getValue())));
            }
        });
        detailCodeName = view.findViewById(R.id.detail_fragment_code_name);
        detailCodeDate= view.findViewById(R.id.detail_fragment_date);
        detailCodeValue =view.findViewById(R.id.detail_fragment_code_content);
        detailDelete=view.findViewById(R.id.detail_fragment_delete);
        detailCodeName.setText(qrCode.getName());
        Calendar cal = qrCode.getDate();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format = new SimpleDateFormat("EEEE dd.MMMM yyyy", Locale.GERMAN);
        detailCodeDate.setText(format.format(cal.getTime()));
        detailCodeValue.setText(qrCode.getValue());
        /*detailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrcodeData.removeQRCode(qrCodeId);
            }
        });*/
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
		qrcodeData = new QRCodeData(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
