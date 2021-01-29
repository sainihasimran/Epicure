package com.cegep.epicure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PreparationAdapter extends RecyclerView.Adapter<PreparationAdapter.Viewholder> {
    private List<PreparationModal> preparationModalList;
    Context context;

    public PreparationAdapter(List<PreparationModal> preparationModalList) {
        this.preparationModalList = preparationModalList;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.preparationlist,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TextView preparations =holder.Preitems;
        preparations.setText(preparationModalList.get(position).getPreitems());
    }

    @Override
    public int getItemCount() {
        return preparationModalList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        private TextView Preitems;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Preitems=itemView.findViewById(R.id.PreItems);
        }
        public void setData(String items){
            Preitems.setText(items);
        }
    }
}
