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
public class Products implements Serializable {
    String res_id,title,title_ar,price,qut,stock,about,about_ar,description,description_ar,cart_price,option_title,option__title_ar;
    ArrayList<Images> images;
    Restaurants restaurant;
    ArrayList<Category> categories;
    ArrayList<Options> options;
    ArrayList<Groups> groups;
    Products(String res_id, String title, String title_ar,String stock,String price,String qut, String about, String about_ar, String description, String description_ar,
             String option_title, String option__title_ar, JSONArray jsonArray, JSONArray jsonArray1, JSONArray jsonArray2, JSONArray jsonArray3, JSONArray jsonArray4){
        this.res_id=res_id;
        this.title=title;
        this.title_ar=title_ar;
        this.stock=stock;
        this.price=price;
        this.qut=qut;
        this.cart_price=price;
        this.about=about;
        this.about_ar=about_ar;
        this.description=description;
        this.description_ar=description_ar;
        this.option_title=option_title;
        this.option__title_ar=option__title_ar;
        this.images = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.options = new ArrayList<>();
        this.groups = new ArrayList<>();
        for(int i = 0;i<jsonArray.length();i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Images temp = new Images(jsonObject.getString("image"),jsonObject.getString("thumb"));
                        this.images.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject tmp_json = null;

        try {
            tmp_json = jsonArray1.getJSONObject(0);
            Restaurants temp1 = new Restaurants(tmp_json.getString("id")
                    ,tmp_json.getString("title"),
                    tmp_json.getString("title_ar"),
                    tmp_json.getString("area"),
                    tmp_json.getString("current_status"),
                    tmp_json.getString("hours"),
                    tmp_json.getString("time"),
                    tmp_json.getString("minimum"),
                    tmp_json.getString("image"),
                    tmp_json.getString("banner"),
                    tmp_json.getString("description"),
                    tmp_json.getString("description_ar"),
                    tmp_json.getString("small_description"),
                    tmp_json.getString("small_description_ar"),
                    tmp_json.getString("rating"),
                    tmp_json.getString("reviews"),
                    tmp_json.getJSONArray("payment"),
                    tmp_json.getJSONArray("category"),
                    tmp_json.getJSONArray("menu"),
                    tmp_json.getJSONArray("promotions"),
                    tmp_json.getJSONArray("all_reviews"));
            this.restaurant = temp1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0;i<jsonArray2.length();i++){

            try {
                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                Category temp = new Category(jsonObject.getString("id"),jsonObject.getString("title"),jsonObject.getString("title_ar"));
                this.categories.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        for(int i = 0;i<jsonArray3.length();i++){

            try {
                JSONObject jsonObject = jsonArray3.getJSONObject(i);
                Options temp = new Options(jsonObject.getString("option"),jsonObject.getString("option_ar"),jsonObject.getString("price"),jsonObject.getString("option_id"));
                this.options.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        for(int i = 0;i<jsonArray4.length();i++){

            try {
                JSONObject jsonObject = jsonArray4.getJSONObject(i);
                Groups temp = new Groups(jsonObject.getString("group"),jsonObject.getString("group_ar"),jsonObject.getString("minimum"),jsonObject.getString("maximum"),jsonObject.getJSONArray("addons"));
                this.groups.add(temp);
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
        if(Settings.get_user_language(context).equals("ar"))
            return description_ar;
        else
            return  description;
    }
    public String getoption_title(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return option__title_ar;
        else
            return  option_title;
    }

    public class Images implements Serializable{
        String img,thumb;
        Images(String img, String thumb) {
            this.img = img;
            this.thumb = thumb;

        }

    }

    public class Category implements Serializable{
        String id,title, title_ar;

        Category(String id,String title, String title_ar) {
            this.id = id;
            this.title = title;
            this.title_ar = title_ar;
        }

        public String getTitle(Context context) {
            if (Settings.get_user_language(context).equals("ar"))
                return title_ar;
            else
                return title;
        }
    }
    public class Options implements Serializable{
        String option,option_ar,price,option_id;
        Boolean isselected = false;
        Options(String option_id,String option,String option_ar,String price){
            this.option_id=option_id;
            this.option=option;
            this.option_ar=option_ar;
            this.price=price;
        }
        public String get_option(Context context) {
            if (Settings.get_user_language(context).equals("ar"))
                return option_ar;
            else
                return option;
        }

    }
    public class Groups implements Serializable{
        ArrayList<Addons> addons;
        String group,group_ar,min,max;
        Groups(String group,String group_ar,String min, String max,JSONArray addon_json) {
            this.group = group;
            this.group_ar = group_ar;
            this.min = min;
            this.max = max;
            addons = new ArrayList<>();
            for(int i = 0;i<addon_json.length();i++){

                try {
                    JSONObject jsonObject = addon_json.getJSONObject(i);
                    Addons temp = new Addons(jsonObject.getString("addon_id"),jsonObject.getString("addon"),jsonObject.getString("addon_ar"),jsonObject.getString("price"));
                    this.addons.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
        public  String get_group(Context context) {
            if(Settings.get_user_language(context).equals("ar"))
                return group_ar;
            else
                return  group;
        }


             public class Addons implements Serializable{
                     String addon,addon_ar,price,addon_id;
                 Boolean isselected = false;
                    Addons(String addon_id,String addon,String addon_ar,String price){
                        this.addon_id=addon_id;
                        this.addon=addon;
                        this.addon_ar=addon_ar;
                        this.price=price;
                    }
                 public  String get_addon(Context context) {
                     if(Settings.get_user_language(context).equals("ar"))
                         return addon_ar;
                     else
                         return  addon;
                 }

             }
    }
}
