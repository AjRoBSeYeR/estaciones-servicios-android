package android.training.demoapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.training.demoapp.Adapters.MapsAdapters.MapsInfoWindowAdapter;
import android.training.demoapp.Helpers.BaseMenuActivity;
import android.training.demoapp.Helpers.BaseNavDrawerActivity;
import android.training.demoapp.Pojo.ListaEESSPrecio;
import android.training.demoapp.Pojo.Mapas.MapCluster;
import android.training.demoapp.Pojo.Mapas.MarkerClusterRenderer;
import android.training.demoapp.ViewModel.EstacionesViewModel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MapsActivity extends BaseNavDrawerActivity implements OnMapReadyCallback {


    public static final float INITIAL_ZOOM = 8f;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String LOG_TAG = MapsActivity.class.getSimpleName();
    private Context context = this;
    private Activity activity = (Activity) context;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng posicionAbrirMapa ;
    private EstacionesViewModel estacionesViewModel;
    private ClusterManager<MapCluster> mClusterManager;
    private  IconGenerator iconGenerator;
    private  ImageView markerImageView;
    private Boolean isStyleMap;
    private int combustible;
    private int limiteCombustible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.getLocation();
        this.isStyleMap = false;
        this.combustible = 1;
        this.limiteCombustible = 10;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    private void getLocation() {
        if(isLocationEnabled(context)){
            if (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);
            } else {
                Log.d(LOG_TAG, "getLocation: permissions GRANTED");
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
                mFusedLocationClient.getLastLocation().addOnSuccessListener(
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Start the reverse geocode AsyncTask
                                posicionAbrirMapa  = new LatLng(location.getLatitude(), location.getLongitude());
                                createMap();
                            }
                        });
            }
        }else{
            solicitarUbicacion();
        }
    }

    private void createMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) context);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

  enableMyLocation(mMap);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle( context, R.raw.map_style));
      //  mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        setUpClusterManager(mMap);
       // capturarEventos();

