package com.example.qr_code_scanner.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qr_code_scanner.Activities.HistoryActivity;
import com.example.qr_code_scanner.QrCodeListAdapter;
import com.example.qr_code_scanner.R;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	@Nullable
	private QrCodeListAdapter listAdapter;
	RecyclerView recyclerView;

	private EditText editCodeName;
	private TextView editDate;
	private TextView editCodeContent;
	private Button editSave;


	// TODO: Rename and change types of parameters
	private String mParam1;
	private String value;

	private OnFragmentInteractionListener mListener;

	public EditFragment() {

	}

	@SuppressLint("ValidFragment")
	public EditFragment(String value) {
		this.value = value;
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
		Date date = Calendar.getInstance().getTime();
		editCodeName.setText("QR-Code" + qrcodeData.getLatestID());
		editCodeContent.setText(value);
		editDate.setText(date.toString());
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
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
	}

	private long createQRCode(String name) {
		return qrcodeData.createQRCode(
				new QRCodeModel(qrcodeData.getLatestID(), name, value, new Date().getTime()));
	}

}

