package dev.jaun.qr_code_scanner.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.jaun.qr_code_scanner.QrCodeListAdapter;
import dev.jaun.qr_code_scanner.R;
import dev.jaun.qr_code_scanner.database.QRCodeData;
import dev.jaun.qr_code_scanner.database.datatypes.QRCodeModel;

//import androidx.core.app.Fragment;
//import androidx.core.app.FragmentActivity;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;

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
		recyclerView = view.findViewById(R.id.list_fragment_recycler_view);

		onCreateViewHook(requireActivity(),requireContext(),view,savedInstanceState);
	}

	public void onCreateViewHook(@NonNull FragmentActivity activity,
	                             @NonNull Context context, @NonNull View v,
	                             @Nullable Bundle savedInstanceState) {
		setLayoutManager(context);
		setListAdapter(activity, v);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	private void setLayoutManager(@NonNull Context context) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
		recyclerView.setLayoutManager(layoutManager);
	}

	@NonNull
	private ArrayList<QRCodeModel> getQrCodes(@NonNull Context context) {
		QRCodeData certificateData = new QRCodeData(context);
		return certificateData.getAllQRCodes();
	}

	private void setListAdapter(@NonNull FragmentActivity activity, @NonNull View view) {
        QrCodeListAdapter listAdapter = new QrCodeListAdapter(activity, getQrCodes(activity), getContext());
		listAdapter.registerAdapterDataObserver(new ListDataObserver(view, listAdapter));
		recyclerView.setAdapter(listAdapter);
	}
}