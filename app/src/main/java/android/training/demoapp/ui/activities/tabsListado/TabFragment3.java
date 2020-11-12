package android.training.demoapp.ui.activities.tabsListado;


import android.content.Context;
import android.os.Bundle;
import android.training.demoapp.adapters.EstacionesServicioAdapter;
import android.training.demoapp.pojo.ListaEESSPrecio;
import android.training.demoapp.R;
import android.training.demoapp.ui.tools.sharedPrefs.SharedPrefs;
import android.training.demoapp.viewModel.EstacionesViewModel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment3 extends Fragment {

  private static final String LOG_TAG =  TabFragment3.class.getSimpleName();

  private String sharedPrefFile = "sharedPrefFile";
  private String shProvincia;

  private EstacionesViewModel estacionesViewModel;
  private Context context;
  private RecyclerView recyclerView;
  private EstacionesServicioAdapter adapter;
  private  View rootView;
  private int diesel;

  private int limiteCombustible;
  public TabFragment3()  {
    this.diesel = 2;
    this.limiteCombustible = 10;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    context = this.getActivity();
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);

    iniciar();
    obtenerListadoEstacionesDB();

    return rootView;
  }


  private void iniciar(){
    shProvincia = (String) SharedPrefs.getPref(context,SharedPrefs.PROVINCIA_ID);

    recyclerView = rootView.findViewById(R.id.recyclerview_listado_estaciones);
    adapter = new EstacionesServicioAdapter(context);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));

  }

//  private void obtenerListadoEstacionesDB(){
//    estacionesViewModel =  new ViewModelProvider(this).get(EstacionesViewModel.class);
//    estacionesViewModel.getPorPrecio2(diesel,limiteCombustible).observe(this, new Observer<List<ListaEESSPrecio>>() {
//      @Override
//      public void onChanged(@Nullable final List<ListaEESSPrecio> est) {
//        Log.d(LOG_TAG, "provc"+ est);
//        ArrayList<ListaEESSPrecio> ListaEESSPrecio = (ArrayList<ListaEESSPrecio>) est;
//        adapter.setRegistros(ListaEESSPrecio);
//      }
//    });
//  }

  private void obtenerListadoEstacionesDB(){
    estacionesViewModel = new ViewModelProvider(this).get(EstacionesViewModel.class);
    estacionesViewModel.getDieselMasBarato().observe(this, new Observer<List<ListaEESSPrecio>>() {
      @Override
      public void onChanged(@Nullable final List<ListaEESSPrecio> est) {
        Log.d(LOG_TAG, "provc"+ est);
        ArrayList<ListaEESSPrecio> ListaEESSPrecio = (ArrayList<ListaEESSPrecio>) est;
        adapter.setRegistros(ListaEESSPrecio);
      }
    });
  }

}
