package com.suraku.quickandroidtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.suraku.quickandroidtest.helpers.RecipeHelper;

public class MainActivity extends AppCompatActivity {

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

        // Initialize fragments
        mRecipeListFragment = new RecipeListFragment();
        mSearchFragment = new SearchFragment();

        // Initialize recipe list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, RecipeHelper.getInstance().getRecipeTitles());
        mRecipeListFragment.setListAdapter(arrayAdapter);

        // Display the default fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, mRecipeListFragment);
        transaction.commit();

        // Initialize listeners
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
        return true;
    }
}
