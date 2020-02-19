package android.training.demoapp.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.training.demoapp.pojo.Provincia;
import android.training.demoapp.repository.ProvinciaRepository;

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
