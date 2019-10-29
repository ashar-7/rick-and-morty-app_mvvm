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
import com.se7en.rmdb.data.models.episodes.Episode;


public class EpisodesListAdapter extends PagedListAdapter<Episode, EpisodesListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.episodeNameTextView);
        }
    }

    private static DiffUtil.ItemCallback<Episode> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Episode>() {
                @Override
                public boolean areItemsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };

    public interface OnItemClickListener {
        void onClick(int position, View view);
    }

    private OnItemClickListener itemClickListener;
    private Context context;

    public EpisodesListAdapter(Context context) {
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
                .inflate(R.layout.episode_item_recyclerview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Episode episode = getItem(position);
        if(episode == null) return;

        String episode_num = episode.getEpisode();
        String name = episode.getName();

        String episodeString = context.getString(R.string.episodeItem, episode_num, name);
        holder.nameTextView.setText(episodeString);
        holder.itemView.setOnClickListener(v -> itemClickListener.onClick(position, v));
    }
}
