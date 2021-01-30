package com.cegep.epicure.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.cegep.epicure.ItemClickListener;
import com.cegep.epicure.R;
import com.cegep.epicure.model.Recipe;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

class RecipeViewHolder extends RecyclerView.ViewHolder {

    public TextView recipeNameTextView;
    public ImageView recipeImage;

    private Recipe recipe;

    RecipeViewHolder(@NonNull View itemView, ItemClickListener<Recipe> recipeItemClickListener) {
        super(itemView);
        itemView.setOnClickListener(v -> recipeItemClickListener.onItemClick(recipe));
        recipeNameTextView = itemView.findViewById(R.id.recipe_name_text);
        recipeImage = itemView.findViewById(R.id.recipe_image);
    }

    void bind(Recipe recipe) {
        this.recipe = recipe;
        recipeNameTextView.setText(recipe.getName());

        Glide.with(itemView.getContext())
                .load(recipe.getImage())
                .into(recipeImage);
    }
}
