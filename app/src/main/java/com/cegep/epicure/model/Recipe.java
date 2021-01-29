package com.cegep.epicure.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Recipe {

    public int RecipeId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Image")
    private String image;

    @SerializedName("ServingSize")
    private int servingSize;

    @SerializedName("Category")
    private String category;

    @SerializedName("Duration")
    private int duration;

    @SerializedName("Calories")
    private int calories;

    @SerializedName("Ingredients")
    private List<String> ingredients;

    @SerializedName("PreparationSteps")
    private List<String> preparationSteps;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(List<String> preparationSteps) {
        this.preparationSteps = preparationSteps;
    }
}
