package in.yellowsoft.darabeel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartPageAdapter extends BaseAdapter{
    float total;
    int number;
    String [] result;
    Context context;
    String opt_price;
    float addon_price=0;
    int [] imageId;
    CartFragment cartFragment;
    ArrayList<CartItem> cart_items;
    AlertDialogManager alert = new AlertDialogManager();

    //    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public CartPageAdapter(Context mainActivity, ArrayList<CartItem> cart_items, CartFragment cartFragment) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.cart_items=cart_items;
        this.cartFragment=cartFragment;
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
        TextView cart_item_name,cart_item_quantity,catr_item_price,cart_item_total,stat_quantity,stat_price,stat_total,delete;
        ImageView cart_item_image;
       LinearLayout pluse,minus;
      }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.cart_item, null);
        holder.stat_quantity=(TextView) rowView.findViewById(R.id.static_cart_item_quantity);
        holder.stat_quantity.setText(Settings.getword(context,"quantity"));
        holder.stat_price=(TextView) rowView.findViewById(R.id.static_cart_item_price);
        holder.stat_price.setText(Settings.getword(context,"price"));
        holder.stat_total=(TextView) rowView.findViewById(R.id.static_cart_item_total);
        holder.stat_total.setText(Settings.getword(context,"total"));
        holder.cart_item_name=(TextView) rowView.findViewById(R.id.cart_item_name);
        holder.cart_item_quantity=(TextView) rowView.findViewById(R.id.cart_item_quantity);
        holder.catr_item_price=(TextView) rowView.findViewById(R.id.cart_item_price);
        holder.cart_item_total=(TextView) rowView.findViewById(R.id.cart_item_total);
        holder.cart_item_image=(ImageView) rowView.findViewById(R.id.cart_item_img);
        holder.delete=(TextView) rowView.findViewById(R.id.cart_delete);
        holder.pluse=(LinearLayout) rowView.findViewById(R.id.cart_plus_ll);
        holder.minus=(LinearLayout) rowView.findViewById(R.id.cart_minus_ll);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart_items.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.cart_item_name.setText(cart_items.get(position).products.getTitle(context));
        holder.cart_item_quantity.setText(" "+cart_items.get(position).quantity);
        number=Integer.parseInt(cart_items.get(position).quantity);
        holder.pluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Integer.parseInt(cart_items.get(position).products.qut)-1)<number){
                    alert.showAlertDialog(context, "Info",Settings.getword(context,"out_stock")+"   select"+cart_items.get(position).products.qut+" Products", true);
                }else {
                    number++;
                    holder.cart_item_quantity.setText(" " + String.valueOf(number));
                    cart_items.get(position).quantity = String.valueOf(number);
                    notifyDataSetChanged();
                }
            }
        });
        holder. minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number > 1) {
                    number--;
                    holder.cart_item_quantity.setText(" "+String.valueOf(number));
                    cart_items.get(position).quantity =String.valueOf(number);
                    notifyDataSetChanged();
                }
            }
        });

//        for (int i=0;i<cart_items.get(position).products.groups.size();i++){
//            for (int j=0;j<cart_items.get(position).products.groups.get(i).addons.size();j++){
//                if (cart_items.get(position).products.groups.get(i).addons.get(j).isselected){
//                    addon_price=addon_price+Float.parseFloat(cart_items.get(position).products.groups.get(i).addons.get(j).price);
//                }
//            }
//        }
        holder.catr_item_price.setText(cart_items.get(position).products.cart_price+ " KD " );
         total = number * Float.parseFloat(cart_items.get(position).products.cart_price);
        holder.cart_item_total.setText(String.format("%.3f",total)+ " KD " );
        getsum();


//         holder.tv.setText(companyDetailses.get(position).title1);
//        holder.tv1.setText(companyDetailses.get(position).current_status);
//        price=Integer.parseInt(companyDetailses.get(position).price_pickup)+Integer.parseInt(companyDetailses.get(position).price_drop_off);
//        holder.tv2.setText(String.valueOf(price));
//        // holder.img.setImageResource(imageId[position]);
        Picasso.with(context).load(cart_items.get(position).products.images.get(0).img).into(holder.cart_item_image);

        return rowView;
    }
    public void getsum(){
        float temp=0;
        for(int i=0;i<cart_items.size();i++){
            temp=temp+Float.parseFloat(cart_items.get(i).quantity)*Float.parseFloat(cart_items.get(i).products.cart_price);
            cartFragment.total_amount.setText(String.format("%.3f",temp)+" KD");
        }
    }

}