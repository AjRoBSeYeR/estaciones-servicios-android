package android.training.demoapp.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.training.demoapp.R;
import android.training.demoapp.adapters.ProvinciasAdapter;
import android.training.demoapp.ui.tools.fetchAddressTask.FetchAddressTask;
import android.training.demoapp.pojo.EstacionesServicio;
import android.training.demoapp.pojo.ListaEESSPrecio;
import android.training.demoapp.pojo.Provincia;
import android.training.demoapp.ui.receiver.InternetReceiver;
import android.training.demoapp.data.remote.api.EstacionesProvinciasService;
import android.training.demoapp.data.remote.api.ProvinciasServices;
import android.training.demoapp.ui.tools.sharedPrefs.SharedPrefs;
import android.training.demoapp.viewModel.EstacionesViewModel;
import android.training.demoapp.viewModel.ProvinciaViewModel;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class SyncActivity extends AppCompatActivity implements
        FetchAddressTask.OnTaskCompleted{
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String LOG_TAG = SyncActivity.class.getSimpleName();
    private Context context = this;
    private Activity activity = (Activity) context;
    private ProvinciaViewModel provinciaViewModel;
    private EstacionesViewModel estacionesViewModel;
    private String shProvincia;
    private boolean shIsProvincia;
    private Provincia provinciaSpinerSeleccionada;

    private Boolean spinnerTouched;
    private InternetReceiver internetReceiver;
    private LatLng latLng;
    private Spinner spinner;
    private TextView sinInternet;
    private TextView seleccionaProvincia;
    private Button btnVerListado;
    private Button btnIrMapa;
    private Button btnSyncLocation;

    //https://medium.com/@manuelmato/c%C3%B3mo-implementar-una-arquitectura-limpia-en-android-59b7510cef3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        spinnerTouched = false;
        shProvincia = (String) SharedPrefs.getPref(context,SharedPrefs.PROVINCIA_ID);
        shIsProvincia = (boolean) SharedPrefs.getPref(context,SharedPrefs.IS_PROVINCIA);
        provinciaViewModel = new ViewModelProvider(this).get(ProvinciaViewModel.class);
        spinner  = findViewById(R.id.prov_spiner);
        seleccionaProvincia  = findViewById(R.id.seleccionaProvincia);
        sinInternet  = findViewById(R.id.sinInternet);
        btnVerListado = findViewById(R.id.btnVerListado);
        btnIrMapa = findViewById(R.id.btnIrMapa);
        this.latLng = null;
        if(!shIsProvincia){
            cargarProvinciasDB();
        }
        this.conexionInternet();
        this.cargarProvicasSpiner();
        this.irListado();
        this.irMapa();
        btnSyncLocation = findViewById(R.id.btnSyncLocation);
        btnSyncLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


              getLocation();
            }
        });
    }//onCreate

    private void conexionInternet(){
        internetReceiver = new InternetReceiver(new InternetReceiver.InternetReceiverInterface() {
            @Override
            public void getResponseInternetReceiver(boolean response) {
                if(response){
                    if(shIsProvincia==false){
                        cargarProvinciasDB();
                    }
                    if(spinner.getVisibility()==View.INVISIBLE){
                        spinner.setVisibility(View.VISIBLE);
                        seleccionaProvincia.setVisibility(View.VISIBLE);
                        btnVerListado.setVisibility(View.VISIBLE);
                        btnIrMapa.setVisibility(View.VISIBLE);
                        btnSyncLocation.setVisibility(View.VISIBLE);
                        sinInternet.setVisibility(View.INVISIBLE);
                    }

                }else{
                    spinner.setVisibility(View.INVISIBLE);
                    seleccionaProvincia.setVisibility(View.INVISIBLE);
                    btnVerListado.setVisibility(View.INVISIBLE);
                    btnIrMapa.setVisibility(View.INVISIBLE);
                    btnSyncLocation.setVisibility(View.INVISIBLE);
                    sinInternet.setVisibility(View.VISIBLE);
                }
            }
        });

        IntentFilter  filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(internetReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(internetReceiver);
        super.onDestroy();
    }

    private void cargarProvinciasDB(){
            new ProvinciasServices(new ProvinciasServices.ProvinciasServicesInterface() {
                @Override
                public void getResponse(List<Provincia> response) {
                    Log.d(LOG_TAG, "code" + response);
                    List<Provincia> provs;
                    provs = response;
                    Provincia provinciaInsert;
                    for (int i = 0; i < provs.size(); i++) {
                        Log.d(LOG_TAG, "provc" + provs.get(i));
                        provinciaInsert = provs.get(i);
                        provinciaViewModel.insert(provinciaInsert);
                    }
                    SharedPrefs.setPref(context, SharedPrefs.IS_PROVINCIA,true);
                }
                @Override
                public void showErrorMessage(String message) {
                    Log.d(LOG_TAG, "code" + message);
                }
            });
    }

    private void cargarProvicasSpiner(){
        provinciaViewModel.getAllProvincias().observe(this, new Observer<List<Provincia>>() {
            @Override
            public void onChanged(@Nullable final List<Provincia> provincias) {
                Log.d(LOG_TAG, "provc" + provincias);
                ProvinciasAdapter adapter = new ProvinciasAdapter(context, provincias);
                spinner.setAdapter(adapter);
                spinner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            spinnerTouched = true; // User DID touched the spinner!
                        }
                        return false;
                    }
                });
                if(shProvincia!=null) {
                    spinner.setSelection((Integer.parseInt(shProvincia))-1);
                }
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        if (spinnerTouched) {
                            Object provinciaSeleccionada = parent.getItemAtPosition(pos);
                            provinciaSpinerSeleccionada = (Provincia) provinciaSeleccionada;
                            SharedPrefs.setPref(context, SharedPrefs.PROVINCIA_ID,provinciaSpinerSeleccionada.getIDPovincia());
                            volcarEstacionesProvincaDB();
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });// spinner.setOnItemSelectedListener(
            }
        });
    }


    private void obtenerPosicionDispositivo() {



        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.e("location", location.getLatitude() + "");
                if (location != null) {
                    // Start the reverse geocode AsyncTask
                    latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    FetchAddressTask task = new FetchAddressTask(context, SyncActivity.this);

                    task.execute(location);
                   // new FetchAddressTask(context, SyncActivity.this).execute(location);
                } else {
                    Toast toast = Toast.makeText(context, "fallo ubicacion", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
//                Toast toast = Toast.makeText(context, "onStatusChanged", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 0, 250);
//                toast.show();
            }

            public void onProviderEnabled(String provider) {
                Toast toast = Toast.makeText(context, "onProviderEnabled", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            }

            public void onProviderDisabled(String provider) {
                Toast toast = Toast.makeText(context, "onProviderDisabled", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
                showLocationSettingsDialog();
            }

        };

        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);

        }else {
           // locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);

        }
    }




    void showLocationSettingsDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(activity)
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult( activity,1);
                            } catch (IntentSender.SendIntentException exception) {
                                // Ignore the error.
                            } catch (ClassCastException exception) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }


    private void volcarEstacionesProvincaDB(){
        shProvincia = (String) SharedPrefs.getPref(context,SharedPrefs.PROVINCIA_ID);
        estacionesViewModel = new ViewModelProvider(this).get(EstacionesViewModel.class);
        new EstacionesProvinciasService(new EstacionesProvinciasService.EstacionesProvinciasServicesInterface() {
            @Override
            public void getResponse(EstacionesServicio response) {
                Log.d(LOG_TAG, "EstacionesServicio"+response);
                EstacionesServicio estacionesServicio;
                estacionesServicio =  response;
                SharedPrefs.setPref(context, SharedPrefs.FECHHA,estacionesServicio.getFecha());
                List<ListaEESSPrecio> listadoEstaciones =  estacionesServicio.getListaEESSPrecio();
                ListaEESSPrecio estacionInsert;
                estacionesViewModel.deleteAll();
                for(int i=0; i<listadoEstaciones.size(); i++){
                    Log.d(LOG_TAG, "listadoEstaciones"+ listadoEstaciones.get(i));
                    estacionInsert = listadoEstaciones.get(i);
                    String latString = estacionInsert.getLatitud().replace(',', '.');
                    String lngString = estacionInsert.getLongitudWGS84().replace(',', '.');
                if(latLng!=null){
                    estacionInsert.setDistancia(
                            calcularDistancia(new LatLng(Double.parseDouble(latString),Double.parseDouble(lngString)))
                    );
                }
                    estacionesViewModel.insert(estacionInsert);
                }
                irTabsListado();
            }
            @Override
            public void showErrorMessage(String message) {
                Log.d(LOG_TAG, "EstacionesServicio showErrorMessage "+message);
            }
        }, shProvincia);
    }

