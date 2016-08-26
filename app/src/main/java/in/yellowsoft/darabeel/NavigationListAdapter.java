package in.yellowsoft.darabeel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView tv;
        ImageView img;
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
        holder.tv=(TextView)rowView.findViewById(R.id.title);
        holder.img=(ImageView) rowView.findViewById(R.id.icon);
        if(position==0||position==1||position==3){
            holder.img.setVisibility(View.VISIBLE);
        }else{
            holder.img.setVisibility(View.INVISIBLE);
        }
        holder.tv.setText(titles.get(position));
        Picasso.with(context).load(images.get(position)).into(holder.img);
        return rowView;
    }

}