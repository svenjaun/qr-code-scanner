package dev.jaun.qr_code_scanner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    @NonNull
    private Context context;

    private ImageView listElementQrCode;
    private TextView listElementNameTextView;
    private TextView listEllementDateTextView;

    public ViewHolder(@NonNull Context context, @NonNull View view) {
        super(view);
        listElementQrCode = view.findViewById(R.id.list_element_qr_code);
        listElementNameTextView = view.findViewById(R.id.list_element_qr_code_name);
        listEllementDateTextView = view.findViewById(R.id.list_element_qr_code_date);

        this.context = context;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    public ImageView getListElementQrCode() {
        return listElementQrCode;
    }

    public TextView getListElementNameTextView() {
        return listElementNameTextView;
    }

    public TextView getListEllementDateTextView() {
        return listEllementDateTextView;
    }


}
