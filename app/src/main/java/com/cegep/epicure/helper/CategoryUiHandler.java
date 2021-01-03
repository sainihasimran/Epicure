package com.cegep.epicure.helper;

import android.view.View;
import com.cegep.epicure.R;
import com.cegep.epicure.model.Category;

public class CategoryUiHandler {

    private View selectedCategoryView;

    private final CategorySelectedListener categorySelectedListener;

    public CategoryUiHandler(View view, CategorySelectedListener categorySelectedListener) {
        View vegetarianView = view.findViewById(R.id.vege_view);
        View nonVegetarianView = view.findViewById(R.id.meat_view);
        View veganView = view.findViewById(R.id.vegan_view);
        View drinksView = view.findViewById(R.id.drinks_view);

        setClickListener(vegetarianView, Category.VEGETARIAN);
        setClickListener(nonVegetarianView, Category.NON_VEGETARIAN);
        setClickListener(veganView, Category.VEGAN);
        setClickListener(drinksView, Category.DRINKS);

        setSelected(vegetarianView);

        this.categorySelectedListener = categorySelectedListener;
    }

    private void setClickListener(View view, Category category) {
        view.setOnClickListener(v -> {
            setSelected(view);
            categorySelectedListener.onCategorySelected(category);
        });
    }

    private void setSelected(View view) {
        if (selectedCategoryView != null) {
            selectedCategoryView.setSelected(false);
        }
        selectedCategoryView = view;
        selectedCategoryView.setSelected(true);
    }

    public interface CategorySelectedListener {

        void onCategorySelected(Category category);
    }
}
