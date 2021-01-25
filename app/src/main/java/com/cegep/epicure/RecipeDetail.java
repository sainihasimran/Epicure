package com.cegep.epicure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetail extends AppCompatActivity {
    private RecyclerView rvIng,rvPre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //ingrediets list layout manager

        rvIng=findViewById(R.id.ingredientslist);
        LinearLayoutManager IngredietsLayout= new LinearLayoutManager(this);
        IngredietsLayout.setOrientation(LinearLayoutManager.VERTICAL);
        rvIng.setLayoutManager(IngredietsLayout);

        //making list for ingredients
        List<IngredientsModal> ingredientsModalList = new ArrayList<>();
        ingredientsModalList.add(new IngredientsModal("ITEM1"));
        ingredientsModalList.add(new IngredientsModal("ITEM2"));
        ingredientsModalList.add(new IngredientsModal("ITEM3"));
        ingredientsModalList.add(new IngredientsModal("ITEM4"));
        ingredientsModalList.add(new IngredientsModal("ITEM5"));

        IngredientsAdapter ingredientsAdapter= new IngredientsAdapter(ingredientsModalList);
        rvIng.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();




        //preparation list layout manager

        rvPre=findViewById(R.id.preparationlist);
        LinearLayoutManager PreparationLayout= new LinearLayoutManager(this);
        PreparationLayout.setOrientation(LinearLayoutManager.VERTICAL);
        rvPre.setLayoutManager(PreparationLayout);
        //making list for ingredients
        List<PreparationModal> preparationModalList = new ArrayList<>();
        preparationModalList.add(new PreparationModal("Step1"));
        preparationModalList.add(new PreparationModal("Step2"));
        preparationModalList.add(new PreparationModal("Step3"));
        preparationModalList.add(new PreparationModal("Step4"));
        preparationModalList.add(new PreparationModal("Step5"));

        PreparationAdapter preparationAdapter=new PreparationAdapter(preparationModalList);
        rvPre.setAdapter(preparationAdapter);
        preparationAdapter.notifyDataSetChanged();





    }
}