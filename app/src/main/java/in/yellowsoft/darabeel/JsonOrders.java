package in.yellowsoft.darabeel;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 27-05-2016.
 */
public class JsonOrders implements java.io.Serializable {
    String id,fname,lname,home_ph,work_ph,area_id,area_title,area_title_ar,block,street,building,flor,flat,phone,
            comments,coupon_code,dis_amount,pricee,del_charges,tot_price,payment_method,res_id,res_title,res_title_ar,res_image,
            deli_date,deli_time,pay_status,deli_status,date,ratingg,review,dara,reward;
    ArrayList<Product> product;
    JsonOrders(JSONObject order){
        product=new ArrayList<>();
        try {
            id=order.getString("id");
            fname=order.getString("first_name");
            lname=order.getString("last_name");
            home_ph=order.getString("home_phone");
            work_ph=order.getString("work_phone");
            JSONObject json_area=order.getJSONObject("area");
                area_id=json_area.getString("id");
                area_title=json_area.getString("title");
                area_title_ar=json_area.getString("title_ar");
            block=order.getString("block");
            street=order.getString("street");
            building=order.getString("building");
            flor=order.getString("floor");
            flat=order.getString("flat");
            phone=order.getString("phone");
            comments=order.getString("comments");
            coupon_code=order.getString("coupon_code");
            dis_amount=order.getString("discount_amount");
            dara=order.getString("darabeel_charges");
            reward = order.getString("reward_amount");
            pricee=order.getString("price");
            del_charges=order.getString("delivery_charges");
            tot_price=order.getString("total_price");
            payment_method=order.getString("payment_method");
            JSONObject json_res=order.getJSONObject("restaurant");
                res_id=json_res.getString("id");
                res_title=json_res.getString("title");
                res_title_ar=json_res.getString("title_ar");
                res_image=json_res.getString("image");
            deli_date=order.getString("delivery_date");
            deli_time=order.getString("delivery_time");
            pay_status=order.getString("payment_status");
            deli_status=order.getString("delivery_status");
            date=order.getString("date");
            ratingg=order.getString("rating");
            review=order.getString("review");
            JSONArray json_pro=order.getJSONArray("products");
            for(int i=0;i<json_pro.length();i++){
                JSONObject temp=json_pro.getJSONObject(i);
               Product pro=new Product(temp);
                this.product.add(pro);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String get_res_Title(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return res_title_ar;
        else
            return  res_title;
    }
    public String get_area(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return area_title_ar;
        else
            return  area_title;
    }
    public class Product {
       String pro_id,product_id,product_name,quantity,pro_price;
        ArrayList<Addon> addo=new ArrayList<>();
        ArrayList<Option> opt=new ArrayList<>();
        Product(JSONObject prod){
            try {
                pro_id=prod.getString("id");
                product_id=prod.getString("product_id");
                product_name=prod.getString("product_name");
                quantity=prod.getString("quantity");
                pro_price=prod.getString("price");
                JSONArray json_adon=prod.getJSONArray("addons");
                for(int j=0;j<json_adon.length();j++){
                    Addon addon=new Addon(json_adon.getJSONObject(j));
                    this.addo.add(addon);
                }
                JSONArray json_option=prod.getJSONArray("options");
                for(int k=0;k<json_option.length();k++){
                    Option opti=new Option(json_option.getJSONObject(k));
                    this.opt.add(opti);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public class Addon {
            String add_id,add_title,add_title_ar;
            ArrayList<Item> itm=new ArrayList<>();
            Addon(JSONObject addons){

                try {
                    add_id=addons.getString("id");
                    add_title=addons.getString("title");
                    add_title_ar=addons.getString("title_ar");
                    JSONArray json_item=addons.getJSONArray("items");
                    for(int l=0;l<json_item.length();l++){
                        Item item=new Item(json_item.getJSONObject(l));
                        this.itm.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public String get_addon_Title(Context context) {
                if(Settings.get_user_language(context).equals("ar"))
                    return add_title_ar;
                else
                    return  add_title;
            }
            public class Item {
                String item_id,item_title,item_title_ar,item_price;
                Item(JSONObject items){
                    try {
                        item_id=items.getString("addon_id");
                        item_title=items.getString("addon_title");
                        item_title_ar=items.getString("addon_title_ar");
                        item_price=items.getString("price");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                public String get_item_Title(Context context) {
                    if(Settings.get_user_language(context).equals("ar"))
                        return item_title_ar;
                    else
                        return  item_title;
                }
            }

        }
        public class Option {
        Option(JSONObject options){

        }
    }
}
}
