package com.cegep.epicure;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainNavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commit();
    }

    @Override
    public void navigateToCreateRecipeScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CreateRecipeFragment.newInstance())
                .addToBackStack(CreateRecipeFragment.class.getSimpleName())
                .commit();
    }
}