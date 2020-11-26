package android.training.demoapp.data.remote.api;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String GEOPORTAL_ENDOPOINT = "https://sedeaplicaciones.minetur.gob.es/";
    private static OkHttpClient client;
    private static Retrofit retrofit;

    public static APIService getRestClient(/*Context context*/) {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                        .disableHtmlEscaping().create()))
                .client(client)
                .baseUrl(GEOPORTAL_ENDOPOINT)
                .build();


        return retrofit.create(APIService.class);
    }

}