package in.yellowsoft.darabeel;

/**
 * Created by Chinni on 16-05-2016.
 */

import android.content.Context;

public class Promotions implements java.io.Serializable{
    String title, title_ar,description,description_ar,pricee,image;

    Promotions(String title, String title_ar, String description, String description_ar, String pricee, String image) {
        this.title = title;
        this.title_ar = title_ar;
        this.description = description;
        this.description_ar = description_ar;
        this.pricee = pricee;
        this.image = image;
    }

    public String getTitle(Context context) {
        if (Settings.get_user_language(context).equals("ar"))
            return title_ar;
        else
            return title;
    }
    public String getDescription(Context context) {
        if (Settings.get_user_language(context).equals("ar"))
            return description_ar;
        else
            return description;
    }

}

