package android.training.demoapp.ui.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.training.demoapp.R;
import android.training.demoapp.adapters.HomeItemAdapter;
import android.training.demoapp.domain.HomeItem;
import android.training.demoapp.ui.tools.sharedPrefs.SharedPrefs;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class HomeActivity extends AppCompatActivity {
//https://www.raywenderlich.com/3595916-clean-architecture-tutorial-for-android-getting-started#toc-anchor-003


    private Context context = this;
    private Activity activity = (Activity) context;
    private HomeItemAdapter adapter;


    private String shProvincia;

    private ProgressBar pbarProgreso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Intent intent = new Intent(this, MiIntentService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }


        //scheduleJobFirebaseToRoomDataUpdate();

        List<HomeItem>  items = new ArrayList<HomeItem>();
        items.add(new HomeItem(1, context.getString(R.string.sync), context.getString(R.string.sync_desc)));
        items.add(new HomeItem(2,context.getString(R.string.listado),"Listado de estaciones de servicio. Por distancia y precio."));
        items.add(new HomeItem(3,context.getString(R.string.mapa),"Mapa con las estaciones de servicio de tu provincia"));
//        items.add(new HomeItem(4,context.getString(R.string.registro),"Calcula la media de consumo de tu vehiculo"));
//        items.add(new HomeItem(5,context.getString(R.string.extra),"Extra sin sentido"));
//        items.add(new HomeItem(6,context.getString(R.string.jobScheduler),"Prueba básica"));

        shProvincia = (String) SharedPrefs.getPref(context,SharedPrefs.PROVINCIA_ID);

//        if(shProvincia==null){
//            Intent i = new Intent(context, SyncActivity.class);
//            startActivity(i);
//            finish();
//        }
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


//Registro y  asociacion BroadcastReceiver
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(MiIntentService.ACTION_PROGRESO);
//        filter.addAction(MiIntentService.ACTION_FIN);
//        ProgressReceiver rcv = new ProgressReceiver();
//        registerReceiver(rcv, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.ajustesManager();
    }

//    private void scheduleJobFirebaseToRoomDataUpdate(){
//
//        // El primer parámetro es el JOB_ID. El segundo parámetro es ComponentNamepara el JobServiceque creó.
//        // El ComponentNamese utiliza para asociar el JobServicecon el JobInfoobjeto.
//        ComponentName serviceName = new ComponentName(getPackageName(),
//                NotificationJobService.class.getName());
//        JobInfo.Builder builder = new JobInfo.Builder(1, serviceName).setPeriodic(60000);
//
//
//        //Dentro del scheduleJob()método, use getSystemService()para inicializar mScheduler.
//        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//
//            //Schedule the job and notify the user
//            JobInfo myJobInfo = builder.build();
//            jobScheduler.schedule(myJobInfo);
//            Toast.makeText(this, "Trabajo programado, el trabajo se ejecutará cuando " +
//                    "se cumplen las restricciones.", Toast.LENGTH_SHORT).show();
//
//
//
//    }

    private void ajustesManager(){
        androidx.preference.PreferenceManager.setDefaultValues(context, R.xml.ajustes, false);
        SharedPreferences sharedPref =androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        Boolean switchPref = sharedPref.getBoolean(AjustesActivity.KEY_AJUSTES, false);
        Toast.makeText(context, switchPref.toString(),Toast.LENGTH_LONG).show();
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
            //finish();
            return true;
        case 3:
             intent = new Intent(context, MapsActivity.class);
            startActivity(intent);
            //finish();
            return true;
//        case 4:
//             intent = new Intent(context, ListadoRegistrosActivity.class);
//            startActivity(intent);
//            return true;
//        case 5:
//             intent = new Intent(context, ImplicitIntentNotifi.class);
//            startActivity(intent);
//            return true;
//        case 6:
//             intent = new Intent(context, JobSchedulerActivity.class);
//            startActivity(intent);
//            return true;

        default:
           return false;
    }
}


    //MENU CERRAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.ajustes_menu, menu);

        getMenuInflater().inflate(R.menu.cerrar_menu, menu);

//        getMenuInflater().inflate(R.menu.lang_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.atras:
                finish();
                System.exit(0);
                return true;
//            case R.id.action_language:
//                Intent languageIntent = new
//                        Intent(Settings.ACTION_LOCALE_SETTINGS);
//                startActivity(languageIntent);
//                return true;
//            case R.id.ajustes_app:
//                Intent intent = new Intent(this, AjustesActivity.class);
//                startActivity(intent);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



//CLASE INTERNA PARA CAPTURAR LOS MENSAJES BORADCAST QUE ENVIA MiIntentService
//
//    public class ProgressReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if(intent.getAction().equals(MiIntentService.ACTION_PROGRESO)) {
//                int prog = intent.getIntExtra("progreso", 0);
//                pbarProgreso.setProgress(prog);
//            }
//            else if(intent.getAction().equals(MiIntentService.ACTION_FIN)) {
//                Toast.makeText(HomeActivity.this, "Tarea finalizada!", Toast.LENGTH_LONG).show();
//            }
//        }
//    }



}
