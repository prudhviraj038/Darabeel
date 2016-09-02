package in.yellowsoft.darabeel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CompanylistAdapter extends BaseAdapter implements Filterable{
    Context context;
    ArrayList<Restaurants> restaurants;
    ArrayList<Restaurants> restaurants_all;
    PlanetFilter planetFilter;
    private static LayoutInflater inflater=null;
    public CompanylistAdapter(Context mainActivity, ArrayList<Restaurants> restaurants) {
        context=mainActivity;
        this.restaurants = restaurants;
        this.restaurants_all=restaurants;
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

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;
    }

    public class Holder
    {
        MyTextView com_name,com_items,time,trally_charge,min_order,payment,delivery_time,reviews,status;
        ImageView com_logo,card1,card2,card3,card4,com_status,offer;
        LinearLayout rating_ll,com_payment_type;
        RatingBar ratingBar;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView=convertView;
        if(convertView == null)
        rowView = inflater.inflate(R.layout.company_list_item, null);

        holder.rating_ll=(LinearLayout) rowView.findViewById(R.id.rating_com_list_item);
        Log.e("rating",restaurants.get(position).rating);
        Settings.set_rating(context, restaurants.get(position).rating, holder.rating_ll);
        holder.min_order=(MyTextView) rowView.findViewById(R.id.min_order_item);
        holder.min_order.setText(Settings.getword(context,"minimum_order_amout"));
        holder.payment=(MyTextView) rowView.findViewById(R.id.item_payment);
        holder.payment.setText(Settings.getword(context,"text_payment_type"));
        holder.delivery_time=(MyTextView) rowView.findViewById(R.id.item_delivery_time);
        holder.delivery_time.setText(Settings.getword(context,"text_delivery_time"));
//        holder.reviews=(MyTextView) rowView.findViewById(R.id.review_com_list_item);
        holder.status=(MyTextView) rowView.findViewById(R.id.com_status);
        if(restaurants.get(position).status.equals("Open")){
            holder.status.setTextColor(context.getResources().getColor(R.color.green_text));
        }else if(restaurants.get(position).status.equals("Closed")){
            holder.status.setTextColor(context.getResources().getColor(R.color.red_text));
        }else {
            holder.status.setTextColor(context.getResources().getColor(R.color.yellow_text));
        }
        holder.status.setText("("+restaurants.get(position).status+")");
//        holder.reviews.setText("("+restaurants.get(position).reviews+")");
        holder.com_name=(MyTextView) rowView.findViewById(R.id.company_list_name);
        holder.com_items=(MyTextView) rowView.findViewById(R.id.company_list_items);
        holder.time=(MyTextView) rowView.findViewById(R.id.time_com_list);
        holder.trally_charge=(MyTextView) rowView.findViewById(R.id.travel_charge);
        holder.com_logo=(ImageView) rowView.findViewById(R.id.com_list_logo);
        holder.offer=(ImageView) rowView.findViewById(R.id.img_offer);
        if(restaurants.get(position).promotions.size()>0){
            holder.offer.setVisibility(View.VISIBLE);
        }else{
            holder.offer.setVisibility(View.GONE);
        }
//        holder.payment_ll=(LinearLayout) rowView.findViewById(R.id.pay_layout);


//        for(int i=0;i<restaurants.get(position).payment.size();i++){
//            ImageView temp = new ImageView(context);
//            Picasso.with(context).load(restaurants.get(position).payment.get(i).image).into(temp);
//            holder.payment_ll.addView(temp);
//        }
//        holder.card1=(ImageView) rowView.findViewById(R.id.card1);
//        holder.card2=(ImageView) rowView.findViewById(R.id.card2);
//        holder.card3=(ImageView) rowView.findViewById(R.id.card3);
//        holder.card4=(ImageView) rowView.findViewById(R.id.card4);

//        holder.com_status=(ImageView) rowView.findViewById(R.id.status);
        holder.com_name.setText(restaurants.get(position).title);
        String temp="";
        for(int i=0;i<restaurants.get(position).category.size();i++){
            if(i==0)
                temp = restaurants.get(position).category.get(i).getTitle(context);
            else
                temp=temp+","+restaurants.get(position).category.get(i).getTitle(context);
        }
        holder.com_items.setText(restaurants.get(position).getsdescription(context));
        holder.time.setText(restaurants.get(position).hours);
        holder.trally_charge.setText(restaurants.get(position).min+" KD ");
        Picasso.with(context).load(restaurants.get(position).image).into(holder.com_logo);
        holder.com_payment_type=(LinearLayout)rowView.findViewById(R.id.payment_com_item);
        holder.com_payment_type.removeAllViews();
        for(int i=0;i<restaurants.get(position).payment.size();i++){
            ImageView temp_img = new ImageView(context);
            temp_img.setAdjustViewBounds(true);
            Picasso.with(context).load(restaurants.get(position).payment.get(i).image).into(temp_img);
            holder.com_payment_type.addView(temp_img);
        }
//        if(restaurants.get(position).status.equals("Open")){
//            holder.com_status.setImageResource(R.drawable.open_img);
//        }else if (restaurants.get(position).status.equals("Busy")){
//            holder.com_status.setImageResource(R.drawable.busy_img);
//        }else if (restaurants.get(position).status.equals("Closed")) {
//            holder.com_status.setImageResource(R.drawable.close_img);
//        }else {
//            holder.com_status.setImageResource(R.drawable.open_img);
//        }
       // holder.com_name
        return rowView;
    }
    private class PlanetFilter extends Filter {
        Boolean clear_all=false;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            clear_all=false;
            if (constraint == null || constraint.length() == 0) {
                clear_all=true;
// No filter implemented we return all the list
                results.values = restaurants;
                results.count = restaurants.size();
            }
            else {
// We perform filtering operation
                List<Restaurants> nPlanetList = new ArrayList<Restaurants>();

                for (Restaurants p : restaurants_all) {
                    Log.e(p.rating,String.valueOf(constraint));
                    if (Float.parseFloat(p.rating)>=Float.parseFloat(String.valueOf(constraint)));
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            if (results.count == 0) {
//                restaurants = (ArrayList<Restaurants>) results.values;
                notifyDataSetChanged();
            }
            else if(clear_all){
                restaurants = restaurants_all;
                notifyDataSetChanged();
            }
            else {
                restaurants = (ArrayList<Restaurants>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}