package com.suraku.quickandroidtest;

import java.util.Arrays;
import java.util.List;

public class Recipes {

    private static Recipes mInstance;

    public static Recipes getInstance() {
        if (mInstance == null) {
            mInstance = new Recipes();
        }
        return mInstance;
    }

    private Recipes(){
        mRecipes = Arrays.asList("Chicken", "Steak", "Salad");
    }

    public List<String> getRecipes() {
        return mRecipes;
    }

    private List<String> mRecipes;
}
