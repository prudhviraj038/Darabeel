package in.yellowsoft.darabeel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAccountAddressAdapter extends BaseAdapter{
    Context context;
    ArrayList<Addresss> addresses;
    MyAccountFragment myAccountFragment;
//    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public MyAccountAddressAdapter(Context mainActivity, ArrayList<Addresss> addresses) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.addresses=addresses;
        this.myAccountFragment=myAccountFragment;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return addresses.size();
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
        TextView res_name,order_id,pri,date,pay_method,deli_status,order_details;

      }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.my_account_address_item, null);
        holder.res_name=(TextView) rowView.findViewById(R.id.alias_item);
        holder.res_name.setText(addresses.get(position).alias);
        return rowView;
    }


}