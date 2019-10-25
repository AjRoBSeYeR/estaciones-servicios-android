package android.training.demoapp.data.local.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import android.training.demoapp.pojo.ListaEESSPrecio;

import java.util.List;
//https://developer.android.com/reference/android/arch/persistence/room/Query
//https://stackoverflow.com/questions/50104554/room-user-configurable-order-by-queries

//https://developer.android.com/training/data-storage/room/creating-views
@Dao
public interface EstacionesProvinciaDAO {
//(onConflict = OnConflictStrategy.IGNORE)
    @Insert
    void insert(ListaEESSPrecio estaciones);


    @Query("SELECT * FROM estaciones_provincia_tabla ORDER BY gasolina_name IS NULL, gasolina_name ASC  LIMIT 10")
    LiveData<List<ListaEESSPrecio>> getGasolinaMasBarata();

    @Query("SELECT * FROM estaciones_provincia_tabla ORDER BY precioGasoleoA IS NULL, precioGasoleoA ASC  LIMIT 10")
    LiveData<List<ListaEESSPrecio>> getDieselMasBarato();


//    @Query("SELECT * from estaciones_provincia_tabla ")
//    LiveData<List<ListaEESSPrecio>> getAll();

    @Query("SELECT * FROM estaciones_provincia_tabla")
    LiveData<List<ListaEESSPrecio>> getAll();

    @Query("SELECT * FROM estaciones_provincia_tabla WHERE distancia < :limiteDistancia ORDER BY distancia IS NULL, distancia,gasolina_name, precioGasoleoA ASC")
    LiveData<List<ListaEESSPrecio>> getCercanas(int limiteDistancia);

    @Query("DELETE FROM  estaciones_provincia_tabla")
    void deleteAll();


    @Query("SELECT * FROM estaciones_provincia_tabla WHERE cp=:combustible limit :limit")
    LiveData<List<ListaEESSPrecio>> getPorPrecio(int combustible, int limit);

    @Query("SELECT * FROM estaciones_provincia_tabla" +
            " WHERE" +
            " CASE WHEN :param=1 THEN gasolina_name NOT NULL END OR" +
            " CASE WHEN :param=2 THEN precioGasoleoA NOT NULL END" +
            " ORDER BY" +
            " CASE WHEN :param = 1 THEN  gasolina_name END ASC," +
            " CASE WHEN :param = 2 THEN  precioGasoleoA END ASC" +
            " limit :limit")
    LiveData<List<ListaEESSPrecio>> getPorPrecio2(int param, int limit);




}


//    @Query("select * from estaciones_provincia_tabla ORDER BY order=:arg0 asc limit numberOfCoins=:arg1")
//    LiveData<List<ListaEESSPrecio>> getAllTop(order: String, numberOfCoins: Int): Flowable<List<CoinDB>>

//10gasolinas mas baratas
//SELECT * FROM estaciones_provincia_tabla ORDER BY precioGasolina95ProtecciN IS NULL, precioGasolina95ProtecciN ASC  LIMIT 10

//10gasolinas98 mas baratas
//SELECT * FROM estaciones_provincia_tabla ORDER BY precioGasolina98 IS NULL, precioGasolina98 ASC  LIMIT 10

//SELECT * FROM estaciones_provincia_tabla ORDER BY precioGasoleoA IS NULL, precioGasoleoA ASC  LIMIT 10

//SELECT * FROM estaciones_provincia_tabla ORDER BY precioNuevoGasoleoA IS NULL, precioNuevoGasoleoA ASC  LIMIT 10



