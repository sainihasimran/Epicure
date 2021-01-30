package com.cegep.epicure.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cegep.epicure.ItemClickListener;
import com.cegep.epicure.R;
import com.cegep.epicure.model.Recipe;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final List<Recipe> recipes;
    private final ItemClickListener<Recipe> recipeItemClickListener;

    public RecipeAdapter(List<Recipe> recipes, ItemClickListener<Recipe> recipeItemClickListener) {
        this.recipes = recipes;
        this.recipeItemClickListener = recipeItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view, recipeItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void addItem(Recipe recipe) {
        recipes.add(recipe);
        notifyDataSetChanged();
    }
}
