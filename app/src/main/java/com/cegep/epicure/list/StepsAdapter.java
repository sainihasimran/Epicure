package com.cegep.epicure.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.cegep.epicure.R;
import com.cegep.epicure.list.callback.RemoveItemClickListener;
import com.cegep.epicure.model.PreparationStep;

public class StepsAdapter extends ListAdapter<PreparationStep, StepsViewHolder> {

    private final RemoveItemClickListener removeItemClickListener;

    private final boolean isEditable;

    public StepsAdapter(RemoveItemClickListener removeItemClickListener, boolean isEditable) {
        super(new DiffUtil.ItemCallback<PreparationStep>() {
            @Override
            public boolean areItemsTheSame(@NonNull PreparationStep oldItem, @NonNull PreparationStep newItem) {
                return oldItem.StepDescription.equals(newItem.StepDescription);
            }

            @Override
            public boolean areContentsTheSame(@NonNull PreparationStep oldItem, @NonNull PreparationStep newItem) {
                return false;
            }
        });

        this.removeItemClickListener = removeItemClickListener;
        this.isEditable = isEditable;
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
        holder.bind(getItem(position), position, isEditable);
    }
}
