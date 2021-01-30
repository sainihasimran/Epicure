package com.cegep.epicure.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
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

    @SerializedName("Steps")
    private List<PreparationStep> preparationSteps;

    public Recipe() {

    }

    public String getImage() {
        if (TextUtils.isEmpty((image))) {
            return "https://firebasestorage.googleapis.com/v0/b/epicure-9f032.appspot.com/o/17KITCHEN1-articleLarge.jpg?alt=media&token=b1d1121e-95f1-4d52-ba6b-693a8a9ebe1a";
        }

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

    private Recipe(Parcel in) {
        image = in.readString();
        name = in.readString();
        servingSize = in.readInt();
        category = in.readString();
        duration = in.readInt();
        calories = in.readInt();
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
        dest.writeInt(calories);
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
