package in.yellowsoft.darabeel;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PromotionAdapter extends BaseAdapter{
    int price;
    ArrayList<PromotionList> restaurants;
    String [] result;
    Context context;
    int [] imageId;
//    ArrayList<CompanyDetails> companyDetailses;
    PromotionsListFragment promotionsListFragment;
    private static LayoutInflater inflater=null;
    public PromotionAdapter(Context mainActivity, ArrayList<PromotionList> restaurants, PromotionsListFragment promotionsListFragment) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.restaurants=restaurants;
        this.promotionsListFragment = promotionsListFragment;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        MyTextView show_menu,title_tv,des_tv,price_tv,sta_price;
        SquareImageview promotion_img;
        ImageView res_img;
        LinearLayout rating_ll,show_menu_ll;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.promotion_item_screen, null);
        holder.show_menu = (MyTextView) rowView.findViewById(R.id.showmenu_pro_list_tv);
        holder.show_menu.setText(Settings.getword(context,"show_menu"));
        holder.sta_price = (MyTextView) rowView.findViewById(R.id.p_price_sta);
        holder.sta_price.setText(Settings.getword(context,"price"));
        holder.title_tv = (MyTextView) rowView.findViewById(R.id.p_title);
        holder.title_tv.setText(restaurants.get(position).getTitle(context));
        holder.des_tv = (MyTextView) rowView.findViewById(R.id.p_des);
        holder.des_tv.setText(Html.fromHtml(restaurants.get(position).getdescription(context)));
        holder.price_tv = (MyTextView) rowView.findViewById(R.id.p_price);
        holder.price_tv.setText(restaurants.get(position).price+" KD ");

        holder.res_img = (ImageView) rowView.findViewById(R.id.res_img_promtions);
        Picasso.with(context).load(restaurants.get(position).rest.get(0).image).fit().into(holder.res_img);
        holder.rating_ll=(LinearLayout)rowView.findViewById(R.id.res_promotionlist_rating);
//        Settings.set_rating(context,restaurants.get(position).rest.get(0).rating, holder.rating_ll);
        holder.show_menu_ll=(LinearLayout)rowView.findViewById(R.id.show_mwnu_pro_list_ll);
//        holder.show_menu_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                promotionsListFragment.mCallBack.promotion_back();
//            }
//        });
        holder.promotion_img=(SquareImageview)rowView.findViewById(R.id.promotionlist_img);
        Picasso.with(context).load(restaurants.get(position).image).into(holder.promotion_img);
            return rowView;
        }

    }
