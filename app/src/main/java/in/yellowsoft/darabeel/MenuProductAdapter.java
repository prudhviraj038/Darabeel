package in.yellowsoft.darabeel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<Products> productses;
    private static LayoutInflater inflater = null;

    public MenuProductAdapter(Activity mainActivity, ArrayList<Products> productses) {
        // TODO Auto-generated constructor stu
        context = mainActivity;
        this.productses = productses;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return productses.size();
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

    public class Holder {
        MyTextView meal_name, stock_status, price, number;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        if (convertView == null)
            rowView = inflater.inflate(R.layout.product_list_item, parent, false);
        else
            rowView = convertView;

        holder.meal_name = (MyTextView) rowView.findViewById(R.id.meal_name);
        holder.stock_status = (MyTextView) rowView.findViewById(R.id.stock_status);
        holder.price = (MyTextView) rowView.findViewById(R.id.price_product);
//        holder.number = (MyTextView) rowView.findViewById(R.id.number);
        holder.img = (ImageView) rowView.findViewById(R.id.pro_image);
        holder.meal_name.setText(productses.get(position).getTitle(context));
//        holder.stock_status.setText(productses.get(position).stock);
        if(productses.get(position).qut.equals("0")){
            holder.stock_status.setTextColor(context.getResources().getColor(R.color.red_text));
            holder.stock_status.setText(Settings.getword(context,"out_stock"));
        }else {
            holder.stock_status.setTextColor(context.getResources().getColor(R.color.green_text));
            holder.stock_status.setText(Settings.getword(context,"in_stock"));
        }
        holder.price.setText(productses.get(position).price+" KD");
        Picasso.with(context).load(productses.get(position).images.get(0).img).into(holder.img);
        return rowView;
        }

    }

