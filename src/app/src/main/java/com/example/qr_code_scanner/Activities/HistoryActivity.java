package com.example.qr_code_scanner.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.qr_code_scanner.Fragments.ListFragment;
import com.example.qr_code_scanner.R;

public class HistoryActivity extends AppCompatActivity {

    ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listFragment = new ListFragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment,
                listFragment).commit();
    }
}
