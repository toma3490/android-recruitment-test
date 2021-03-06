package dog.snow.androidrecruittest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import dog.snow.androidrecruittest.R;
import dog.snow.androidrecruittest.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> items;
    private Picasso picasso;
    private LayoutInflater layoutInflater;

    public ItemAdapter(Context context, List<Item> items) {
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
        this.picasso = Picasso.with(context);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;
        private TextView description;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            icon = (ImageView) itemView.findViewById(R.id.icon_ic);
            name = (TextView) itemView.findViewById(R.id.name_tv);
            description = (TextView) itemView.findViewById(R.id.description_tv);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item item = items.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        //load image from cache
        picasso.load(item.getUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.placeholder)
                .into(holder.icon, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        picasso.load(item.getUrl())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(holder.icon);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItems(){
        this.items.clear();
        notifyDataSetChanged();
    }
}
