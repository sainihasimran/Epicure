package com.cegep.epicure;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
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
import com.cegep.epicure.network.NetworkInteractor;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class HomeFragment extends Fragment implements CategoryUiHandler.CategorySelectedListener {

    private RecyclerView recyclerView;

    private FetchRecipesTask currentFetchRecipesTask;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private MainNavigator mainNavigator;

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

        view.findViewById(R.id.add_recipe_button).setOnClickListener(v -> {
            if (mainNavigator != null) {
                mainNavigator.navigateToCreateRecipeScreen();
            }
        });

        User user = new User();

        TextView nameTextView = view.findViewById(R.id.name);
        nameTextView.setText(getString(R.string.greeting, user.FirstName));

        recyclerView = view.findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onCategorySelected(Category category) {
        if (currentFetchRecipesTask != null) {
            currentFetchRecipesTask.cancel(true);
        }

        currentFetchRecipesTask = new FetchRecipesTask();
        currentFetchRecipesTask.execute(category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainNavigator) {
            mainNavigator = (MainNavigator) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainNavigator = null;
    }

    private class FetchRecipesTask extends AsyncTask<Category, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(Category... categories) {
            try {
                Response<List<Recipe>> recipesResponse = NetworkInteractor.getService().fetchRecipes(categories[0].getNiceName()).execute();
                if (recipesResponse.isSuccessful()) {
                    return recipesResponse.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            if (recipes != null) {
                RecipeAdapter recipeAdapter = new RecipeAdapter(recipes);
                recyclerView.setAdapter(recipeAdapter);
            } else {
                Toast.makeText(requireContext(), "Failed to fetch recipes", Toast.LENGTH_SHORT).show();
            }
        }
    }
}