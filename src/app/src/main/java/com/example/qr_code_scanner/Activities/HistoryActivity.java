package com.example.qr_code_scanner.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qr_code_scanner.Fragments.DetailFragment;
import com.example.qr_code_scanner.Fragments.ListFragment;
import com.example.qr_code_scanner.R;

import java.util.concurrent.ExecutionException;


public class HistoryActivity extends AppCompatActivity {

    ListFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listFragment = new ListFragment();

        Intent intent = this.getIntent();
        if (intent != null) {
            try {
                int id = intent.getExtras().getInt("QRCodeID");
                openDetailFragment(id);
            } catch (Exception e) {
                getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment,
                        listFragment).commit();
            }
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment,
                    listFragment).commit();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void openDetailFragment(int id) {
        DetailFragment detailFragment = new DetailFragment(id);
        getFragmentManager().beginTransaction().replace(R.id.history_fragment,
                detailFragment).commit();
    }
}
