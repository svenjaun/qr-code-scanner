package com.example.qr_code_scanner.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qr_code_scanner.Activities.MainActivity;
import com.example.qr_code_scanner.Activities.HistoryActivity;
import com.example.qr_code_scanner.QrCodeListAdapter;
import com.example.qr_code_scanner.R;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Nullable
    private QrCodeListAdapter listAdapter;
     RecyclerView recyclerView;
    private MainActivity mainActivity;
    private EditText editCodeName;
    private TextView editDate;
    private TextView editCodeContent;
    private Button editSave;


	// TODO: Rename and change types of parameters
	private String mParam1;
	private String value;


	public EditFragment() {

	}

    @SuppressLint("ValidFragment")
    public EditFragment(String value, MainActivity mainActivity) {
        this.value = value;
        this.mainActivity = mainActivity;
    }

	QRCodeData qrcodeData;
	long qrCodeID;
	@SuppressLint("RestrictedApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		recyclerView = getActivity().findViewById(R.id.list_fragment_recycler_view);
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

	@SuppressLint("SetTextI18n")
	@Override
	public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
		editCodeName = view.findViewById(R.id.edit_fragment_code_name);
		editDate = view.findViewById(R.id.edit_fragment_date);
		editCodeContent = view.findViewById(R.id.edit_fragment_code_content);
		editSave = view.findViewById(R.id.edit_fragment_save);
		editCodeName.setText("QR-Code" + qrcodeData.getLatestID());
		editCodeContent.setText(value);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format = new SimpleDateFormat("EEEE dd.MMMM yyyy", Locale.GERMAN);
		editDate.setText(format.format(cal.getTime()));
		editSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qrCodeID = createQRCode(editCodeName.getText().toString());
				Intent intent = new Intent(getActivity(), HistoryActivity.class);
				int i = (int)qrCodeID;
				if ((long)i != qrCodeID) {
					throw new IllegalArgumentException(qrCodeID + " cannot be cast to int without changing its value.");
				}
				intent.putExtra("QRCodeID", i);
				startActivity(intent);
			}
		});
		super.onViewCreated(view, savedInstanceState);
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

	private long createQRCode(String name) {
		return qrcodeData.createQRCode(
				new QRCodeModel(qrcodeData.getLatestID(), name, value, new Date().getTime()));
	}
}

