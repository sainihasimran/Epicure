package com.cegep.epicure.list;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cegep.epicure.R;
import com.cegep.epicure.list.callback.RemoveItemClickListener;

class StepsViewHolder extends RecyclerView.ViewHolder {

    private TextView stepNumberTextView;
    private TextView stepTextView;

    private RemoveItemClickListener removeItemClickListener;

    private String step;

    StepsViewHolder(@NonNull View view, RemoveItemClickListener removeItemClickListener) {
        super(view);
        this.removeItemClickListener = removeItemClickListener;

        stepNumberTextView = view.findViewById(R.id.step_number);
        stepTextView = view.findViewById(R.id.step_text);
        view.findViewById(R.id.remove_button).setOnClickListener(v -> removeItemClickListener.onRemoveClick(step, getAdapterPosition()));
    }

    void bind(String step, int position) {
        this.step = step;
        stepTextView.setText(step);
        stepNumberTextView.setText(String.valueOf(position + 1));
    }
}
