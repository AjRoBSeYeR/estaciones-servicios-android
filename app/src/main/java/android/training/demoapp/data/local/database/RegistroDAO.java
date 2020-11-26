package android.training.demoapp.data.local.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import android.training.demoapp.pojo.Registro;

import java.util.List;
@Dao
public interface RegistroDAO {

    @Query("SELECT * from registro_tabla ORDER BY fecha DESC")
    LiveData<List<Registro>> getAllRegistros();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Registro registro);

    @Query("DELETE FROM registro_tabla")
    void deleteAll();
}

