package android.training.demoapp.Helpers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.training.demoapp.HomeActivity;
import android.training.demoapp.ImplicitIntentNotifi;
import android.training.demoapp.JobSchedulerActivity;
import android.training.demoapp.ListadoRegistrosActivity;
import android.training.demoapp.MapsActivity;
import android.training.demoapp.R;
import android.training.demoapp.SyncActivity;
import android.training.demoapp.TabsListadoActivity;
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


        switch (item.getItemId()) {

            case R.id.registroBtn:
                Intent i = new Intent(context, ListadoRegistrosActivity.class);
                startActivity(i);
               // finish();
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
                i = new Intent(BaseNavDrawerActivity.this, ImplicitIntentNotifi.class);
                startActivity(i);
                //finish();
                return true;
            case R.id.jobScheduler:
                i = new Intent(BaseNavDrawerActivity.this, JobSchedulerActivity.class);
                startActivity(i);
                //finish();
                return true;


            default:
                // Do nothing
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





//    //MENU LATERAL ----------------------------------------------
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.registroBtn:
//                Intent i = new Intent(BaseNavDrawerActivity.this, ListadoRegistrosActivity.class);
//                startActivity(i);
//                return true;
//
//            case R.id.btnSync:
//                i = new Intent(BaseNavDrawerActivity.this, SyncActivity.class);
//                startActivity(i);
//                //finish();
//                return true;
//            case R.id.btnListado:
//                i = new Intent(BaseNavDrawerActivity.this, TabsListadoActivity.class);
//                startActivity(i);
//                finish();
//                return true;
//            case R.id.btnMapa:
//                i = new Intent(BaseNavDrawerActivity.this, MapsActivity.class);
//                startActivity(i);
//                finish();
//                return true;
//
//            default:
//                // Do nothing
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
