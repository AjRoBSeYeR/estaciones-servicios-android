package android.training.demoapp.Helpers;

import android.content.Intent;
import android.os.Bundle;
import android.training.demoapp.ListadoRegistrosActivity;
import android.training.demoapp.MapsActivity;
import android.training.demoapp.R;
import android.training.demoapp.SyncActivity;
import android.training.demoapp.TabsListadoActivity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    //MENU LATERAL ----------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.registroBtn:
                Intent i = new Intent(BaseMenuActivity.this, ListadoRegistrosActivity.class);
                startActivity(i);
                return true;

            case R.id.btnSync:
                i = new Intent(BaseMenuActivity.this, SyncActivity.class);
                startActivity(i);
                //finish();
                return true;
            case R.id.btnListado:
                i = new Intent(BaseMenuActivity.this, TabsListadoActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.btnMapa:
                i = new Intent(BaseMenuActivity.this, MapsActivity.class);
                startActivity(i);
                finish();
                return true;

            default:
                // Do nothing
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
       // super.onBackPressed();

    }
}
