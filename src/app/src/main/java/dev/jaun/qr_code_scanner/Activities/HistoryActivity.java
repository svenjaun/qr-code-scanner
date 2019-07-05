package dev.jaun.qr_code_scanner.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import dev.jaun.qr_code_scanner.Fragments.DetailFragment;
import dev.jaun.qr_code_scanner.Fragments.ListFragment;
import dev.jaun.qr_code_scanner.R;

public class HistoryActivity extends AppCompatActivity {

    ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        listFragment = new ListFragment();
        Intent intent = this.getIntent();
        if (intent != null) {
            try {
                int id = intent.getExtras().getInt("QRCodeID");
                getFragmentManager().popBackStack();
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
