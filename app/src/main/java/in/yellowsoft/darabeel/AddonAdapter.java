package in.yellowsoft.darabeel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddonAdapter extends BaseAdapter{
    Context context;
    Products products;
    int posi;
    String max;
    float var;
    private static LayoutInflater inflater=null;
    public AddonAdapter(Context mainActivity, Products products, int posi, String max) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        this.products=products;
        this.posi=posi;
        this.max=max;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.products = products;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
       return products.groups.get(posi).addons.size();

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
        TextView addon_title,addon_price;
        ImageView addon_image;
        LinearLayout addon_linear;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.addon_grid_item, null);
        holder.addon_title=(TextView) rowView.findViewById(R.id.addon_name);

        if (Float.parseFloat(products.groups.get(posi).addons.get(position).price)==0){
            holder.addon_title.setText(products.groups.get(posi).addons.get(position).get_addon(context));
        }else {
            holder.addon_title.setText(products.groups.get(posi).addons.get(position).get_addon(context)+"  ("+products.groups.get(posi).addons.get(position).price+" KD ) ");
        }
        holder.addon_image=(ImageView)rowView.findViewById(R.id.addon_img);
        if(products.groups.get(posi).addons.get(position).isselected)
            holder.addon_image.setImageResource(R.drawable.ic_option_pink);
        else
            holder.addon_image.setImageResource(R.drawable.ic_option_brown);

        holder.addon_linear=(LinearLayout)rowView.findViewById(R.id.addon_ll);
        holder.addon_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(can_select_more()) {
                    products.groups.get(posi).addons.get(position).isselected = !products.groups.get(posi).addons.get(position).isselected;

                    if (products.groups.get(posi).addons.get(position).isselected) {
                        var=Float.parseFloat(products.cart_price)+Float.parseFloat(products.groups.get(posi).addons.get(position).price);
                        products.cart_price=String.valueOf(var);
                        holder.addon_image.setImageResource(R.drawable.ic_option_pink);
                    }else {
                        products.cart_price=products.price;
                        holder.addon_image.setImageResource(R.drawable.ic_option_brown);
                    }
                }else if(products.groups.get(posi).addons.get(position).isselected){
                    products.groups.get(posi).addons.get(position).isselected = !products.groups.get(posi).addons.get(position).isselected;

                    if (products.groups.get(posi).addons.get(position).isselected) {
                        var=Float.parseFloat(products.cart_price)+Float.parseFloat(products.groups.get(posi).addons.get(position).price);
                        products.cart_price=String.valueOf(var);
                        holder.addon_image.setImageResource(R.drawable.ic_option_pink);
                    }else{
                        products.cart_price=products.price;
                        holder.addon_image.setImageResource(R.drawable.ic_option_brown);
                    }


                }
                else
                    Toast.makeText(context,Settings.getword(context,"addon_maximum")+" "+max+"  addons",Toast.LENGTH_SHORT).show();

            }
        });
        return rowView;
    }
    public  boolean can_select_more(){
        int temp_count=0;
        for (int i=0;i<products.groups.get(posi).addons.size();i++){

            if(products.groups.get(posi).addons.get(i).isselected){
                temp_count++;
            }

        }
        Log.e("Selected_count",String.valueOf(temp_count));
        Log.e("max_count",max);

        if(temp_count<Integer.parseInt(max))
        return  true;

        else return false;
    }
}