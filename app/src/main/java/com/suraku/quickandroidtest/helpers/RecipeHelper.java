package com.suraku.quickandroidtest.helpers;

import com.suraku.quickandroidtest.models.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeHelper {

    private static RecipeHelper mInstance;

    public static RecipeHelper getInstance() {
        if (mInstance == null) {
            mInstance = new RecipeHelper();
        }
        return mInstance;
    }

    private RecipeHelper(){
        mRecipes = Arrays.asList(
                new Recipe() {{
                    setTitle("Chicken");
                    setIngredients("");
                    setInstruction("");
                }},
                new Recipe() {{
                    setTitle("Steak");
                    setIngredients("");
                    setInstruction("");
                }},
                new Recipe() {{
                    setTitle("Salad");
                    setIngredients("");
                    setInstruction("");
                }}
        );
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public List<String> getRecipeTitles() {
        ArrayList<String> ret = new ArrayList<>();
        for (Recipe recipe : getRecipes()) {
            ret.add(recipe.getTitle());
        }
        return ret;
    }

    private List<Recipe> mRecipes;
}
