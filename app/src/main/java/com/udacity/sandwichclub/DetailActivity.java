package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.image_iv) ImageView imageIv;
    @BindView(R.id.also_known_tv) TextView alsoKwonAsTv;
    @BindView(R.id.ingredients_tv) TextView ingredientsTv;
    @BindView(R.id.origin_tv) TextView placeOfOriginTv;
    @BindView(R.id.description_tv) TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        alsoKwonAsTv.setText(joinString(sandwich.getAlsoKnownAs(), ","));
        ingredientsTv.setText(joinString(sandwich.getIngredients(), "\n"));
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private String joinString(List<String> listOfString, String conjuction) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for(String word : listOfString){
            if(isFirst)
                isFirst = false;
            else
                stringBuilder.append(conjuction);
            stringBuilder.append(word);
        }
        return stringBuilder.toString();
    }

    private void closeOnError() {
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[0];
        Log.d(TAG,json );
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
