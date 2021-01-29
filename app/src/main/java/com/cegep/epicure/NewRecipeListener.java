package com.cegep.epicure;

import com.cegep.epicure.model.Recipe;

interface NewRecipeListener {

    void onNewRecipeAdded(Recipe recipe);
}
