package android.training.demoapp.ui.activities.baseMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.training.demoapp.ui.activities.HomeActivity;
import android.training.demoapp.ui.activities.MapsActivity;
import android.training.demoapp.R;
import android.training.demoapp.ui.activities.SyncActivity;
import android.training.demoapp.ui.activities.TabsListadoActivity;
import android.view.Menu;
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
            super.onBackPressed();
//            Intent i = new Intent(BaseNavDrawerActivity.this, HomeActivity.class);
//            startActivity(i);
//            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    // Actualmente no se esta usando
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i=null;
        switch (id ) {
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


            default:
                // Do nothing
        }
        return true;
    }


    //MENU CERRAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cerrar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.atras:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
