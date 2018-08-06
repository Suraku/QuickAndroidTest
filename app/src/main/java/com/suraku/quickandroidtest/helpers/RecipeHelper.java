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
                    setTitle("Porridge");
                    setIngredients("Oats, Milk, Golden Syrup");
                    setInstruction("Mix oats and milk in bowl. Microwave for 1 and a half minutes. Add light serving of Golden Syrup on top.");
                }},
                new Recipe() {{
                    setTitle("Salad");
                    setIngredients("Cheese and onion quiche, Ham, Tomatoes, Lettuce, Cheese, Beetroot, Pasta");
                    setInstruction("Oven cook quiche. Slice tomatoes etc. Add to plate.");
                }},
                new Recipe() {{
                    setTitle("Baked Potato");
                    setIngredients("Potato, Cheshire Cheese, Baked Beans, Bacon");
                    setInstruction("Oven cook potato. Slice into quarters and add grated cheese on top, followed by baked beans.");
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
