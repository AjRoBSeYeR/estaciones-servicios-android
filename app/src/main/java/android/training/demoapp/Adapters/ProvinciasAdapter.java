package android.training.demoapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.training.demoapp.domain.Provincia;
import android.training.demoapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProvinciasAdapter extends ArrayAdapter<Provincia> {
    private final LayoutInflater mInflater;
    private  List<Provincia> provincias;

    private static class ViewHolder {
        TextView nombreProv;
    }

    public ProvinciasAdapter(
            Context context,
            List<Provincia> provinciasLista) {
        super(context, R.layout.provincias_spiner_item, provinciasLista);
        mInflater = LayoutInflater.from(context);
        provincias = provinciasLista;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){

        final View view = mInflater.inflate(R.layout.provincias_spiner_item, parent, false);
        TextView nombreProv = (TextView) view.findViewById(R.id.nombre_prov);
        Provincia provincia = provincias.get(position);
        nombreProv.setText(provincia.getProvincia());
        return view;
    }


}