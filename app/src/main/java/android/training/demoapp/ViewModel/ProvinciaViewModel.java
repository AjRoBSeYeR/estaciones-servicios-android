package android.training.demoapp.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.training.demoapp.Pojo.Provincia;
import android.training.demoapp.Repository.ProvinciaRepository;

import java.util.List;

public class ProvinciaViewModel extends AndroidViewModel {

    private ProvinciaRepository pr;
    private LiveData<List<Provincia>> allProvincias;

    public ProvinciaViewModel (Application application) {
        super(application);
        pr = new ProvinciaRepository(application);
        allProvincias = pr.getAllProvincias();
    }



//    public LiveData<List<Provincia>> getAllRegistros() { return allProvincias; }

    public void insert(Provincia provincia) {
        pr.insert(provincia);
    }

    public LiveData<List<Provincia>> getAllProvincias() { return allProvincias; }


}
