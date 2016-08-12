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

public class OptionsAdapter extends BaseAdapter {
    int total;
    float var;
    String[] result;
    Context context;
    int[] imageId;
    AlertDialogManager alert = new AlertDialogManager();
    Products products;
    //    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater = null;

    public OptionsAdapter(Context mainActivity, Products products) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context = mainActivity;
        this.products = products;
        //  imageId=prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return products.options.size();
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
        TextView opt_title, opt_price;
        ImageView opt_img;
        LinearLayout option_linear;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.optional_item, null);
        holder.opt_title = (TextView) rowView.findViewById(R.id.option_title);
        holder.opt_img = (ImageView) rowView.findViewById(R.id.option_img);
        holder.opt_title.setText(products.options.get(position).get_option(context) + "   (" + products.options.get(position).price + " KD)");
        holder.option_linear = (LinearLayout) rowView.findViewById(R.id.option_ll);
        holder.option_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (can_select_more()) {
                    products.options.get(position).isselected = !products.options.get(position).isselected;

                    if (products.options.get(position).isselected) {
                        holder.opt_img.setImageResource(R.drawable.ic_option_pink);
                        var=Float.parseFloat(products.price) +Float.parseFloat( products.options.get(position).price);
                        products.cart_price = String.valueOf(var);

                    }else {
                        holder.opt_img.setImageResource(R.drawable.ic_option_brown);
                        products.cart_price = products.price;
                    }
                } else if (products.options.get(position).isselected) {
                    products.options.get(position).isselected = !products.options.get(position).isselected;

                    if (products.options.get(position).isselected){
                        holder.opt_img.setImageResource(R.drawable.ic_option_pink);
                        var=Float.parseFloat(products.price) +Float.parseFloat( products.options.get(position).price);
                        products.cart_price = String.valueOf(var);

                    } else {
                        holder.opt_img.setImageResource(R.drawable.ic_option_brown);
                        products.cart_price = products.price;
                    }
                }
                else
                    alert.showAlertDialog(context, "Info", Settings.getword(context, "max_options_selected"), false);
//                    Toast.makeText(context, Settings.getword(context,"max_options_selected"), Toast.LENGTH_SHORT).show();

            }
        });
        return rowView;
    }

    public boolean can_select_more() {
        int temp_count = 0;
        for (int i = 0; i < products.options.size(); i++) {
            if (products.options.get(i).isselected) {
                temp_count++;
            }
        }
        Log.e("Selected_count", String.valueOf(temp_count));
        if (temp_count <1)
            return true;

        else return false;
    }

}
