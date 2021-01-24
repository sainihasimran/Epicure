package com.cegep.epicure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IngredientsAdapter  extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] Ingredients;
    public IngredientsAdapter(Activity context, String[] Ingredients) {
        super(context, R.layout.ingredientslist, Ingredients);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.Ingredients = Ingredients;
    }
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.ingredientslist, null,true);

            TextView itemName = (TextView) rowView.findViewById(R.id.ingItems);
            itemName.setText(Ingredients[position]);
            return rowView;

        };



}
