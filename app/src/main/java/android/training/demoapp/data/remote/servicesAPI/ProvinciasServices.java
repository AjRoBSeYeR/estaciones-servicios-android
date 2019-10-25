package android.training.demoapp.data.remote.servicesAPI;

import android.training.demoapp.pojo.Provincia;
import android.training.demoapp.viewModel.ProvinciaViewModel;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinciasServices {
    private static final String LOG_TAG = ProvinciasServices.class.getSimpleName();
    private final ProvinciasServicesInterface listener;
    private APIService apiService;

    private ProvinciaViewModel provinciaViewModel;

    public ProvinciasServices(ProvinciasServicesInterface listener){
        this.listener = listener;
        apiService = RestClient.getRestClient();
        provinciasServices();
    }



    public void provinciasServices() {
        Call<List<Provincia>> call = null;

        call = apiService.getProvincias();

        call.enqueue(new Callback<List<Provincia>>() {

            @Override
            public void onResponse(Call<List<Provincia>> call, Response<List<Provincia>> response) {
                Log.d(LOG_TAG, "response"+response.body());
                if (response.body() != null) {
                    List<Provincia> jsonObject = response.body();
                    listener.getResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Provincia>> call, Throwable t) {
                Log.d(LOG_TAG, "code"+t);
                listener.showErrorMessage("error obteniendo datos del ProvinciasServices.java");
            }
        });
    }

    public interface ProvinciasServicesInterface {
        void getResponse(List<Provincia> response);

        void showErrorMessage(String message);
    }

}
