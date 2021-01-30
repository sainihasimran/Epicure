package com.cegep.epicure.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Recipe implements Parcelable {

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
    private List<Ingredient> ingredients;

    @SerializedName("PreparationSteps")
    private List<PreparationStep> preparationSteps;

    public Recipe() {

    }

    private Recipe(Parcel in) {
        image = in.readString();
        name = in.readString();
        servingSize = in.readInt();
        category = in.readString();
        duration = in.readInt();
        calories = in.readInt();
        ingredients = in.readArrayList(null);
        preparationSteps = in.readArrayList(null);
    }

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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<PreparationStep> getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(List<PreparationStep> preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeInt(servingSize);
        dest.writeString(category);
        dest.writeInt(duration);
        dest.writeInt((calories));
        dest.writeList(ingredients);
        dest.writeList(preparationSteps);
    }

    public static final Parcelable.Creator<Recipe> CREATOR
            = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
