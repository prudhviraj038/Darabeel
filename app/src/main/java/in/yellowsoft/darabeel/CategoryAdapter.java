package in.yellowsoft.darabeel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter{
    Context context;
    ArrayList<Category> categories;
    private static LayoutInflater inflater=null;
    public CategoryAdapter(Context mainActivity, ArrayList<Category> categories) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categories = categories;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return categories.size();
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
        rowView = inflater.inflate(R.layout.category_item, null);
        holder.com_name=(MyTextView) rowView.findViewById(R.id.cat_name);
        holder.com_name.setText(categories.get(position).getTitle(context));
        return rowView;
        
    }

}