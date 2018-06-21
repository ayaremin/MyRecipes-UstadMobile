package com.eminayar.myrecipes.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.eminayar.myrecipes.R;
import com.eminayar.myrecipes.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;


class RecipeListAdapter extends RealmBaseAdapter<Recipe> implements ListAdapter {
    Realm realm;

    RecipeListAdapter(OrderedRealmCollection<Recipe> realmResults) {
        super(realmResults);
        realm = Realm.getDefaultInstance();
    }

    public int getId (int pos) {
        return adapterData.get(pos).getRecipeId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_recipe, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            final Recipe recipe = adapterData.get(position);
            viewHolder.recipeNameTextView.setText(recipe.getName());
            viewHolder.ingredientsTextView.setText(recipe.getIngredientsAsString());
            viewHolder.favouriteImageView.setImageResource(recipe.isFavourite() ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);

            viewHolder.favouriteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    realm.beginTransaction();
                    if (recipe.isFavourite()) {
                        recipe.setFavourite(false);
                    } else {
                        recipe.setFavourite(true);
                    }
                    realm.commitTransaction();
                }
            });
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.recipe_name_text_view)
        TextView recipeNameTextView;
        @BindView(R.id.ingredients_text_view)
        TextView ingredientsTextView;
        @BindView(R.id.favourite_image_view)
        ImageView favouriteImageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}