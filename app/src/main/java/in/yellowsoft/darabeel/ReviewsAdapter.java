package in.yellowsoft.darabeel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


public class ReviewsAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    Restaurants restaurants;
//    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public ReviewsAdapter(Context mainActivity, Restaurants restaurants ) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.restaurants=restaurants;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return restaurants.allReviwses.size();
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
        MyTextView name,description,date;
        LinearLayout rating;
      }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.reviews_item, null);
        Log.e("res_name",restaurants.getTitle(context));
        holder.name=(MyTextView) rowView.findViewById(R.id.review_name);
        holder.date=(MyTextView) rowView.findViewById(R.id.date_reviews);
        holder.description=(MyTextView) rowView.findViewById(R.id.review_des);
        holder.name.setText(restaurants.allReviwses.get(position).namee);
        holder.date.setText(restaurants.allReviwses.get(position).date);
        holder.description.setText(restaurants.allReviwses.get(position).review);
        holder.rating=(LinearLayout)rowView.findViewById(R.id.reviews_list_rating);
        Settings.set_rating_yellow(context, restaurants.allReviwses.get(position).ratingg,holder.rating);
        return rowView;
    }
}