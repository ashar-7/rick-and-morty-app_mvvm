package com.se7en.rmdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.se7en.rmdb.R;
import com.se7en.rmdb.data.models.locations.Location;


public class LocationsListAdapter extends PagedListAdapter<Location, LocationsListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.locationNameTextView);
        }
    }

    private static DiffUtil.ItemCallback<Location> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Location>() {
                @Override
                public boolean areItemsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };

    public interface OnItemClickListener {
        void onClick(int position, View view);
    }

    private OnItemClickListener itemClickListener;
    private Context context;

    public LocationsListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.location_item_recyclerview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = getItem(position);
        if(location == null) return;

        String name = location.getName();

        holder.nameTextView.setText(name);
        holder.itemView.setOnClickListener(v -> itemClickListener.onClick(position, v));
    }
}
