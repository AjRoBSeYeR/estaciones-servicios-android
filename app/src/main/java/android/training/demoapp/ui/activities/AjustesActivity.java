package android.training.demoapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AjustesActivity extends AppCompatActivity {

    public static final String KEY_AJUSTES = "switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AjustesFragment())
                .commit();
    }

}
