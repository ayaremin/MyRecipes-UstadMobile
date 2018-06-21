package com.eminayar.myrecipes.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eminayar.myrecipes.R;
import com.eminayar.myrecipes.base.BaseActivity;
import com.eminayar.myrecipes.base.Constants;
import com.eminayar.myrecipes.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditActivity extends BaseActivity implements AddEditActivityContract.View {

    AddEditActivityPresenter presenter;
    @BindView(R.id.name_edit_text)
    TextInputEditText nameEditText;
    @BindView(R.id.description_edit_text)
    TextInputEditText descriptionEditText;
    @BindView(R.id.ingredients_text_view)
    TextView ingredientsTextView;
    @BindView(R.id.delete_button)
    Button deleteButton;
    @BindView(R.id.save_button)
    Button saveButton;

    private int recipeId;
    private Recipe recipe;
    private ArrayList<String> ingredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        ButterKnife.bind(this);

        //handle incoming data from bundle, -1 for no recipe so it will create new record
        recipeId = getIntent().getIntExtra(Constants.RECIPE_ID, -1);

        presenter = new AddEditActivityPresenter();
        presenter.attachView(this);
    }

    @Override
    public void setUI() {
        if (recipeId == -1) {
            // There is no recipe then hide delete button
            deleteButton.setVisibility(View.GONE);
        } else {
            presenter.findRecipe(recipeId);
        }
    }

    public static Intent createIntent(Context ctx, int recipeId) {
        Intent i = new Intent(ctx, AddEditActivity.class);
        i.putExtra(Constants.RECIPE_ID, recipeId);
        return i;
    }

    @Override
    public void onRecipeFound(Recipe recipe) {
        //Fill data with found recipe
        deleteButton.setVisibility(View.VISIBLE);
        nameEditText.setText(recipe.getName());
        descriptionEditText.setText(recipe.getDescription());
        ingredientsTextView.setText(recipe.getIngredientsAsString());
    }

    @Override
    public void onRecipeSaved() {
        Toast.makeText(this, getString(R.string.message_recipe_saved), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onRecipeDeleted() {
        Toast.makeText(this, getString(R.string.label_recipe_deleted), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onError() {
        Toast.makeText(this, getString(R.string.error_fill_all_areas), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.ingredients_text_view)
    public void onIngredientsTextViewClicked() {
        final String[] ings = new String[]{"Potato", "Tomato", "Apple", "Banana", "Orange", "Onion"};
        new AlertDialog.Builder(this)
                .setTitle(R.string.label_select_ingredients)
                .setMultiChoiceItems(ings, new boolean[]{false, false, false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            ingredients.add(ings[i]);
                        } else {
                            ingredients.remove(ings[i]);
                        }
                    }
                })
                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ingredientsTextView.setText(getIngredientsAsString(ingredients));
                    }
                })
                .show();
    }

    @OnClick(R.id.delete_button)
    public void onDeleteButtonClicked() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.label_delete_recipe)
                .setMessage(R.string.message_delete_recipe)
                .setPositiveButton(R.string.label_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.deleteRecipe(recipeId);
                    }
                })
                .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    @OnClick(R.id.save_button)
    public void onSaveButtonClicked() {
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        ArrayList<String> ings = ingredients;

        presenter.saveOrUpdateRecipe(recipeId, name, description, ings);
    }

    private  String getIngredientsAsString(ArrayList<String> ingredients) {
        StringBuilder sb = new StringBuilder();
        for (String s : ingredients) {
            sb.append(s);
            sb.append(", ");
        }
        return sb.toString();
    }

}