package android.training.demoapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import android.os.AsyncTask;
import android.training.demoapp.data.local.database.RegistroDAO;
import android.training.demoapp.data.local.database.RoomDB;
import android.training.demoapp.pojo.Registro;

import java.util.List;

public class RegistrosRepository {
    private RegistroDAO mRegistroDAO;
    private LiveData<List<Registro>> mAllRegistros;

    public RegistrosRepository(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        mRegistroDAO = db.registroDAO();
        mAllRegistros = mRegistroDAO.getAllRegistros();
    }
    public LiveData<List<Registro>> getAllWords() {
        return mAllRegistros;
    }

    public void insert (Registro registro) {
        new insertAsyncTask(mRegistroDAO).execute(registro);
    }

    public void deleteAll () {
        new deleteAllAsyncTask(mRegistroDAO).execute();
    }




    static class insertAsyncTask extends AsyncTask<Registro, Void, Void> {

        private RegistroDAO mAsyncTaskDao;

        insertAsyncTask(RegistroDAO dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Registro... registros) {
            mAsyncTaskDao.insert(registros[0]);
            return null;
        }
    }



    private static class deleteAllAsyncTask extends AsyncTask<Registro, Void, Void> {

        private RegistroDAO mAsyncTaskDao;

        deleteAllAsyncTask(RegistroDAO dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Registro... registros) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }





}
