package android.training.demoapp.domain.mapas;

import android.training.demoapp.domain.ListaEESSPrecio;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MapCluster implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;
    private ListaEESSPrecio estacioneServicio;


    public MapCluster(double lat, double lng, String title, String snippet, ListaEESSPrecio estacioneServicio) {
        this.mPosition = new LatLng(lat, lng);
        this.mTitle = title;
        this.mSnippet = snippet;
        this.estacioneServicio = estacioneServicio;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public ListaEESSPrecio getEstacioneServicio() {
        return estacioneServicio;
    }


}