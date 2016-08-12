package in.yellowsoft.darabeel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OffersAdapter extends BaseAdapter{
    Restaurants restaurants;
    String [] result;
    Context context;
    int [] imageId;
    OffersListFragment offersListFragment;
//    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public OffersAdapter(Context mainActivity, Restaurants restaurants, OffersListFragment offersListFragment) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.offersListFragment=offersListFragment;
        this.restaurants=restaurants;

        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return restaurants.promotions.size();
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
        TextView show_menu;
        SquareImageview promotion_img;
        LinearLayout rating_ll,show_menu_ll;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.offers_item_screen, null);
        holder.promotion_img=(SquareImageview)rowView.findViewById(R.id.promotion_img);
        Log.e("img",restaurants.promotions.get(position).image);
        Picasso.with(context).load(restaurants.promotions.get(position).image).fit().into(holder.promotion_img);

////         holder.tv.setText(companyDetailses.get(position).title1);
//        holder.tv1.setText(companyDetailses.get(position).current_status);
//        price=Integer.parseInt(companyDetailses.get(position).price_pickup)+Integer.parseInt(companyDetailses.get(position).price_drop_off);
//        holder.tv2.setText(String.valueOf(price));
//        // holder.img.setImageResource(imageId[position]);
//        Picasso.with(context).load(companyDetailses.get(position).logo).into(holder.img);
        return rowView;
    }

}