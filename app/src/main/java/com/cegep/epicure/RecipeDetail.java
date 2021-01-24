package com.cegep.epicure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class RecipeDetail extends AppCompatActivity {
    ListView list;
    String[] Ingredients ={
            "item 1","item 2",
            "item 3","item 4",
            "item 5",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        IngredientsAdapter adapter=new IngredientsAdapter(this, Ingredients);
        list=(ListView)findViewById(R.id.ingredientslist);
        list.setAdapter(adapter);
    }
}