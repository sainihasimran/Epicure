package com.cegep.epicure.model;

public class PreparationStep {

    public int StepNumber;

    public int RecipeId;

    public String StepDescription;

    public PreparationStep(int stepNumber, int recipeId, String stepDescription) {
        StepNumber = stepNumber;
        RecipeId = recipeId;
        StepDescription = stepDescription;
    }
}