//        final MarkerClusterRenderer renderer = new MarkerClusterRenderer(context, mMap, mClusterManager);
//        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new MapsInfoWindowAdapter(LayoutInflater.from(context)));
//        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionAbrirMapa, INITIAL_ZOOM));
//        mClusterManager.setRenderer(renderer);


    }//FIN onMapReady



    private void capturarEventos(){

        mClusterManager.setOnClusterClickListener(
                new ClusterManager.OnClusterClickListener<MapCluster>() {
                    @Override public boolean onClusterClick(Cluster<MapCluster> cluster) {

                        Toast.makeText(MapsActivity.this, "Cluster click", Toast.LENGTH_SHORT).show();

                        // if true, do not move camera

                        return false;
                    }
                });

        mClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<MapCluster>() {
                    @Override public boolean onClusterItemClick(MapCluster clusterItem) {

                        Toast.makeText(MapsActivity.this, "Cluster item click", Toast.LENGTH_SHORT).show();

                        // if true, click handling stops here and do not show info view, do not move camera
                        // you can avoid this by calling:
                        // renderer.getMarker(clusterItem).showInfoWindow();

                        return false;
                    }
                });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(MapsActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void solicitarUbicacion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.network_not_enabled)
                .setMessage(R.string.open_location_settings)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                getLocation();
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(context, R.string.location_permission_denied,Toast.LENGTH_LONG).show();
                                Intent i = new Intent(MapsActivity.this, TabsListadoActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this, R.string.location_permission_denied,Toast.LENGTH_LONG).show();
                   // mLocationTextView.setText(R.string.location_permission_denied);
                    Intent i = new Intent(MapsActivity.this, TabsListadoActivity.class);
                    startActivity(i);
                    finish();
                }
                break;
        }
    }




    private void setUpClusterManager(GoogleMap googleMap){
        // Position the map.
       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MapCluster>(context, googleMap);

        MarkerClusterRenderer renderer = new MarkerClusterRenderer(context, mMap, mClusterManager);
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new MapsInfoWindowAdapter(LayoutInflater.from(context)));
        mClusterManager.setRenderer(renderer);


        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionAbrirMapa, INITIAL_ZOOM));


        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }
    private void addItems() {
        //https://codinginfinite.com/android-google-map-custom-marker-clustering/
        //https://ahsensaeed.com/android-custom-info-window-view-on-marker-click-map-utils/

// https://developers.google.com/maps/documentation/android-sdk/utility/marker-clustering
// https://medium.com/@tonyshkurenko/work-with-clustermanager-bdf3d70fb0fd
// https://github.com/tonyshkurenko/ClusterManagerDemo
        estacionesViewModel =  new ViewModelProvider(this).get(EstacionesViewModel.class);
        estacionesViewModel.getAll().observe(this, new Observer<List<ListaEESSPrecio>>() {
            @Override
            public void onChanged(@Nullable final List<ListaEESSPrecio> est) {
                List<ListaEESSPrecio> estaciones =  est;
                Log.d(LOG_TAG, "provc"+ est);
                for (int i = 0; i < estaciones.size(); i++) {
                    String latString = estaciones.get(i).getLatitud().replace(',', '.');
                    String lngString = estaciones.get(i).getLongitudWGS84().replace(',', '.');
                    double latitud = Double.parseDouble(latString);
                    double longitud = Double.parseDouble(lngString);
                    MapCluster mapCluster = new MapCluster(latitud, longitud, estaciones.get(i).getRotulo(), estaciones.get(i).getMunicipio(), estaciones.get(i));
                    mClusterManager.addItem(mapCluster);
                }
                mClusterManager.cluster();

            }
        });
    }





    private void masBarata(){
        estacionesViewModel =  new ViewModelProvider(this).get(EstacionesViewModel.class);
        estacionesViewModel.getPorPrecio2(combustible,limiteCombustible).observe(this, new Observer<List<ListaEESSPrecio>>() {
            @Override
            public void onChanged(@Nullable final List<ListaEESSPrecio> est) {
                List<ListaEESSPrecio> estaciones =  est;
                Log.d(LOG_TAG, "provc"+ est);
                for (int i = 0; i < estaciones.size(); i++) {
                    String latString = estaciones.get(i).getLatitud().replace(',', '.');
                    String lngString = estaciones.get(i).getLongitudWGS84().replace(',', '.');
                    double latitud = Double.parseDouble(latString);
                    double longitud = Double.parseDouble(lngString);
                    MapCluster mapCluster = new MapCluster(latitud, longitud, estaciones.get(i).getRotulo(), estaciones.get(i).getMunicipio(), estaciones.get(i));
                    mClusterManager.addItem(mapCluster);
                }
                mClusterManager.cluster();
            }
        });
    }

private void masBarata3(){
    estacionesViewModel =  new ViewModelProvider(this).get(EstacionesViewModel.class);
    estacionesViewModel.getPorPrecio2(combustible,limiteCombustible).observe(this, new Observer<List<ListaEESSPrecio>>() {
        @Override
        public void onChanged(@Nullable final List<ListaEESSPrecio> est) {
            List<ListaEESSPrecio> estaciones =  est;
            Log.d(LOG_TAG, "provc"+ est);
            for (int i = 0; i < estaciones.size(); i++) {
                //double latitud = Double.parseDouble(estaciones.get(i).getLatitud().valueOf());

                String latString = estaciones.get(i).getLatitud().replace(',', '.');
                String lngString = estaciones.get(i).getLongitudWGS84().replace(',', '.');
                double latitud = Double.parseDouble(latString);
                double longitud = Double.parseDouble(lngString);

                LatLng coord=new LatLng(latitud,longitud);
                String snippet = String.format(Locale.getDefault(),
                        estaciones.get(i).getMunicipio(),
                        latitud,
                        longitud);


                mMap.addMarker(new MarkerOptions()
                        .position(coord)
                        .title(estaciones.get(i).getDirecciN())
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_YELLOW)));
            }
        }
    });
}





    private void enableMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

//MENU solo para maps
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.maps_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.gasolinaBarata:
                mMap.clear();
                mClusterManager.clearItems();
                masBarata();
                return true;

            case R.id.refreshMapa:
                mMap.clear();
                mClusterManager.clearItems();
                onMapReady(mMap);
                return true;

            case R.id.ajustesMapa:

                return true;

            case R.id.mapStyle:
                if( mMap.getMapType()==GoogleMap.MAP_TYPE_HYBRID){
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }else{
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


