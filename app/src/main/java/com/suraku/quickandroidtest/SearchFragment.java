package com.suraku.quickandroidtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.suraku.quickandroidtest.helpers.RecipeHelper;
import com.suraku.quickandroidtest.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private BaseAdapter mRecipeListAdapter;
    private ArrayList<String> mListItems;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(KEY_LIST, mListItems);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mListItems = savedInstanceState.getStringArrayList(KEY_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        if (mListItems == null) {
            mListItems = new ArrayList<>();
        }

        RecipeListFragment recipeListFragment = (RecipeListFragment) getChildFragmentManager().findFragmentById(R.id.searchList);
        if (recipeListFragment == null ) {
            Log.d(TAG, "Error setting up SearchFragment.");
            return null;
        }

        mRecipeListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mListItems);
        recipeListFragment.setListAdapter(mRecipeListAdapter);

        Button button = (Button) rootView.findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListItems.clear();

                EditText searchEditText = (EditText) rootView.findViewById(R.id.searchText);
                String searchText = searchEditText.getText().toString().toLowerCase();

                List<Recipe> recipes = RecipeHelper.getInstance().getRecipes();
                for (Recipe recipe : recipes) {
                    if (recipe.getTitle().toLowerCase().contains(searchText)
                            || recipe.getIngredients().toLowerCase().contains(searchText)
                            || recipe.getInstruction().toLowerCase().contains(searchText)) {
                        mListItems.add(recipe.getTitle());
                    }
                }
                mRecipeListAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    private String TAG = "SEARCH_FRAGMENT";
    private String KEY_LIST = "KEY_LIST";
}
