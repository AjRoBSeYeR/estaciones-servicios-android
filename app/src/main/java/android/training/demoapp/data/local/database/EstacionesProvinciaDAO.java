package android.training.demoapp.data.local.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import android.training.demoapp.domain.ListaEESSPrecio;

import java.util.List;

@Dao
public interface EstacionesProvinciaDAO {
//(onConflict = OnConflictStrategy.IGNORE)
    @Insert
    void insert(ListaEESSPrecio estaciones);

    @Query("SELECT * FROM estaciones_provincia_tabla ORDER BY gasolina_name IS NULL, gasolina_name ASC  LIMIT 100")
    LiveData<List<ListaEESSPrecio>> getGasolinaMasBarata();

    @Query("SELECT * FROM estaciones_provincia_tabla ORDER BY precioGasoleoA IS NULL, precioGasoleoA ASC  LIMIT 100")
    LiveData<List<ListaEESSPrecio>> getDieselMasBarato();

    @Query("SELECT * FROM estaciones_provincia_tabla")
    LiveData<List<ListaEESSPrecio>> getAll();

    @Query("SELECT * FROM estaciones_provincia_tabla WHERE distancia < :limiteDistancia  AND gasolina_name!= \"\"  AND precioGasoleoA!= \"\" ORDER BY distancia IS NULL, distancia,gasolina_name, precioGasoleoA ASC")
    LiveData<List<ListaEESSPrecio>> getCercanas(int limiteDistancia);

    @Query("DELETE FROM  estaciones_provincia_tabla")
    void deleteAll();

    @Query("SELECT * FROM estaciones_provincia_tabla WHERE cp=:combustible limit :limit")
    LiveData<List<ListaEESSPrecio>> getPorPrecio(int combustible, int limit);

    @Query("SELECT * FROM estaciones_provincia_tabla" +
            " WHERE" +
            " CASE WHEN :param=1 THEN gasolina_name NOT NULL AND gasolina_name!= \"\" END OR" +
            " CASE WHEN :param=2 THEN precioGasoleoA NOT NULL AND precioGasoleoA!= \"\" END" +
            " ORDER BY" +
            " CASE WHEN :param = 1 THEN  gasolina_name END ASC," +
            " CASE WHEN :param = 2 THEN  precioGasoleoA END ASC" +
            " limit :limit")
    LiveData<List<ListaEESSPrecio>> getPorPrecio2(int param, int limit);

}


