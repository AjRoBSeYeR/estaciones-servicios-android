package android.training.demoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.training.demoapp.Adapters.HomeItemAdapter;
import android.training.demoapp.Pojo.HomeItem;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = ListadoRegistrosActivity.class.getSimpleName();

    private Context context = this;
    private Activity activity = (Activity) context;

    private HomeItemAdapter adapter;


    @Override
    public void onBackPressed() {}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<HomeItem>  items = new ArrayList<HomeItem>();

        items.add(new HomeItem(1, context.getString(R.string.sync), context.getString(R.string.sync_desc)));

        items.add(new HomeItem(2,context.getString(R.string.listado),"descripcion"));

        items.add(new HomeItem(3,context.getString(R.string.mapa),"descripcion"));

        items.add(new HomeItem(4,context.getString(R.string.registro),"descripcionn"));

        items.add(new HomeItem(5,context.getString(R.string.extra),"descripcion"));

        items.add(new HomeItem(6,context.getString(R.string.jobScheduler),"descripcion"));

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new HomeItemAdapter(context, items, new HomeItemAdapter.onItemClick(){

            @Override
            public void onItemClick(HomeItem item) {
                Toast toast = Toast.makeText(context, item.getTitulo(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
                abrir(item);

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.setHomeItem(items);

    }




private Boolean abrir(HomeItem item){
    switch (item.getId()) {

        case 1:
            Intent intent = new Intent(context, SyncActivity.class);
            startActivity(intent);
            return true;
        case 2:
             intent = new Intent(context, TabsListadoActivity.class);
            startActivity(intent);
            return true;
        case 3:
             intent = new Intent(context, MapsActivity.class);
            startActivity(intent);
            return true;
        case 4:
             intent = new Intent(context, ListadoRegistrosActivity.class);
            startActivity(intent);
            return true;
        case 5:
             intent = new Intent(context, ImplicitIntentNotifi.class);
            startActivity(intent);
            return true;
        case 6:
             intent = new Intent(context, JobSchedulerActivity.class);
            startActivity(intent);
            return true;

        default:
           return false;
    }
}



    //MENU CERRAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.ajustes_menu, menu);

        getMenuInflater().inflate(R.menu.cerrar_menu, menu);

        getMenuInflater().inflate(R.menu.lang_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.atras:
                finish();
                return true;
            case R.id.action_language:
                Intent languageIntent = new
                        Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(languageIntent);
                return true;
            case R.id.ajustes_app:
                Intent intent = new Intent(this, AjustesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }









}
