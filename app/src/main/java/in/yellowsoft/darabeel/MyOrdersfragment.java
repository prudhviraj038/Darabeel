package in.yellowsoft.darabeel;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrdersfragment extends Fragment {
    String head;
    TextView res_name, sta_ord_id, sta_ord_date, Sta_ord_name, sta_ord_mobile, sta_ord_area, sta_ord_block, sta_ord_street, sta_ord_bilding,
            sta_discount_oreder, discount_order, sta_or_det_floor, sta_or_det_flat, or_det_floor,or_det_flat,sta_sub_total,sta_dara, sta_dc, sta_grand_total, ord_date, ord_id, ord_name,
            ord_mobile, ord_area, ord_block, ord_street, ord_building, ord_pay_mode, ord_sub_total, ord_dc,dara, ord_grand_total,delivery_date,
            sta_delivery_date,sta_reorder;
    ViewFlipper viewFlipper;
    ArrayList<JsonOrders> orderses;
    FragmentTouchListner mCallBack;
    ListView order_list, ord_detail_list;
    OrderAdapter orderAdapter;
    OrderDetailsAdapter orderDetailsAdapter;
    boolean loaded=false;
    ProgressBar progressBar;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void songselected(Restaurants restaurant);
        public  Animation get_animation(Boolean enter,Boolean loaded);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listneddr");
        }
    }
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        return mCallBack.get_animation(enter,loaded);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_orders_page, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
//        mCallBack.text_back_butt(head);
//        head = Settings.getword(getActivity(), "my_orders");
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar5);
        orderses = new ArrayList<>();
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper7);
        sta_ord_id = (TextView) view.findViewById(R.id.sta_order_id);
        sta_ord_id.setText(Settings.getword(getActivity(), "order_id"));
        sta_ord_date = (TextView) view.findViewById(R.id.sta_order_date);
        sta_ord_date.setText(Settings.getword(getActivity(), "order_date"));
        sta_delivery_date = (TextView) view.findViewById(R.id.sta_delivery_date);
        sta_delivery_date.setText(Settings.getword(getActivity(), "selecte_delivery_date"));
        Sta_ord_name = (TextView) view.findViewById(R.id.sta_or_det_name);
        Sta_ord_name.setText(Settings.getword(getActivity(), "contact_us_name"));
        sta_ord_mobile = (TextView) view.findViewById(R.id.sta_or_det_number);
        sta_ord_mobile.setText(Settings.getword(getActivity(), "mobile"));
        sta_ord_area = (TextView) view.findViewById(R.id.sta_or_det_area);
        sta_ord_area.setText(Settings.getword(getActivity(), "area"));
        sta_ord_block = (TextView) view.findViewById(R.id.sta_or_det_block);
        sta_ord_block.setText(Settings.getword(getActivity(), "block"));
        sta_ord_street = (TextView) view.findViewById(R.id.sta_or_det_street);
        sta_ord_street.setText(Settings.getword(getActivity(), "street_name"));
        sta_ord_bilding = (TextView) view.findViewById(R.id.sta_or_det_building);
        sta_ord_bilding.setText(Settings.getword(getActivity(), "building_name"));
        sta_or_det_floor = (TextView) view.findViewById(R.id.sta_or_det_floor);
        sta_or_det_floor.setText(Settings.getword(getActivity(), "floor_number"));
        sta_or_det_flat = (TextView) view.findViewById(R.id.sta_or_det_flat);
        sta_or_det_flat.setText(Settings.getword(getActivity(), "flat_number"));
        sta_sub_total = (TextView) view.findViewById(R.id.sta_sub_total_details);
        sta_sub_total.setText(Settings.getword(getActivity(), "sub_total"));
        sta_dc = (TextView) view.findViewById(R.id.sta_res_dc_details);
        sta_dc.setText(Settings.getword(getActivity(), "delivery_charges"));
        sta_grand_total = (TextView) view.findViewById(R.id.sta_grand_tot_details);
        sta_grand_total.setText(Settings.getword(getActivity(), "grand_total"));
        sta_discount_oreder = (TextView) view.findViewById(R.id.sta_discount_oreder);
        sta_discount_oreder.setText(Settings.getword(getActivity(), "discount"));
        sta_dara = (TextView) view.findViewById(R.id.sta_dara_charges);
        sta_dara.setText(Settings.getword(getActivity(), "darabeel_charges"));



        ord_date = (TextView) view.findViewById(R.id.order_dat);
        delivery_date = (TextView) view.findViewById(R.id.delivery_dat);
        ord_id = (TextView) view.findViewById(R.id.order_id_details);
        ord_name = (TextView) view.findViewById(R.id.or_det_name);
        ord_mobile = (TextView) view.findViewById(R.id.or_det_number);
        ord_area = (TextView) view.findViewById(R.id.or_det_area);
        ord_block = (TextView) view.findViewById(R.id.or_det_block);
        ord_street = (TextView) view.findViewById(R.id.or_det_street);
        ord_building = (TextView) view.findViewById(R.id.or_det_building);
        or_det_floor = (TextView) view.findViewById(R.id.or_det_floor);
        or_det_flat = (TextView) view.findViewById(R.id.or_det_flat);
        ord_sub_total = (TextView) view.findViewById(R.id.sub_total_details);
        ord_dc = (TextView) view.findViewById(R.id.res_dc_details);
        ord_grand_total = (TextView) view.findViewById(R.id.grand_tot_details);
        discount_order = (TextView) view.findViewById(R.id.discount_order);
        dara = (TextView) view.findViewById(R.id.dara_charges);


        get_orders();
        order_list = (ListView) view.findViewById(R.id.order_list);
        orderAdapter = new OrderAdapter(getActivity(), orderses, this);
        order_list.setAdapter(orderAdapter);
        ord_detail_list = (ListView) view.findViewById(R.id.item_list_details);
        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    gotodetails(i);
                int posi=i;
                String temp=orderses.get(i).deli_date+" "+orderses.get(i).deli_time;
                viewFlipper.setDisplayedChild(1);
                ord_date.setText(orderses.get(i).date);
                delivery_date.setText(temp);
                ord_id.setText(orderses.get(i).id);
                ord_name.setText(orderses.get(i).fname + " " + orderses.get(i).lname);
                ord_mobile.setText(orderses.get(i).phone);
                ord_area.setText(orderses.get(i).get_area(getActivity()));
                ord_block.setText(orderses.get(i).block);
                ord_street.setText(orderses.get(i).street);
                ord_building.setText(orderses.get(i).building);
                or_det_floor.setText(orderses.get(i).flor);
                or_det_flat.setText(orderses.get(i).flat);
                ord_sub_total.setText(orderses.get(i).pricee + " KD");
                ord_dc.setText(orderses.get(i).del_charges + " KD");
                ord_grand_total.setText(orderses.get(i).tot_price + " KD");
                discount_order.setText(orderses.get(i).dis_amount + " KD");
                dara.setText(orderses.get(i).dara + " KD");
                Log.e("size", String.valueOf(orderses.get(i).product.size()));
                orderDetailsAdapter = new OrderDetailsAdapter(getActivity(), orderses, posi);

                ord_detail_list.setAdapter(orderDetailsAdapter);
                setListViewHeightBasedOnItems(ord_detail_list);
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.setDisplayedChild(0);
                        return true;
                    }
                }
                return false;
            }
        });
    }
    public void get_orders(){
        String url=Settings.SERVERURL+"order-history.php?member_id="+Settings.getUserid(getActivity());
        Log.e("url--->", url);
        progressBar.setVisibility(View.VISIBLE);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
//                if(progressDialog!=null)
//                    progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                Log.e("products",jsonArray.toString());
                orderses.clear();
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JsonOrders temp_product = new JsonOrders(jsonObject);
                        orderses.add(temp_product);
                        orderAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                progressBar.setVisibility(View.GONE);

            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
//
//    public void gotodetails(int position){
//        posi=position;
//
//    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }
    }
}
