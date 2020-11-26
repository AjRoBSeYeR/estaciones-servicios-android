package android.training.demoapp.data.remote.api;


import android.training.demoapp.domain.EstacionesServicio;
import android.training.demoapp.domain.Provincia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface APIService {

    String API_PROVINCIAS ="/ServiciosRESTCarburantes/PreciosCarburantes/Listados/Provincias/";

    @GET(API_PROVINCIAS )
    @Headers("Content-Type: application/json")
    Call< List<Provincia> > getProvincias();

    @GET("/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroProvincia/{id}")
    @Headers("Content-Type: application/json")
    Call<EstacionesServicio> getEstacionesProvincia(@Path("id") String id);

}