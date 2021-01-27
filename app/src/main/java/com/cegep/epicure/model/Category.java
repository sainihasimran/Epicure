package com.cegep.epicure.model;

public enum Category {

    VEGETARIAN, NON_VEGETARIAN, VEGAN, DRINKS;

    public String getNiceName() {
        return this.toString().charAt(0) + this.toString().substring(1).toLowerCase().replace("_", " ");
    }
}
