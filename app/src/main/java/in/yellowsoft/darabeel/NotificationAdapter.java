package in.yellowsoft.darabeel;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter{
    int price;
    ArrayList<Promotions> restaurants;
    String [] result;
    Context context;
    int [] imageId;
    NotificationFragment promotionsListFragment;
    private static LayoutInflater inflater=null;
    public NotificationAdapter(Context mainActivity, ArrayList<Promotions> restaurants, NotificationFragment promotionsListFragment) {
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
        MyTextView offer_name,offer_descri,pro_price;
        ImageView com_logo,offer_background;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.notification_item_screen, null);
        holder.offer_name = (MyTextView) rowView.findViewById(R.id.promotion_title);
        holder.offer_descri = (MyTextView) rowView.findViewById(R.id.promotion_description);
        holder.pro_price = (MyTextView) rowView.findViewById(R.id.promotion_price);
        holder.offer_background = (ImageView) rowView.findViewById(R.id.offer_img_promtions);

        holder.offer_name.setText(restaurants.get(position).getTitle(context));
        holder.offer_descri.setText(Html.fromHtml(restaurants.get(position).getDescription(context)));
        holder.pro_price.setText("KD  "+restaurants.get(position).pricee);
        Picasso.with(context).load(restaurants.get(position).image).fit().into(holder.offer_background);

            return rowView;
        }

    }
