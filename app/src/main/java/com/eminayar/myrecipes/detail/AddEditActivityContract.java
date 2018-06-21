package com.eminayar.myrecipes.detail;

import com.eminayar.myrecipes.base.BaseContract;
import com.eminayar.myrecipes.models.Recipe;

import java.util.ArrayList;

public interface AddEditActivityContract extends BaseContract {

    interface View extends BaseContract.View {

        void onRecipeFound(Recipe recipe);

        void onRecipeSaved();

        void onRecipeDeleted();

        void onError();
    }

    interface Presenter<V extends View> {

        void findRecipe(long recipeId);

        void saveOrUpdateRecipe(int recipeId, String name, String description, ArrayList<String> ings);

        void deleteRecipe(long recipeId);
    }
}