package in.yellowsoft.darabeel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NavigationListAdapter extends BaseAdapter{
    Context context;
    ArrayList<Integer> images;
    ArrayList<String> titles;
    private static LayoutInflater inflater=null;
    public NavigationListAdapter(Activity mainActivity, ArrayList<String> titles,  ArrayList<Integer> images) {
        // TODO Auto-generated constructor stu
        context=mainActivity;
        this.images=images;
        this.titles=titles;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titles.size();
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
        MyTextView tv;
        ImageView img;
        LinearLayout bg,line;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        if(convertView==null)
        rowView = inflater.inflate(R.layout.menu_item, parent,false);
        else
        rowView = convertView;
        holder.tv=(MyTextView)rowView.findViewById(R.id.title);
        holder.img=(ImageView) rowView.findViewById(R.id.icon);
        holder.bg=(LinearLayout)rowView.findViewById(R.id.nav_item_back);
        holder.line=(LinearLayout)rowView.findViewById(R.id.nav_line);
//        if(position==0||position==1||position==3){
//            holder.img.setVisibility(View.VISIBLE);
//        }else{
//            holder.img.setVisibility(View.INVISIBLE);
//        }
        if(position==9||position==10){
            holder.line.setVisibility(View.GONE);
        }else{
            holder.line.setVisibility(View.VISIBLE);
        }
        if(position==10){
            holder.bg.setBackgroundColor(Color.parseColor("#d4005a"));

        }else{
            holder.bg.setBackgroundColor(Color.parseColor("#71422a"));

        }
        holder.tv.setText(titles.get(position));
        Picasso.with(context).load(images.get(position)).into(holder.img);
        return rowView;
    }

}