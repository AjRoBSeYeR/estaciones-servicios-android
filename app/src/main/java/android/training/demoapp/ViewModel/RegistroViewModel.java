package android.training.demoapp.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.training.demoapp.Pojo.Registro;
import android.training.demoapp.Repository.RegistrosRepository;

import java.util.List;

public class RegistroViewModel extends AndroidViewModel {

    private RegistrosRepository mRepository;

    private LiveData<List<Registro>> mAllRegistros;

    public RegistroViewModel (Application application) {
        super(application);
        mRepository = new RegistrosRepository(application);
        mAllRegistros = mRepository.getAllWords();
    }

    public LiveData<List<Registro>> getAllRegistros() { return mAllRegistros; }

    public void insert(Registro registro) { mRepository.insert(registro); }
    public void deleteAll() { mRepository.deleteAll(); }
}


