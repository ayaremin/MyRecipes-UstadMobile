package com.eminayar.myrecipes.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.eminayar.myrecipes.R;
import com.eminayar.myrecipes.base.BaseActivity;
import com.eminayar.myrecipes.detail.AddEditActivity;
import com.eminayar.myrecipes.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.realm.RealmResults;

public class RecipeListActivity extends BaseActivity implements RecipeListActivityContract.View {

    @BindView(R.id.recipes_list_view)
    ListView recipesListView;
    @BindView(R.id.add_new_recipe_button)
    Button addNewRecipeButton;
    @BindView(R.id.no_result_linear_layout)
    LinearLayout noResultLinearLayout;

    private RecipeListActivityPresenter presenter;

    RecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        presenter = new RecipeListActivityPresenter();
        presenter.attachView(this);
    }

    @Override
    public void setUI() {

    }


    @OnClick(R.id.add_new_recipe_button)
    public void onNewRecipeClicked() {
        startActivity(AddEditActivity.createIntent(this, -1));
    }

    @Override
    public void showNoResultView() {
        noResultLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecipes(RealmResults<Recipe> recipes) {
        recipesListView.setVisibility(View.VISIBLE);
        adapter = new RecipeListAdapter(recipes);
        recipesListView.setAdapter(adapter);
    }

    @Override
    public void hideRecipes() {
        recipesListView.setVisibility(View.GONE);
    }

    @Override
    public void hideNoResultView() {
        noResultLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_recipe) {
            // if we click plus button in toolbar it will always try to create new recipe so recipe id will be -1
            startActivity(AddEditActivity.createIntent(this, -1));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //refresh adapter if we are comning from any other screen to make list updated
        presenter.getAllRecipes();
    }

    @OnItemClick(R.id.recipes_list_view)
    public void onRecipeClicked(int pos) {
        startActivity(AddEditActivity.createIntent(this, adapter.getId(pos)));
    }
}