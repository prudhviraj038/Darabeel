package in.yellowsoft.darabeel;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chinni on 05-05-2016.
 */
public class PromotionList implements Serializable {
    String title,title_ar,price,description,description_ar,image;
    ArrayList<Rest> rest;
    PromotionList(String title, String title_ar,String description, String description_ar, String price,String image,
                  JSONArray jsonArray){
        this.title=title;
        this.title_ar=title_ar;
        this.description=description;
        this.description_ar=description_ar;
        this.price=price;
        this.image=image;
        this.rest = new ArrayList<>();
        for(int i = 0;i<jsonArray.length();i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Rest temp = new Rest(jsonObject);
                        this.rest.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public  String getTitle(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return title_ar;
        else
            return  title;
    }

    public String getdescription(Context context) {
        if (Settings.get_user_language(context).equals("ar"))
            return description_ar;
        else
            return description;
    }

    public class Rest implements Serializable{
        String id,title,title_ar,rating,image;
        Rest(JSONObject jsonObject) {
            try {
                id=jsonObject.getString("id");
                title=jsonObject.getString("title");
                title_ar=jsonObject.getString("title_ar");
                image=jsonObject.getString("image");
//                rating=jsonObject.getString("rating");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
