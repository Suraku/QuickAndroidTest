package com.suraku.quickandroidtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class RecipeListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listfragment_recipes, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1, Recipes.getInstance().getRecipes());
        setListAdapter(arrayAdapter);
    }
}
