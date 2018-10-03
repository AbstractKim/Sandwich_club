package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";



    public static Sandwich parseSandwichJson(String json) {
        //1. create JSONObject from source(json string)
        JSONObject sourceJsonObject = null;
        try {
            sourceJsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //if input string violates JSON format, just return null object
        if(sourceJsonObject == null)
            return null;

        //2. get JSON object of "name", and extracts mainName as String and alsoKnownAs as array
        JSONObject nameJsonObject = null;
        String mainName = null;
        List<String> alsoKnownAs = new ArrayList<>();
        try {
            nameJsonObject = sourceJsonObject.getJSONObject("name");
            //2.1 extract "mainName" as String
            mainName = nameJsonObject.getString(KEY_MAIN_NAME);
            //2.2 extract "alsoKnownAs" as Array
            JSONArray alsoKnownAsJSONArray = nameJsonObject.getJSONArray(KEY_ALSO_KNOW_AS);
            int lengthOfAlsoKnownAsJSONArray = alsoKnownAsJSONArray.length();
            if(lengthOfAlsoKnownAsJSONArray > 0){
                for(int i = 0; i < lengthOfAlsoKnownAsJSONArray; i++){
                    alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //3.get String of "placeOfOrigin"
        String placeOfOrigin = null;
        try {
            placeOfOrigin = sourceJsonObject.getString(KEY_PLACE_OF_ORIGIN);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //4.get String of "description"
        String description = null;
        try {
            description = sourceJsonObject.getString(KEY_DESCRIPTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //5.get String of image (source link)
        String image = null;
        try {
            image = sourceJsonObject.getString(KEY_IMAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //6.get Array of ingredients
        List<String> ingredients = new ArrayList<>();

        try {
            JSONArray ingredientsJSONArray = sourceJsonObject.getJSONArray(KEY_INGREDIENTS);
            int lengthOfingredientsJSONArray = ingredientsJSONArray.length();
            if(lengthOfingredientsJSONArray >0) {
                for(int i = 0; i < lengthOfingredientsJSONArray; i++){
                    ingredients.add(ingredientsJSONArray.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
