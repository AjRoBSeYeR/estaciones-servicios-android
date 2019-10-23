package android.training.demoapp.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.training.demoapp.Adapters.EstacionesServicioAdapter;
import android.training.demoapp.Pojo.ListaEESSPrecio;
import android.training.demoapp.R;
import android.training.demoapp.ViewModel.EstacionesViewModel;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment1 extends Fragment {

    private static final String LOG_TAG =  TabFragment1.class.getSimpleName();
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "sharedPrefFile";
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
    // Inflate the layout for this fragment
      rootView = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
      obtenerListadoEstacionesDB();
      iniciar();
     // mostrarAjustes();
//      If I understood you well getFragmentManager() is now deprecated
//      we will use getChildFragmentManager() Return a private FragmentManager for placing and managing Fragments inside of this Fragment.
//    we will use getParentFragmentManager() Return the FragmentManager for interacting with fragments associated with this fragment's activity.



      return rootView;
  }


    private void iniciar(){
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        shProvincia = mPreferences.getString("IdProvincia", shProvincia);
        recyclerView = rootView.findViewById(R.id.recyclerview_listado_estaciones);
        adapter = new EstacionesServicioAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    private void obtenerListadoEstacionesDB(){
        estacionesViewModel = new ViewModelProvider(this).get(EstacionesViewModel.class);
        estacionesViewModel.getCercanas(limiteDistancia).observe(this, new Observer<List<ListaEESSPrecio>>() {
            @Override
            public void onChanged(@Nullable final List<ListaEESSPrecio> est) {
                Log.d(LOG_TAG, "provc"+ est);
                ArrayList<ListaEESSPrecio> ListaEESSPrecio = (ArrayList<ListaEESSPrecio>) est;
                adapter.setRegistros(ListaEESSPrecio);
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
