package android.training.demoapp.adapters;

import android.content.Context;
import android.training.demoapp.pojo.ListaEESSPrecio;
import android.training.demoapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EstacionesServicioAdapter extends RecyclerView.Adapter<EstacionesServicioAdapter.EstacionesServicioViewHolder> {



    class EstacionesServicioViewHolder extends RecyclerView.ViewHolder {
        private final TextView rotulo;
        private final TextView localidad;
        private final TextView precioGasolina;
        private final TextView precioDiesel;
        private final TextView direccion;
        private final TextView distancia;
        private final TextView horario;

        private EstacionesServicioViewHolder(View itemView) {
            super(itemView);
            rotulo = itemView.findViewById(R.id.rotulo);
            localidad = itemView.findViewById(R.id.localidad);
            direccion = itemView.findViewById(R.id.direccion);
            precioGasolina = itemView.findViewById(R.id.precioGasolina);
            precioDiesel = itemView.findViewById(R.id.precioDiesel);
            distancia = itemView.findViewById(R.id.distancia);
            horario = itemView.findViewById(R.id.horario);
        }
    }

    private final LayoutInflater mInflater;
    private List<ListaEESSPrecio> mListaEESSPrecio;

    public EstacionesServicioAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public EstacionesServicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_estaciones_servicio_item, parent, false);
        return new EstacionesServicioAdapter.EstacionesServicioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EstacionesServicioAdapter.EstacionesServicioViewHolder holder, int position) {
        ListaEESSPrecio actual = mListaEESSPrecio.get(position);
        holder.rotulo.setText((String.valueOf(actual.getRotulo())));
        holder.localidad.setText((String.valueOf(actual.getLocalidad())));
        holder.direccion.setText((String.valueOf(actual.getDirecciN())));
        holder.precioDiesel.setText((String.valueOf("Gasoleo A: "+actual.getPrecioGasoleoA())));
        holder.precioGasolina.setText((String.valueOf("Gasolina 95: "+actual.getPrecioGasolina95ProtecciN())));
        holder.distancia.setText((String.valueOf(actual.getDistancia()+"km")));
        holder.horario.setText((String.valueOf(actual.getHorario())));
    }
    public void setRegistros(List<ListaEESSPrecio> registro){
        mListaEESSPrecio = registro;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mListaEESSPrecio != null)
            return mListaEESSPrecio.size();
        else return 0;
    }

}

