package android.training.demoapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import android.training.demoapp.Pojo.Provincia;

import java.util.List;
@Dao
public interface ProvinciaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Provincia provincia);

    @Query("SELECT * from provincia_tabla ORDER BY provincia ASC")
    LiveData<List<Provincia>> getAllProvincias();


}
