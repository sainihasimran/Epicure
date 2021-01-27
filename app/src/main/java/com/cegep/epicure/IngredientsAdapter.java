package com.cegep.epicure;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.Viewholder> {

    private  List<IngredientsModal> ingredientsModalList;
      Context context;


    public IngredientsAdapter(List<IngredientsModal> ingredientsModalList) {
        this.ingredientsModalList = ingredientsModalList;
        this.context=context;

    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredientslist,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
TextView ingredent =holder.ingItems;
ingredent.setText(ingredientsModalList.get(position).getIngItems());
    }



    @Override
    public int getItemCount() {
        return ingredientsModalList.size();
    }

      class Viewholder extends RecyclerView.ViewHolder{
        private TextView ingItems;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ingItems=itemView.findViewById(R.id.ingItems);
        }
        public void setData(String items){
            ingItems.setText(items);
        }
    }
}
