package android.training.demoapp.activities.baseMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.training.demoapp.activities.HomeActivity;
import android.training.demoapp.activities.ImplicitIntentNotifi;
import android.training.demoapp.activities.JobSchedulerActivity;
import android.training.demoapp.activities.ListadoRegistrosActivity;
import android.training.demoapp.activities.MapsActivity;
import android.training.demoapp.R;
import android.training.demoapp.activities.SyncActivity;
import android.training.demoapp.activities.TabsListadoActivity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class BaseNavDrawerActivity extends AppCompatActivity implements   NavigationView.OnNavigationItemSelectedListener {
private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START )) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
//            Intent i = new Intent(BaseNavDrawerActivity.this, HomeActivity.class);
//            startActivity(i);
//            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i=null;
        switch (id ) {
            case R.id.registroBtn:
                i = new Intent(context, ListadoRegistrosActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.btnHome:
                 i = new Intent(context, HomeActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.btnSync:
                i = new Intent(context, SyncActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.btnListado:
                i = new Intent(context, TabsListadoActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.btnMapa:
                i = new Intent(context, MapsActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.extra1:
                i = new Intent(context, ImplicitIntentNotifi.class);
                startActivity(i);
                finish();
                return true;
            case R.id.jobScheduler:
                i = new Intent(context, JobSchedulerActivity.class);
                startActivity(i);
                finish();
                return true;


            default:
                // Do nothing
        }
        return true;
    }


}
