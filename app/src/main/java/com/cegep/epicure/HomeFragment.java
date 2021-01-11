package com.cegep.epicure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cegep.epicure.helper.CategoryUiHandler;
import com.cegep.epicure.list.RecipeAdapter;
import com.cegep.epicure.model.Category;
import com.cegep.epicure.model.Recipe;
import com.cegep.epicure.model.User;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CategoryUiHandler.CategorySelectedListener {

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        new CategoryUiHandler(view, this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: 02/01/21 Remove hardcoded user
        User user = new User();

        TextView nameTextView = view.findViewById(R.id.name);
        nameTextView.setText(getString(R.string.greeting, user.firstName));

        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            recipes.add(new Recipe());
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        RecipeAdapter recipeAdapter = new RecipeAdapter(recipes);
        recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onCategorySelected(Category category) {

    }
}