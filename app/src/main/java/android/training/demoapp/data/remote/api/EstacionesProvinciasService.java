package android.training.demoapp.data.remote.api;

import android.training.demoapp.domain.EstacionesServicio;
import android.training.demoapp.viewModel.EstacionesViewModel;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstacionesProvinciasService {

    private static final String LOG_TAG = EstacionesProvinciasService.class.getSimpleName();
    private final EstacionesProvinciasServicesInterface listener;
    private APIService apiService;

    private EstacionesViewModel estacionesViewModel;

    public EstacionesProvinciasService(EstacionesProvinciasServicesInterface listener,String id) {
        this.listener = listener;
        apiService = RestClient.getRestClient();
        estacionesProvinciasService(id);
    }

    public void estacionesProvinciasService(String id) {
        Call <EstacionesServicio> call = null;

        call = apiService.getEstacionesProvincia(id);

        call.enqueue(new Callback<EstacionesServicio>() {


            @Override
            public void onResponse(Call <EstacionesServicio> call, Response<EstacionesServicio> response) {
                Log.d(LOG_TAG, "response body"+response.body());

                if (response.body() != null) {
                    EstacionesServicio jsonObject = response.body();
                    listener.getResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call <EstacionesServicio> call, Throwable t) {
                Log.d(LOG_TAG, "errror"+t);
                listener.showErrorMessage("error obteniendo datos del estacionesProvinciasService.java");

            }
        });
    }



    public interface EstacionesProvinciasServicesInterface {

        void getResponse(EstacionesServicio response);

        void showErrorMessage(String message);
    }
}
