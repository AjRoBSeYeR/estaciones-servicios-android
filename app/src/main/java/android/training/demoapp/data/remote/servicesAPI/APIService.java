package android.training.demoapp.data.remote.servicesAPI;


import android.training.demoapp.pojo.EstacionesServicio;
import android.training.demoapp.pojo.Provincia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface APIService {

    String API_PROVINCIAS ="/ServiciosRESTCarburantes/PreciosCarburantes/Listados/Provincias/";
    //String API_ESTACIONES_PROVINCIA ="/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroProvincia/"+id;

    @GET(API_PROVINCIAS )
    @Headers("Content-Type: application/json")
    Call< List<Provincia> > getProvincias();

    @GET("/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroProvincia/{id}")
    @Headers("Content-Type: application/json")
    Call<EstacionesServicio> getEstacionesProvincia(@Path("id") String id);


//    @GET("/alzfae/api/paciente/GetPaciente")
//    @Headers("Content-Type: application/json")
//    Call<User> getPacienteByIDRequest(@Query("idPaciente") String idPaciente);
}