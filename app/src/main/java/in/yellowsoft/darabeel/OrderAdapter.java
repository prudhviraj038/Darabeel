package in.yellowsoft.darabeel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends BaseAdapter{
    Context context;
    String write;
    Dialog alert;

    float rate;
    ArrayList<JsonOrders> orderses;
    MyOrdersfragment myAccountFragment;
//    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public OrderAdapter(Context mainActivity, ArrayList<JsonOrders> orderses, MyOrdersfragment myAccountFragment) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.orderses=orderses;
        this.myAccountFragment=myAccountFragment;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return orderses.size();
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
        TextView res_name,order_id,pri,date,pay_method,deli_status,sta_reorder,rating_tv,
        sta_order_id,sta_pri,sta_date,sta_pay_method,sta_deli_status;
        ImageView res_image;
        LinearLayout reorder_ll,rating_ll,rating;
      }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.orders_fragment_item, null);
        holder.res_name=(TextView) rowView.findViewById(R.id.res_name);
        holder.res_name.setText(orderses.get(position).get_res_Title(context));
        holder.order_id=(TextView) rowView.findViewById(R.id.order_id);
        holder.order_id.setText(orderses.get(position).id);
        holder.pri=(TextView) rowView.findViewById(R.id.tot_price);
        holder.pri.setText(" KD "+orderses.get(position).tot_price);
        holder.date=(TextView) rowView.findViewById(R.id.date);
        holder.date.setText(orderses.get(position).date);
        holder.pay_method=(TextView) rowView.findViewById(R.id.pay_method);
        holder.pay_method.setText(orderses.get(position).payment_method);
        holder.deli_status=(TextView) rowView.findViewById(R.id.delivery_status);
        holder.deli_status.setText(orderses.get(position).deli_status);

        holder.sta_order_id=(TextView) rowView.findViewById(R.id.sta_order_id);
        holder.sta_order_id.setText(Settings.getword(context, "order_id"));
        holder.sta_pri=(TextView) rowView.findViewById(R.id.sta_price_order);
        holder.sta_pri.setText(Settings.getword(context, "total"));
        holder.sta_date=(TextView) rowView.findViewById(R.id.sta_order_date);
        holder.sta_date.setText(Settings.getword(context, "order_date"));
        holder.sta_pay_method=(TextView) rowView.findViewById(R.id.sta_order_pay_mode);
        holder.sta_pay_method.setText(Settings.getword(context, "order_payment_method"));
        holder.sta_deli_status=(TextView) rowView.findViewById(R.id.sta_order_status);
        holder.sta_deli_status.setText(Settings.getword(context, "order_status"));
        holder.sta_reorder = (TextView)rowView.findViewById(R.id.reorder_tv);
        holder.sta_reorder.setText(Settings.getword(context, "reorder"));

        holder.rating_tv=(TextView) rowView.findViewById(R.id.order_rating_tv);
        holder.rating_tv.setText(Settings.getword(context, "rate_order"));
        holder.res_image=(ImageView)rowView.findViewById(R.id.res_image);
        Picasso.with(context).load(orderses.get(position).res_image).fit().into(holder.res_image);
        holder.rating=(LinearLayout)rowView.findViewById(R.id.rating_order_ll);
        Settings.set_rating(context,orderses.get(position).ratingg, holder.rating);
        holder.reorder_ll=(LinearLayout)rowView.findViewById(R.id.reorder_ll);
        holder.rating_ll=(LinearLayout)rowView.findViewById(R.id.order_rating_ll);
        if(orderses.get(position).ratingg.equals("")||orderses.get(position).ratingg.equals("0")){
            holder.rating_ll.setVisibility(View.VISIBLE);
            holder.rating.setVisibility(View.GONE);
        }else {
            holder.rating_ll.setVisibility(View.GONE);
            holder.rating.setVisibility(View.VISIBLE);
        }
        holder.reorder_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRes(orderses.get(position).res_id);
//                myAccountFragment.add_to_cart(orderses.get(position).product, orderses.get(position).q,et_spl_request.getText().toString());
            }
        });
        holder.rating_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAccountFragment.get_order_id(orderses.get(position).id);
//                alert = new Dialog(context);
//                View view1 = inflater.inflate(R.layout.alert_dialog_screen, null);
//                alert.setContentView(view1);
//                alert.setCancelable(true);
//                alert.setTitle(Settings.getword(context,"rate_order"));
////                final RatingBar ratingBar = (RatingBar)alert.findViewById(R.id.ratingBar);
////                ratingBar.setIsIndicator(false);
//                final EditText comm=(EditText)view1.findViewById(R.id.write_comm_et);
//                LinearLayout submit=(LinearLayout)view1.findViewById(R.id.alert_submit_ll);
//                LinearLayout rating_ll=(LinearLayout)view1.findViewById(R.id.give_rating_ll);
//                set__give_rating(context, "0", rating_ll);
//                submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        rate=ratingBar.getRating();
//                        Log.e("rating", String.valueOf(rate));
//                        write=comm.getText().toString();
//                        Log.e("comments", write);
////                        holder.rating_ll.setVisibility(View.GONE);
////                        holder.rating.setVisibility(View.VISIBLE);
////                        Settings.set_rating(context, String.valueOf(rate), holder.rating);
//                        send_rating(position);
//
//                    }
//                });
//                alert.show();
            }
        });

        return rowView;

    }


    private void getRes(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Settings.getword(context, "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url;
        url = Settings.SERVERURL + "restaurants.php?rest_id=" + id;
        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("reponse", jsonArray.toString());
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tmp_json = jsonArray.getJSONObject(i);
                        Restaurants temp = new Restaurants(tmp_json.getString("id")
                                , tmp_json.getString("title"),
                                tmp_json.getString("title_ar"),
                                tmp_json.getString("area"),
                                tmp_json.getString("current_status"),
                                tmp_json.getString("hours"),
                                tmp_json.getString("time"),
                                tmp_json.getString("minimum"),
                                tmp_json.getString("image"),
                                tmp_json.getString("banner"),
                                tmp_json.getString("description"),
                                tmp_json.getString("description_ar"),
                                tmp_json.getString("small_description"),
                                tmp_json.getString("small_description_ar"),
                                tmp_json.getString("rating"),
                                tmp_json.getString("reviews"),
                                tmp_json.getJSONArray("payment"),
                                tmp_json.getJSONArray("category"),
                                tmp_json.getJSONArray("menu"),
                                tmp_json.getJSONArray("promotions"),
                                tmp_json.getJSONArray("all_reviews"));
//                        restaurantses.add(temp);
                        myAccountFragment.mCallBack.songselected(temp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();

            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}


