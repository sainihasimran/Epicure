package com.cegep.epicure;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.cegep.epicure.list.StepsAdapter;
import com.cegep.epicure.model.Ingredient;
import com.cegep.epicure.model.Recipe;
import com.cegep.epicure.network.NetworkInteractor;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.io.IOException;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String KEY_RECIPE_ID = "RECIPE_ID";

    public static Intent getCallingIntent(Context context, int recipeId) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(KEY_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        int recipeId = getIntent().getIntExtra(KEY_RECIPE_ID, -1);
        new FetchRecipeTask().execute(recipeId);
    }

    private void setUiData(Recipe recipe) {
        TextView recipeName = findViewById(R.id.recipe_name);
        TextView recipeTime = findViewById(R.id.recipe_time);
        TextView servingSize = findViewById(R.id.serving_size);
        ImageView recipeImage = findViewById(R.id.recipe_image);
        RecyclerView stepsList = findViewById(R.id.preparationlist);

        ChipGroup chipGroup = findViewById(R.id.ingredients_group);
        for (Ingredient ingredient : recipe.getIngredients()) {
            Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.ingredient_chip, chipGroup, false);
            chip.setCloseIconVisible(false);
            chip.setText(ingredient.Name);
            chipGroup.addView(chip);
        }

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        recipeName.setText(recipe.getName());
        Glide.with(this)
                .load(recipe.getImage())
                .transition(withCrossFade(factory))
                .into(recipeImage);

        int duration = recipe.getDuration();
        if (duration > 60) {
            recipeTime.setText((recipe.getDuration() / 60) + " hrs");
        } else {
            recipeTime.setText(recipe.getDuration() + " mins");
        }

        servingSize.setText(String.format("Serves %d people", recipe.getServingSize()));

        StepsAdapter stepsAdapter = new StepsAdapter(null, false);
        stepsList.setAdapter(stepsAdapter);
        stepsAdapter.submitList(recipe.getPreparationSteps());
    }

    private class FetchRecipeTask extends AsyncTask<Integer, Void, Recipe> {

        @Override
        protected Recipe doInBackground(Integer... integers) {
            try {
                Response<Recipe> recipeResponse = NetworkInteractor.getService().fetchRecipe(integers[0]).execute();
                if (recipeResponse.isSuccessful()) {
                    return recipeResponse.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            super.onPostExecute(recipe);
            if (recipe != null) {
                setUiData(recipe);
            } else {
                Toast.makeText(RecipeDetailActivity.this, "Failed to fetch recipe", Toast.LENGTH_SHORT).show();
            }
        }
    }
}