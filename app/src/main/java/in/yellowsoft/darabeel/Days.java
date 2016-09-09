package in.yellowsoft.darabeel;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HP on 08-Sep-16.
 */
public class Days {
    String id,title,title_ar,str;
    Days(JSONObject jsonObject){
        try {
            id=jsonObject.getString("id");
            title=jsonObject.getString("title");
            title_ar=jsonObject.getString("title_ar");
            str=jsonObject.getString("string");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public  String getTitle(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return title_ar;
        else
            return  title;
    }
}
