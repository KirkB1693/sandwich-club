package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mAlsoKnownTv;
    private TextView mDescriptionTv;
    private TextView mIngredientsTv;
    private TextView mOriginTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mOriginTv = findViewById(R.id.origin_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mAlsoKnownTv.setText("");
        List<String> akaList = sandwich.getAlsoKnownAs();
        for (String aka : akaList){
            if (aka.equals(akaList.get(0))) {
                mAlsoKnownTv.append(aka);
            } else {
                mAlsoKnownTv.append( ", " + aka);
            }

        }

        mDescriptionTv.setText(sandwich.getDescription());

        mOriginTv.setText(sandwich.getPlaceOfOrigin());

        mIngredientsTv.setText("");
        List<String> ingredientsList = sandwich.getIngredients();
        for (String ingredient : ingredientsList){
            if (ingredient.equals(ingredientsList.get(0))) {
                mIngredientsTv.append(ingredient);
            } else {
                mIngredientsTv.append( "\n" + ingredient);
            }
        }
    }
}
