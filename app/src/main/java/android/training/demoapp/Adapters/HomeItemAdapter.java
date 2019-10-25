package android.training.demoapp.adapters;

import android.content.Context;
import android.training.demoapp.pojo.HomeItem;
import android.training.demoapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<HomeItem> items;
    private final onItemClick listener;

    public HomeItemAdapter(Context context, List<HomeItem> item,onItemClick listener) {
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
        this.listener = listener;
    }



    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recyclerview_home_item, parent, false);
        return new HomeItemAdapter.HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        HomeItem current = items.get(position);
        holder.tvTitle.setText((String.valueOf(current.getTitulo())));

        holder.tvDescription.setText((String.valueOf(current.getDescriptcion())));
        holder.bind(items.get(position), listener);

    }
    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else return 0;
    }

    public void setHomeItem(List<HomeItem> homeItem){
        items = homeItem;
        notifyDataSetChanged();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvDescription;

        private HomeViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }

        public void bind(final HomeItem item, final onItemClick listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    public interface onItemClick {
        void onItemClick(HomeItem item);
    }







}
