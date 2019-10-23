package android.training.demoapp.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.training.demoapp.Pojo.Provincia;
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










//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        Provincia provincia = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        ViewHolder viewHolder; // view lookup cache stored in tag
//        if (convertView == null) {
//            // If there's no view to re-use, inflate a brand new view for row
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.provincias_spiner_item, parent, false);
//            viewHolder.nombreProv = (TextView) convertView.findViewById(R.id.nombre_prov);
//            // Cache the viewHolder object inside the fresh view
//            convertView.setTag(viewHolder);
//        } else {
//            // View is being recycled, retrieve the viewHolder object from tag
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        // Populate the data from the data object via the viewHolder object
//        // into the template view.
//        viewHolder.nombreProv.setText(provincia.getProvincia());
//        // Return the completed view to render on screen
//        return convertView;
//    }
}