package android.training.demoapp.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.training.demoapp.pojo.Registro;
import android.training.demoapp.repository.RegistrosRepository;

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


