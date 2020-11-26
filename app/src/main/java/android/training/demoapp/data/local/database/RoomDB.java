package android.training.demoapp.data.local.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import android.training.demoapp.pojo.ListaEESSPrecio;
import android.training.demoapp.pojo.Provincia;
import android.training.demoapp.pojo.Registro;

@Database(entities = {Registro.class, Provincia.class, ListaEESSPrecio.class}, version = 15, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {


    private static RoomDB INSTANCE;
    public abstract RegistroDAO registroDAO();
    public abstract ProvinciaDAO provinciaDAO();
    public abstract EstacionesProvinciaDAO estacionesProvinciaDAO();

    public static RoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, "gas_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RegistroDAO mDao;

//        Registro[] registros = {
//                //new Registro("2019-05-06")
//        };
        PopulateDbAsync(RoomDB db) {
            mDao = db.registroDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

         //   mDao.deleteAll();

//            for (int i = 0; i <= registros.length - 1; i++) {
//                Registro registro = registros[i];
//                mDao.insert(registro);
//            }
            return null;
        }
    }
}
