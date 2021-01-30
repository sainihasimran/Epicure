package com.cegep.epicure.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cegep.epicure.R;
import com.cegep.epicure.list.callback.RemoveItemClickListener;
import com.cegep.epicure.model.PreparationStep;

class StepsViewHolder extends RecyclerView.ViewHolder {

    private TextView stepNumberTextView;
    private TextView stepTextView;
    private ImageView removeButton;

    private RemoveItemClickListener removeItemClickListener;

    private PreparationStep step;

    StepsViewHolder(@NonNull View view, RemoveItemClickListener removeItemClickListener) {
        super(view);
        this.removeItemClickListener = removeItemClickListener;

        stepNumberTextView = view.findViewById(R.id.step_number);
        stepTextView = view.findViewById(R.id.step_text);
        removeButton = view.findViewById(R.id.remove_button);
    }

    void bind(PreparationStep step, int position, Boolean isEditable) {
        this.step = step;
        stepTextView.setText(step.StepDescription);
        stepNumberTextView.setText(String.valueOf(position + 1));
        removeButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);
        removeButton.setOnClickListener(v -> {
            if (removeItemClickListener != null) {
                removeItemClickListener.onRemoveClick(step, getAdapterPosition());
            }
        });
    }
}
