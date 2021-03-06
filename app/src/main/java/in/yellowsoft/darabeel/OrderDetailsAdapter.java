package in.yellowsoft.darabeel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;

public class OrderDetailsAdapter extends BaseAdapter{
    Context context;
    ArrayList<JsonOrders> orderses;
    int posi;
//    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public OrderDetailsAdapter(Context mainActivity, ArrayList<JsonOrders> orderses,int posi) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.orderses=orderses;
        this.posi=posi;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return orderses.get(posi).product.size();
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
        MyTextView item_name,item_qty,item_price;
      }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.order_detail_item, null);
        holder.item_name=(MyTextView) rowView.findViewById(R.id.item_name_details);
        holder.item_name.setText(orderses.get(posi).product.get(position).product_name);
        holder.item_qty=(MyTextView) rowView.findViewById(R.id.item_qnty_details);
        holder.item_qty.setText(orderses.get(posi).product.get(position).quantity+" x "+String.format("%.3f",Float.parseFloat(orderses.get(posi).product.get(position).pro_price)));
        holder.item_price=(MyTextView) rowView.findViewById(R.id.item_price_details);
        holder.item_price.setText(String.format("%.3f",Integer.parseInt(orderses.get(posi).product.get(position).quantity)*Float.parseFloat(orderses.get(posi).product.get(position).pro_price))+" KD");
        return rowView;
    }


}