package android.training.demoapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.training.demoapp.data.local.database.EstacionesProvinciaDAO;
import android.training.demoapp.data.local.database.RoomDB;
import android.training.demoapp.pojo.ListaEESSPrecio;
import android.training.demoapp.pojo.Registro;

import java.util.List;

public class EstacionesRepository {

    private EstacionesProvinciaDAO estacionesProvinciaDAO;
    private LiveData<List<ListaEESSPrecio>> estacionesServicio;
    private LiveData<List<ListaEESSPrecio>> gasolinaMasBarata;
    private LiveData<List<ListaEESSPrecio>> dieselBarato;

    public EstacionesRepository(Application application) {
        RoomDB db = RoomDB.getDatabase(application);
        estacionesProvinciaDAO = db.estacionesProvinciaDAO();
        estacionesServicio = estacionesProvinciaDAO.getAll();
        gasolinaMasBarata = estacionesProvinciaDAO.getGasolinaMasBarata();
        dieselBarato = estacionesProvinciaDAO.getDieselMasBarato();
    }



    public void insert (ListaEESSPrecio esta) {
        new EstacionesRepository.insertAsyncTask(estacionesProvinciaDAO).execute(esta);
    }

    private static class insertAsyncTask extends AsyncTask<ListaEESSPrecio, Void, Void> {

        private EstacionesProvinciaDAO mAsyncTaskDao;

        insertAsyncTask(EstacionesProvinciaDAO dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(ListaEESSPrecio... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    public LiveData<List<ListaEESSPrecio>> getAll() {
        return estacionesServicio;
    }



    public LiveData<List<ListaEESSPrecio>> getGasolinaMasBarata() {
       return estacionesProvinciaDAO.getGasolinaMasBarata();
    }

    public LiveData<List<ListaEESSPrecio>> getDieselMasBarato() {
        return dieselBarato;
    }

    public LiveData<List<ListaEESSPrecio>> getPorPrecio(int combustible, int limit) {
        return estacionesProvinciaDAO.getPorPrecio(combustible, limit);

    }

    public LiveData<List<ListaEESSPrecio>> getPorPrecio2(int combustible, int limit) {
        return estacionesProvinciaDAO.getPorPrecio2(combustible, limit);

    }

    public LiveData<List<ListaEESSPrecio>> getCercanas(int limiteDistanciat) {
        return estacionesProvinciaDAO.getCercanas(limiteDistanciat);
    }



    public void deleteAll () {
        new deleteAllAsyncTask(estacionesProvinciaDAO).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Registro, Void, Void> {

        private EstacionesProvinciaDAO mAsyncTaskDao;

        deleteAllAsyncTask(EstacionesProvinciaDAO dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Registro... registros) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }











}
