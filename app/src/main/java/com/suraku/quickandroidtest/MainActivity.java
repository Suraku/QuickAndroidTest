package com.suraku.quickandroidtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.suraku.quickandroidtest.helpers.RecipeHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int mRecipeFragmentId;
    private int mSearchFragmentId;
    private int mActiveFragmentId = -1;

    private RecipeListFragment mRecipeListFragment;
    private SearchFragment mSearchFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return switchFragment(mRecipeListFragment);
                case R.id.navigation_dashboard:
                    return switchFragment(mSearchFragment);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve persistent data for this activity and any fragments it has.
        ArrayList<String> searchListItems = null;
        String searchText = "";

        if (savedInstanceState != null) {
            mRecipeFragmentId = savedInstanceState.getInt(KEY_RECIPE_LIST_FRAGMENT_ID, -1);
            mSearchFragmentId = savedInstanceState.getInt(KEY_SEARCH_FRAGMENT_ID, -1);
            mActiveFragmentId = savedInstanceState.getInt(KEY_ACTIVE_FRAGMENT, -1);
            searchListItems = savedInstanceState.getStringArrayList(SearchFragment.KEY_LIST);
            searchText = savedInstanceState.getString(SearchFragment.KEY_SEARCH_TEXT);
        }

        // Initialize listeners
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Initialize fragments
        FragmentManager manager = getSupportFragmentManager();
        mRecipeListFragment = (RecipeListFragment) manager.findFragmentById(mRecipeFragmentId);
        mSearchFragment = (SearchFragment) manager.findFragmentById(mSearchFragmentId);

        if (mRecipeListFragment == null) {
            mRecipeListFragment = new RecipeListFragment();
            mRecipeFragmentId = mRecipeListFragment.getId();
        }
        if (mSearchFragment == null) {
            mSearchFragment = new SearchFragment();
            mSearchFragment.setListItems(searchListItems);
            mSearchFragment.setSearchText(searchText);
            mSearchFragmentId = mSearchFragment.getId();
        }

        // Initialize recipe list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, RecipeHelper.getInstance().getRecipeTitles());
        mRecipeListFragment.setListAdapter(arrayAdapter);


        // Display the previous/default fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mActiveFragmentId > -1 && mActiveFragmentId == mSearchFragment.getId()) {
            transaction.replace(R.id.fragmentContainer, mSearchFragment);
            mActiveFragmentId = mSearchFragment.getId();
        } else {
            transaction.replace(R.id.fragmentContainer, mRecipeListFragment);
            mActiveFragmentId = mRecipeListFragment.getId();
        }
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_ACTIVE_FRAGMENT, mActiveFragmentId);
        outState.putInt(KEY_RECIPE_LIST_FRAGMENT_ID, mRecipeListFragment.getId());
        outState.putInt(KEY_SEARCH_FRAGMENT_ID, mSearchFragment.getId());
        mSearchFragment.onSaveInstanceState(outState);
    }

    /**
     * Switches the displayed main content view.
     * @param fragment
     * @return
     */
    private boolean switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();

        mActiveFragmentId = fragment.getId();
        return true;
    }

    private final String KEY_ACTIVE_FRAGMENT = "KEY_ACTIVE_FRAGMENT";
    private final String KEY_RECIPE_LIST_FRAGMENT_ID = "KEY_RECIPE_LIST_FRAGMENT_ID";
    private final String KEY_SEARCH_FRAGMENT_ID = "KEY_SEARCH_FRAGMENT_ID";
}
