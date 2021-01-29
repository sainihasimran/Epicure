package com.cegep.epicure;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.cegep.epicure.model.Recipe;

public class MainActivity extends AppCompatActivity implements MainNavigator, NewRecipeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance(), HomeFragment.class.getName())
                .commit();
    }

    @Override
    public void navigateToCreateRecipeScreen() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        CreateRecipeFragment createRecipeFragment = CreateRecipeFragment.newInstance();
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, createRecipeFragment)
                .addToBackStack(CreateRecipeFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onNewRecipeAdded(Recipe recipe) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(HomeFragment.class.getName());
        homeFragment.onNewRecipeAdded(recipe);
    }
}