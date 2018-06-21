package com.eminayar.myrecipes.list;

import com.eminayar.myrecipes.base.BasePresenter;
import com.eminayar.myrecipes.models.Recipe;

import io.realm.Realm;
import io.realm.RealmResults;

public class RecipeListActivityPresenter extends BasePresenter<RecipeListActivityContract.View> implements RecipeListActivityContract.Presenter {

    Realm realm;
    RealmResults<Recipe> recipes;

    public RecipeListActivityPresenter() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void attachView(RecipeListActivityContract.View view) {
        super.attachView(view);

        //Query Realm if we have any recipe in our db or not
        getAllRecipes();
    }

    @Override
    public void getAllRecipes() {
        recipes = realm.where(Recipe.class).findAll();
        //Here configure UI for initialization
        if (recipes.isEmpty()) {
            getView().hideRecipes();
            getView().showNoResultView ();
        } else {
            getView().showRecipes(recipes);
            getView().hideNoResultView();
        }
    }
}