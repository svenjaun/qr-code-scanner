package com.example.qr_code_scanner.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qr_code_scanner.QrCodeListAdapter;
import com.example.qr_code_scanner.R;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.util.ArrayList;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	@Nullable private QrCodeListAdapter listAdapter;

	@BindView(R.id.list_fragment_recycler_view) RecyclerView recyclerView;


	private OnFragmentInteractionListener mListener;

	public ListFragment() {
		// Required empty public constructor
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view,
	                          @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		onCreateViewHook(requireActivity(),requireContext(),view,savedInstanceState);
	}

	public void onCreateViewHook(@NonNull FragmentActivity activity,
	                             @NonNull Context context, @NonNull View v,
	                             @Nullable Bundle savedInstanceState) {
		setLayoutManager(context);
		setListAdapter(activity, v);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	private void setLayoutManager(@NonNull Context context) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
		recyclerView.setLayoutManager(layoutManager);
	}

	@NonNull
	private ArrayList<QRCodeModel> getQrCodes(@NonNull Context context) {
		QRCodeData qrCodeData = new QRCodeData(context);
		return qrCodeData.getAllQRCodes();
	}

	private void setListAdapter(@NonNull FragmentActivity activity, @NonNull View view) {
		listAdapter = new QrCodeListAdapter(activity, getQrCodes(activity));
		listAdapter.registerAdapterDataObserver(
				new ListDataObserver(view, listAdapter));
		recyclerView.setAdapter(listAdapter);
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


}

