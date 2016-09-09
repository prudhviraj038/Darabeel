package in.yellowsoft.darabeel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class DateAdapter extends BaseAdapter{
    Context context;
    ArrayList<Days> categories;
    private static LayoutInflater inflater=null;
    public DateAdapter(Context mainActivity, ArrayList<Days> categories) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categories = categories;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.e("size",String.valueOf(categories.size()));
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
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.days_item, null);
        holder.com_name=(MyTextView) rowView.findViewById(R.id.time_tvv);
        holder.com_name.setText(categories.get(position).getTitle(context));
        holder.img=(ImageView)rowView.findViewById(R.id.time_im);

        return rowView;
        
    }

}