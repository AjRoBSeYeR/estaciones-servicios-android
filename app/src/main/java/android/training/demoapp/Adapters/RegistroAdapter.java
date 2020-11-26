package android.training.demoapp.adapters;

/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.training.demoapp.pojo.Registro;
import android.training.demoapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {

    private final LayoutInflater mInflater;
    private List<Registro> mRegistros;

    class RegistroViewHolder extends RecyclerView.ViewHolder {
        private final TextView FechaItemView;
        private final TextView EurosItemView;
        private final TextView LitrosItemView;
        private final TextView KmTotalesItemView;
        private final TextView MediaItemView;

        private RegistroViewHolder(View itemView) {
            super(itemView);
            FechaItemView = itemView.findViewById(R.id.tv_titulo);
            EurosItemView = itemView.findViewById(R.id.EurosView);
            LitrosItemView = itemView.findViewById(R.id.LitrosView);
            KmTotalesItemView = itemView.findViewById(R.id.KmTotalesView);
            MediaItemView = itemView.findViewById(R.id.media);

        }
    }



    public RegistroAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public RegistroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_registro_item, parent, false);
        return new RegistroViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RegistroViewHolder holder, int position) {
        Registro current = mRegistros.get(position);
        holder.FechaItemView.setText(DateFormat.format("dd MMMM yyyy", current.getFecha()).toString());
        holder.EurosItemView.setText((String.valueOf(current.getEuros())));
        holder.LitrosItemView.setText((String.valueOf(current.getLitros())));
        holder.KmTotalesItemView.setText((String.valueOf(current.getKmTotales())));
        holder.MediaItemView.setText(String.valueOf(obtenerConsumoMedio(current.getKmTotales(), current.getEuros())));

    }

    public void setRegistros(List<Registro> registro){
        mRegistros = registro;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mRegistros != null)
            return mRegistros.size();
        else return 0;
    }

    public double obtenerConsumoMedio(Double km, Double litros){
        return km/litros;
    }

}



