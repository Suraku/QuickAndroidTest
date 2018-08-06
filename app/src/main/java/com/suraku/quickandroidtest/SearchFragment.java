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
    private View mRootView;
    private ArrayList<String> mListItems;
    private String mSearchText;

    /**
     * Allow the activity to populate the list as we can lose the bundle state when this isn't
     * the currently active content fragment upon screen rotate.
     * @param val
     */
    public void setListItems(ArrayList<String> val) {
        mListItems = val;
    }

    public void setSearchText(String val) {
        mSearchText = val;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(KEY_LIST, mListItems);

        // Prefer the most up-to-date text, otherwise fallback on last previously known.
        if (mRootView != null) {
            outState.putString(KEY_SEARCH_TEXT, getSearchText());
        } else {
            outState.putString(KEY_SEARCH_TEXT, mSearchText);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mListItems = savedInstanceState.getStringArrayList(KEY_LIST);
            mSearchText = savedInstanceState.getString(KEY_SEARCH_TEXT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_search, container, false);

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

        // Prepare on-click search button listener
        final Button button = (Button) mRootView.findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListItems.clear();

                String searchText = getSearchText().toLowerCase();

                // Filter recipes by the search term entered by the user.
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

        // Set any previously entered search text so the results would make sense.
        EditText searchEditText = (EditText) mRootView.findViewById(R.id.searchText);
        searchEditText.setText(mSearchText);

        return mRootView;
    }

    private String getSearchText() {
        EditText searchEditText = (EditText) mRootView.findViewById(R.id.searchText);
        return searchEditText.getText().toString();
    }

    private String TAG = "SEARCH_FRAGMENT";
    public static String KEY_LIST = "KEY_LIST";
    public static String KEY_SEARCH_TEXT = "KEY_SEARCH_TEXT";
}
