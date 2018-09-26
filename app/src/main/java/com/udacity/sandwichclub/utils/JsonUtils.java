package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        if (json != null && !json.equals("")) {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject sandwichNameObject = sandwichObject.getJSONObject("name");
            String mainName = sandwichNameObject.getString("mainName");
            JSONArray akaNamesArray = sandwichNameObject.getJSONArray("alsoKnownAs");
            List<String> akaNames = new ArrayList<String>();
            if (akaNamesArray.length() != 0) {
                for (int i = 0; i < akaNamesArray.length(); i++) {
                    akaNames.add(akaNamesArray.getString(i));
                }
            }
            String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            String description = sandwichObject.getString("description");
            String imageURL = sandwichObject.getString("image");
            JSONArray ingredientsArray = sandwichObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            if (ingredientsArray.length() != 0) {
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredients.add(ingredientsArray.getString(i));
                }
            }
            Sandwich sandwich = new Sandwich(mainName, akaNames, placeOfOrigin, description, imageURL, ingredients);
            return sandwich;
        }
        return null;
    }
}
