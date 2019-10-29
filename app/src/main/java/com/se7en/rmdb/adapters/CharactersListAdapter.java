package com.se7en.rmdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.se7en.rmdb.R;
import com.se7en.rmdb.data.models.characters.Character;

public class CharactersListAdapter extends PagedListAdapter<Character, CharactersListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImageView;
        TextView nameTextView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            characterImageView = itemView.findViewById(R.id.characterImageView);
            nameTextView = itemView.findViewById(R.id.locationNameTextView);
        }
    }

    private static DiffUtil.ItemCallback<Character> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Character>() {
                @Override
                public boolean areItemsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
                    return (
                            oldItem.getName().equals(newItem.getName())
                            && oldItem.getImage().equals(newItem.getImage())
                    );
                }
            };

    public interface OnItemClickListener {
        void onClick(int position, View view);
    }

    private OnItemClickListener itemClickListener;
    private Context context;

    public CharactersListAdapter(Context context) {
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
                    .inflate(R.layout.character_item_recyclerview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = getItem(position);
        if(character == null) return;

        String name = character.getName();
        String imageURL = character.getImage();

        holder.nameTextView.setText(name);
        holder.itemView.setOnClickListener(v -> itemClickListener.onClick(position, v));

        Glide.with(context)
                .load(imageURL)
                .centerCrop()
                .into(holder.characterImageView);
    }
}
