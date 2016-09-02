package in.yellowsoft.darabeel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductPageAdapter extends BaseAdapter{
    Context context;
    Products productses ;
    ArrayList<Integer> click;
    ProductPageFragment productPageFragment;
    private static LayoutInflater inflater=null;
    public ProductPageAdapter(Context mainActivity, Products productses, ProductPageFragment productPageFragment) {
        click=new ArrayList<>();
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        this.productPageFragment=productPageFragment;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productses = productses;
        for(int i=0;i<productses.groups.size();i++){
            click.add(1);
        }
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return productses.groups.size();
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
        TextView group_title;
        GridView addon_gv;
        AddonAdapter addonAdapter;
        ImageView pluse_list;
        LinearLayout addon_list;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.addon_item, null);
        holder.addon_list=(LinearLayout)rowView.findViewById(R.id.addon_ll);
        holder.pluse_list=(ImageView)rowView.findViewById(R.id.pluse_list);
        holder.group_title=(TextView) rowView.findViewById(R.id.group_name);
        holder.addon_gv=(GridView)rowView.findViewById(R.id.addon_grid);
        holder.group_title.setText(productses.groups.get(position).get_group(context));
        holder.addonAdapter=new AddonAdapter(context,productses,position,productses.groups.get(position).max);
        holder.addon_gv.setAdapter(holder.addonAdapter);
        setGridViewHeightBasedOnItems(holder.addon_gv);
        if(click.get(position)==2){
            holder.addon_list.setVisibility(View.VISIBLE);
            holder.pluse_list.setImageResource(R.drawable.minus_for_dara);
//            productPageFragment.call_list();
//            click.set(position,1);
        }
        holder.pluse_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click.get(position)==1){
                    holder.addon_list.setVisibility(View.VISIBLE);
//                    productPageFragment.call_list();
                    holder.pluse_list.setImageResource(R.drawable.minus_for_dara);
                    click.set(position,2);
//                    notifyDataSetChanged();
                }else{
                    holder.addon_list.setVisibility(View.GONE);
                    holder.pluse_list.setImageResource(R.drawable.plus_for_dara);
                    click.set(position,1);
//                    notifyDataSetChanged();
                }
            }
        });
//        holder.pluse_list.performClick();
        return rowView;
    }
    public static boolean setGridViewHeightBasedOnItems(GridView gridView) {

        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            int lastItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, gridView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
                lastItemsHeight = item.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            if(numberOfItems%2==1)
                totalItemsHeight=totalItemsHeight+lastItemsHeight;

            params.height = totalItemsHeight/2 ;
            gridView.setLayoutParams(params);
            gridView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
}