private float calcularDistancia(LatLng latLngEstacionServicio) {
    float resultadoDistancia;
    LatLng latLngnDispositivo = latLng;
    float distaciaMetros = (float) SphericalUtil.computeDistanceBetween(latLngEstacionServicio, latLngnDispositivo);
    resultadoDistancia = distaciaMetros;
    if (distaciaMetros > 1000) {
        resultadoDistancia = Math.round((distaciaMetros) / 1000);
    }
    return resultadoDistancia;
}

    public void irListado(){

        btnVerListado.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(shProvincia!=null){
                    Intent i = new Intent(SyncActivity.this, TabsListadoActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast toast = Toast.makeText(context, R.string.sync, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                }
            }
        });
    }

    private  void irMapa(){
        btnIrMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(shProvincia!=null){
                    Intent i = new Intent(SyncActivity.this, MapsActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast toast = Toast.makeText(context, R.string.sync, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                }
            }
        });
    }



    private void getLocation() {
        if(isLocationEnabled(context)){
            Log.d(LOG_TAG, "getLocation: permissions GRANTED");
            obtenerPosicionDispositivo();
        }else{
            showLocationSettingsDialog();
        }
    }



    private boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }


    private void irTabsListado(){
        Intent i = new Intent(context, TabsListadoActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,R.string.location_permission_denied,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onTaskCompleted(String result) {
        SharedPrefs.setPref(context, SharedPrefs.PROVINCIA_ID,result);
        spinner.setSelection((Integer.parseInt(result))-1);
        volcarEstacionesProvincaDB();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
      //  final LocationSettingsStates states = LocationSettingsStates.fromIntent(intent);such
        switch (requestCode) {
            case 1:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        obtenerPosicionDispositivo();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast toast = Toast.makeText(context, R.string.ubicacion_denegada, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                        break;
                    default:
                        break;
                }
                break;
        }
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
