package in.yellowsoft.darabeel;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by sriven on 5/6/2016.
 */
public class Category implements Serializable{
    String title,title_ar,id;
    Category(String title, String title_ar, String id){
        this.title=title;
        this.title_ar=title_ar;
        this.id = id;
    }

    public String getTitle(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return title_ar;
        else
            return  title;
    }

}
