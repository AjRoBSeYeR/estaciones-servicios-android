package android.training.demoapp.ui.activities.tabsListado;

import android.content.Context;
import android.graphics.Color;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment1 extends Fragment {

    private static final String LOG_TAG =  TabFragment1.class.getSimpleName();


    private String shProvincia;
    private EstacionesViewModel estacionesViewModel;
    private Context context;
    private RecyclerView recyclerView;
    private EstacionesServicioAdapter adapter;
    private View rootView;
    private FloatingActionButton fab;
    private int limiteDistancia;


    public TabFragment1() {
        this.limiteDistancia = 5;
    }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
      context = this.getActivity();
      rootView = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
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

    private void obtenerListadoEstacionesDB(){
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        estacionesViewModel = new ViewModelProvider(this).get(EstacionesViewModel.class);
        estacionesViewModel.getCercanas(limiteDistancia).observe(this, new Observer<List<ListaEESSPrecio>>() {
            @Override
            public void onChanged(@Nullable final List<ListaEESSPrecio> est) {
                Log.e(LOG_TAG, "provc"+ est);
                ArrayList<ListaEESSPrecio> ListaEESSPrecio = (ArrayList<ListaEESSPrecio>) est;
                adapter.setRegistros(ListaEESSPrecio);
                pDialog.dismiss();
            }
        });
    }

//    private void obtenerListadoEstacionesDB(){
//        estacionesViewModel = ViewModelProviders.of(this).get(EstacionesViewModel.class);
//        estacionesViewModel.getPorPrecio("precioGasolina95ProtecciN",1).observe(this, new Observer<List<ListaEESSPrecio>>() {
//            @Override
//            public void onChanged(@Nullable final List<ListaEESSPrecio> est) {
//                Log.d(LOG_TAG, "provc"+ est);
//                ArrayList<ListaEESSPrecio> ListaEESSPrecio = (ArrayList<ListaEESSPrecio>) est;
//                adapter.setRegistros(ListaEESSPrecio);
//            }
//        });
//    }




}
