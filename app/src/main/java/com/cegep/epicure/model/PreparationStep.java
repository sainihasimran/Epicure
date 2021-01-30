package com.cegep.epicure.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PreparationStep implements Parcelable  {

    public int StepNumber;

    public int RecipeId;

    public String StepDescription;

    public PreparationStep(int stepNumber, int recipeId, String stepDescription) {
        StepNumber = stepNumber;
        RecipeId = recipeId;
        StepDescription = stepDescription;
    }

    public PreparationStep(Parcel in) {
        StepNumber = in.readInt();
        RecipeId = in.readInt();
        StepDescription = in.readString();
    }

    public static final Parcelable.Creator<PreparationStep> CREATOR
            = new Parcelable.Creator<PreparationStep>() {
        public PreparationStep createFromParcel(Parcel in) {
            return new PreparationStep(in);
        }

        public PreparationStep[] newArray(int size) {
            return new PreparationStep[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(StepNumber);
        dest.writeInt(RecipeId);
        dest.writeString(StepDescription);
    }
}
