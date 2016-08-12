package in.yellowsoft.darabeel;

import android.content.Context;

/**
 * Created by Chinni on 26-05-2016.
 */
public class Addresss {
    String id,alias,name,phone,block,street,house,floor,flat,area_id,area_title,area_title_ar;
    Addresss(String id, String alias, String name, String phone, String area_id, String area_title,
             String area_title_ar, String block, String street, String house, String floor, String flat){
        this.id=id;
        this.alias=alias;
        this.name=name;
        this.phone=phone;
        this.area_id=area_id;
        this.area_title=area_title;
        this.area_title_ar=area_title_ar;
        this.block=block;
        this.street=street;
        this.house=house;
        this.floor=floor;
        this.flat=flat;

    }
    public  String getTitle(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return area_title_ar;
        else
            return  area_title;
    }
}
