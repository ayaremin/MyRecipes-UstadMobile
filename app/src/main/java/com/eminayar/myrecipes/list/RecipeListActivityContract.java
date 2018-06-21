package com.eminayar.myrecipes.list;

import com.eminayar.myrecipes.base.BaseContract;
import com.eminayar.myrecipes.models.Recipe;

import io.realm.RealmResults;

public interface RecipeListActivityContract extends BaseContract {

    interface View extends BaseContract.View {

        void showNoResultView();

        void showRecipes(RealmResults<Recipe> recipes);

        void hideRecipes();

        void hideNoResultView();
    }

    interface Presenter<V extends View> {

        void getAllRecipes();

    }
}