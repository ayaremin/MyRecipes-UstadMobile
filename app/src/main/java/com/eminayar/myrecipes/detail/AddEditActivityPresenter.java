package com.eminayar.myrecipes.detail;

import android.text.TextUtils;

import com.eminayar.myrecipes.base.BasePresenter;
import com.eminayar.myrecipes.models.Recipe;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AddEditActivityPresenter extends BasePresenter<AddEditActivityContract.View> implements AddEditActivityContract.Presenter {

    Realm realm;

    public AddEditActivityPresenter() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void findRecipe(long recipeId) {
        Recipe recipe = realm.where(Recipe.class).equalTo("recipeId", recipeId).findFirst();
        getView().onRecipeFound(recipe);
    }

    @Override
    public void deleteRecipe(final long recipeId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Recipe> result = realm.where(Recipe.class).equalTo("recipeId", recipeId).findAll();
                result.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getView().onRecipeDeleted();
            }
        });
    }

    @Override
    public void saveOrUpdateRecipe(int recipeId, String name, String description, ArrayList ings) {
        //Fill ingredients to realm list, cause Realm doesnt accept ArrayList or List
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || ings.isEmpty()) {
            getView().onError();
            return;
        }
        RealmList<String> ingredients = new RealmList<>();
        ingredients.addAll(ings);

        Recipe recipe = new Recipe(name, description, new Date().getTime(), ingredients);
        if (recipeId != -1) {
            //it means we are updating a recipe
            recipe.setId(recipeId);
        } else {
            recipe.setId(new Random().nextInt(100000));
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recipe);
        realm.commitTransaction();

        getView().onRecipeSaved();
    }
}