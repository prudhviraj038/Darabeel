package in.yellowsoft.darabeel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class MenuCategoryAdapter extends BaseAdapter{
    Context context;
    Restaurants restaurantses;

    private static LayoutInflater inflater=null;
    public MenuCategoryAdapter(Context context, Restaurants restaurantses) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.restaurantses = restaurantses;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return restaurantses.menu.size();
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
        MyTextView com_name;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.menu_category_item, null);
        holder.com_name=(MyTextView) rowView.findViewById(R.id.menu_cat_name);
        holder.com_name.setText(restaurantses.menu.get(position).getTitle(context));
        return rowView;
        
    }

}