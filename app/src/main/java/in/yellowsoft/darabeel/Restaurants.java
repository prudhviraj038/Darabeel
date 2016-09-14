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
public class Restaurants implements Serializable{
    String res_id,title,title_ar,area,status,hours,time,min,image,description,description_ar,sdescription,sdescription_ar,rating,reviews,banner;
    ArrayList<Payment> payment;
    ArrayList<Category> category;
    ArrayList<Menu> menu;
   ArrayList<Promotions> promotions;
    ArrayList<AllReviws> allReviwses;
    Restaurants(String res_id, String title, String title_ar, String area, String status, String hours, String time, String min, String image, String banner,
                String description, String description_ar, String sdescription, String sdescription_ar, String rating, String reviews, JSONArray jsonArray, JSONArray jsonArray1, JSONArray jsonArray2, JSONArray jsonArray3,JSONArray jsonArray4) {
        this.res_id = res_id;
        this.title = title;
        this.title_ar = title_ar;
        this.area = area;
        this.status = status;
        this.hours = hours;
        this.time = time;
        this.min = min;
        this.image = image;
        this.banner = banner;
        this.description = description;
        this.description_ar = description_ar;
        this.sdescription = sdescription;
        this.sdescription_ar = sdescription_ar;
        this.rating = rating;
        this.reviews = reviews;
        this.allReviwses = new ArrayList<>();
        this.payment = new ArrayList<>();
        this.category = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.promotions = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Payment temp = new Payment(jsonObject.getString("id"), jsonObject.getString("image"), jsonObject.getString("title"), jsonObject.getString("title_ar"));
                this.payment.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < jsonArray1.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                Category temp = new Category(jsonObject.getString("title"), jsonObject.getString("title_ar"));
                this.category.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // Menu tempf = new Menu("0","All","جميع");
        //this.menu.add(tempf);

        for (int i = 0; i < jsonArray2.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                Menu temp = new Menu(jsonObject.getString("id"), jsonObject.getString("title"), jsonObject.getString("title_ar"));
                this.menu.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        JSONObject tmp_json = null;
        for (int i = 0; i < jsonArray3.length(); i++) {
            try {
                tmp_json = jsonArray3.getJSONObject(i);
                Promotions temp1 = new Promotions(tmp_json.getString("title"),
                        tmp_json.getString("title_ar"), tmp_json.getString("description"),
                        tmp_json.getString("description_ar"),
                        tmp_json.getString("price"),
                        tmp_json.getString("image"));
                this.promotions.add(temp1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < jsonArray4.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray4.getJSONObject(i);
                AllReviws temp = new AllReviws(jsonObject);
                this.allReviwses.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public String getTitle(Context context) {
        if (Settings.get_user_language(context).equals("ar"))
            return title_ar;
        else
            return title;
    }

    public String getdescription(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return description_ar;
        else
            return  description;
    }
    public String getsdescription(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return sdescription_ar;
        else
            return  sdescription;
    }

    public class Payment implements Serializable{
        String title, title_ar, image,id;
        Payment(String id,String image,String title, String title_ar ) {
            this.id=id;
            this.title = title;
            this.title_ar = title_ar;
            this.image = image;
        }

        public String getTitle(Context context) {
            if(Settings.get_user_language(context).equals("ar"))
                return title_ar;
            else
                return  title;
        }
    }
     public class Category implements Serializable{
         String title,title_ar;
         Category(String title,String title_ar){
             this.title=title;
             this.title_ar=title_ar;
         }

         public String getTitle(Context context) {
             if(Settings.get_user_language(context).equals("ar"))
                 return title_ar;
             else
                 return  title;
         }
     }
    public class Menu implements Serializable{
        String id,title, title_ar;

        Menu(String id,String title, String title_ar) {
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



    public class AllReviws implements Serializable{
        String m_id,namee,ratingg,review,date;

        AllReviws(JSONObject jsonObject) {
            try {
                m_id=jsonObject.getJSONObject("member").getString("id");
                namee=jsonObject.getJSONObject("member").getString("name");
                ratingg=jsonObject.getString("rating");
                review=jsonObject.getString("review");
                date=jsonObject.getString("date");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
