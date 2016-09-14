package in.yellowsoft.darabeel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import java.util.ArrayList;

public class FinalPageAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    ArrayList<CartItem> cart_items;
//    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public FinalPageAdapter(Context mainActivity, ArrayList<CartItem> cart_items) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.cart_items=cart_items;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cart_items.size();
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
        MyTextView final_items_name,final_multification,final_item_price;
        ImageView cart_item_image;
      }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.final_item, null);
        holder.final_items_name=(MyTextView) rowView.findViewById(R.id.final_item_name);
        holder.final_multification=(MyTextView) rowView.findViewById(R.id.multification_final);
        holder.final_item_price=(MyTextView) rowView.findViewById(R.id.final_item_price);
        holder.final_items_name.setText(cart_items.get(position).products.getTitle(context));
        holder.final_multification.setText(cart_items.get(position).quantity+" x "+String.format("%.3f",Float.parseFloat(cart_items.get(position).products.cart_price)));
        holder.final_item_price.setText(String.format("%.3f",Integer.parseInt(cart_items.get(position).quantity) * Float.parseFloat(cart_items.get(position).products.cart_price))+ " KD" );
        return rowView;
    }
}