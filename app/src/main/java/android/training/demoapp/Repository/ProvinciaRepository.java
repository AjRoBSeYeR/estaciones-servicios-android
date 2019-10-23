package android.training.demoapp.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.training.demoapp.Database.ProvinciaDAO;
import android.training.demoapp.Database.RoomDB;
import android.training.demoapp.Pojo.Provincia;

import java.util.List;

public class ProvinciaRepository {

    private ProvinciaDAO provinciaDAO;

    public ProvinciaRepository(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        provinciaDAO = db.provinciaDAO();
    }

    public void insert (Provincia provincia) {
        new ProvinciaRepository.insertAsyncTask(provinciaDAO).execute(provincia);
    }
    private static class insertAsyncTask extends AsyncTask<Provincia, Void, Void> {

        private ProvinciaDAO mAsyncTaskDao;

        insertAsyncTask(ProvinciaDAO dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Provincia... provincias) {
            mAsyncTaskDao.insert(provincias[0]);
            return null;
        }
    }


    public LiveData<List<Provincia>> getAllProvincias() {
            return provinciaDAO.getAllProvincias();
    }

//    private static class getAllProvinciasAsyncTask extends AsyncTask<Provincia, Void, Void> {
//
//        private ProvinciaDAO mAsyncTaskDao;
//
//        getAllProvinciasAsyncTask(ProvinciaDAO dao) {
//            mAsyncTaskDao = dao;
//        }
//
//
//        @Override
//        protected Void doInBackground(Provincia... provs) {
//            mAsyncTaskDao.getAllProvincias();
//            return null;
//        }
//    }

}
