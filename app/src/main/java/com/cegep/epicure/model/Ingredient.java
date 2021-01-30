package com.cegep.epicure.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    public int RecipeId;

    public String Name;

    public Ingredient(int recipeId, String name) {
        RecipeId = recipeId;
        Name = name;
    }

    private Ingredient(Parcel in) {
        in.writeInt(RecipeId);
        in.writeString(Name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        RecipeId = dest.readInt();
        Name = dest.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
