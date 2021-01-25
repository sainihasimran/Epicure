package com.cegep.epicure.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.cegep.epicure.R;
import com.cegep.epicure.list.callback.RemoveItemClickListener;

public class StepsAdapter extends ListAdapter<String, StepsViewHolder> {

    private final RemoveItemClickListener removeItemClickListener;

    public StepsAdapter(RemoveItemClickListener removeItemClickListener) {
        super(new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return false;
            }
        });

        this.removeItemClickListener = removeItemClickListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_preparation_step, parent, false);
        return new StepsViewHolder(view, removeItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }
}
