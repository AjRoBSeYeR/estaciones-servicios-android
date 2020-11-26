package android.training.demoapp.adapters.mapsAdapters;

import android.training.demoapp.domain.mapas.MapCluster;
import android.training.demoapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapsInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final LayoutInflater mInflater;

    public MapsInfoWindowAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    @Override public View getInfoWindow(Marker marker) {
        //si este es nulo se pone el fondo por defecto
        return null;
    }

    @Override public View getInfoContents(Marker marker) {
        final View itemView = mInflater.inflate(
                R.layout.recyclerview_estaciones_servicio_item, null);

        MapCluster infoWindowData = (MapCluster) marker.getTag();

        TextView rotulo = itemView.findViewById(R.id.rotulo);
        TextView localidad = itemView.findViewById(R.id.localidad);
        TextView direccion = itemView.findViewById(R.id.direccion);
        TextView precioGasolina = itemView.findViewById(R.id.precioGasolina);
        TextView precioDiesel = itemView.findViewById(R.id.precioDiesel);
        TextView distancia = itemView.findViewById(R.id.distancia);
        TextView horario = itemView.findViewById(R.id.horario);

        rotulo.setText(infoWindowData.getEstacioneServicio().getRotulo());
        localidad.setText(infoWindowData.getEstacioneServicio().getLocalidad());
        direccion.setText(infoWindowData.getEstacioneServicio().getDirecciN());
        precioGasolina.setText(infoWindowData.getEstacioneServicio().getPrecioGasolina95ProtecciN());
        precioDiesel.setText(infoWindowData.getEstacioneServicio().getPrecioGasoleoA());
        distancia.setText((String.valueOf(infoWindowData.getEstacioneServicio().getDistancia())));
        horario.setText(infoWindowData.getEstacioneServicio().getHorario());

        return itemView;
    }

}