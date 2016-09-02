package in.yellowsoft.darabeel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 04-05-2016.
 */
public class CompanyPageFragment extends Fragment {
    Restaurants restaurants;

    boolean loaded=false;
    AlertDialogManager alert = new AlertDialogManager();
    ImageView company_logo,review_res_logo;
    MyTextView about_company,com_area,com_status,com_cuisines,com_wrk_hours,about_tab_tv,other_info_tab_tv,
            com_delivery_time,com_min_order,com_delivery_charges,sta_res_info,sta_about_title,sta_area,sta_status,sta_hours,
            sta_time,sta_min,sta_dc,sta_cuisines,sta_payment,sta_promotion,sta_show,review,area_tv;
    LinearLayout show_menu,ll_promotion,rating,com_payment_type,tab_about_ll,tab_other_ll,about_ll,other_ll,review_rating,review_ll,area_ll;
    FragmentTouchListner mCallBack;
    AreaAdapter personAdapter ;
    String head;
    ArrayList<Area> area_list;
    ListView listView;
    ViewFlipper viewFlipper;
    public interface FragmentTouchListner {
        public void showmenu(Restaurants restaurants);
//        public void five_items();
        public void text_back_butt(String header);
        public void clear_cart();
        public void product_list(String rest_id,String cat_id,String cat_name,String type);
        public  Animation get_animation(Boolean enter,Boolean loaded);
        public void area_list(String id,Restaurants restaurants);
        public void to_promotions(Restaurants restaurants);
        public void reviews_page(Restaurants restaurants);
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
       return mCallBack.get_animation(enter,loaded);
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
                    + " must implement LogoutUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.restaurant_page_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        restaurants = (Restaurants)getArguments().getSerializable("restaurant");
        Log.e("r_id", restaurants.res_id);
//        mCallBack.five_items();
        head=restaurants.getTitle(getActivity());
        mCallBack.text_back_butt(head);
        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper5);

        Log.e("res_name", restaurants.getTitle(getActivity()));
        rating=(LinearLayout)view.findViewById(R.id.rating_com_page);
        Settings.set_rating(getActivity(), restaurants.rating, rating);

        tab_about_ll=(LinearLayout)view.findViewById(R.id.about_tab_ll);
        tab_other_ll=(LinearLayout)view.findViewById(R.id.other_info_tab_ll);
        about_ll=(LinearLayout)view.findViewById(R.id.ll_res_about);
        other_ll=(LinearLayout)view.findViewById(R.id.ll_res_other_info);
        about_tab_tv=(MyTextView)view.findViewById(R.id.about_tab);
        about_tab_tv.setText(Settings.getword(getActivity(), "about"));
        other_info_tab_tv=(MyTextView)view.findViewById(R.id.other_info);
        other_info_tab_tv.setText(Settings.getword(getActivity(), "restaurant_info"));
        sta_about_title=(MyTextView)view.findViewById(R.id.sta_about_restaurant);
        sta_about_title.setText(Settings.getword(getActivity(), "about" )+ " " + Html.fromHtml(restaurants.getTitle(getActivity())));
        sta_res_info=(MyTextView)view.findViewById(R.id.sta_resta_info);
        sta_res_info.setText(Settings.getword(getActivity(), "restaurant_info"));
        sta_area=(MyTextView)view.findViewById(R.id.sat_area_cpage);
        sta_area.setText(Settings.getword(getActivity(),"area"));
        sta_status=(MyTextView)view.findViewById(R.id.sat_status_cpage);
        sta_status.setText(Settings.getword(getActivity(),"text_status"));
        sta_cuisines=(MyTextView)view.findViewById(R.id.sat_cuisines_cpage);
        sta_cuisines.setText(Settings.getword(getActivity(),"text_cuisines"));
        sta_hours=(MyTextView)view.findViewById(R.id.sat_hours_cpage);
        sta_hours.setText(Settings.getword(getActivity(),"text_working_hours"));
        sta_time=(MyTextView)view.findViewById(R.id.sat_time_cpage);
        sta_time.setText(Settings.getword(getActivity(),"text_delivery_time"));
        sta_dc=(MyTextView)view.findViewById(R.id.sat_dc_cpage);
        sta_dc.setText(Settings.getword(getActivity(),"delivery_charges"));
        sta_min=(MyTextView)view.findViewById(R.id.sat_min_cpage);
        sta_min.setText(Settings.getword(getActivity(),"min_order_amount"));
        sta_payment=(MyTextView)view.findViewById(R.id.sat_payment_cpage);
        sta_payment.setText(Settings.getword(getActivity(),"text_payment_type"));
        sta_promotion=(MyTextView)view.findViewById(R.id.tv_Promotions);
        sta_promotion.setText(Settings.getword(getActivity(),"promotions"));
        sta_show=(MyTextView)view.findViewById(R.id.tv_show_menu);
        sta_show.setText(Settings.getword(getActivity(),"show_menu"));
        com_area=(MyTextView)view.findViewById(R.id.area);
        area_tv=(MyTextView)view.findViewById(R.id.select_area_tv);
        area_ll=(LinearLayout)view.findViewById(R.id.select_area_ll);
        if(Settings.getArea_id(getActivity()).equals("-1")){
            area_tv.setText(Settings.getword(getActivity(),"select_area"));
            com_area.setText(Settings.getword(getActivity(),"please_area"));
            area_ll.setVisibility(View.VISIBLE);
        }else {
            area_tv.setText(Settings.getArea_name(getActivity()));
            com_area.setText(Settings.getArea_name(getActivity()));
            area_ll.setVisibility(View.GONE);
        }
        review=(MyTextView)view.findViewById(R.id.reviews_com_page);
        review.setText(Settings.getword(getActivity(), "review"));

        company_logo=(ImageView)view.findViewById(R.id.resta_img);
        Picasso.with(getActivity()).load(restaurants.image).into(company_logo);
        tab_other_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about_ll.setVisibility(View.GONE);
                other_ll.setVisibility(View.VISIBLE);
                tab_other_ll.setBackgroundResource(R.color.brown);
                other_info_tab_tv.setTextColor(Color.parseColor("#ffffff"));
                tab_about_ll.setBackgroundColor(0x00000000);
                about_tab_tv.setTextColor(Color.parseColor("#512918"));
            }
        });
        tab_about_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about_ll.setVisibility(View.VISIBLE);
                other_ll.setVisibility(View.GONE);
                tab_other_ll.setBackgroundColor(0x00000000);
                other_info_tab_tv.setTextColor(Color.parseColor("#512918"));
                tab_about_ll.setBackgroundResource(R.color.brown);
                about_tab_tv.setTextColor(Color.parseColor("#ffffff"));
            }
        });

        area_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getarea();
                viewFlipper.setDisplayedChild(1);
//                mCallBack.area_list(restaurants.res_id,restaurants);
            }
        });
        show_menu=(LinearLayout)view.findViewById(R.id.show_menu);
        show_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Settings.getArea_id(getActivity()).equals("-1")) {
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_area"), false);
                } else {
                    if (restaurants.menu.size() == 0)
                        mCallBack.product_list(restaurants.res_id,restaurants.res_id,restaurants.getTitle(getActivity()),"1");
//                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "no_prodcts_in_restaurant"), false);
                    else
                        mCallBack.showmenu(restaurants);
                }
            }
        });
        review_ll=(LinearLayout)view.findViewById(R.id.review_ll);
        review_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mCallBack.reviews_page(restaurants);
            }
        });
        about_company=(MyTextView)view.findViewById(R.id.about_restauarnt);
        about_company.setText(Html.fromHtml(restaurants.getdescription(getActivity())));
        ll_promotion=(LinearLayout)view.findViewById(R.id.ll_Promotions);
        if (restaurants.promotions.size()>0){
            ll_promotion.setVisibility(View.VISIBLE);
        }else{
            ll_promotion.setVisibility(View.GONE);
        }
        ll_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.getArea_id(getActivity()).equals("-1")) {
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_area"), false);
                } else {
                    mCallBack.to_promotions(restaurants);
                }
            }
        });

//        com_area.setText(Settings.getword(getActivity(),"please_area"));
        area_list = new ArrayList<>();
        ListView area_listView = (ListView)view.findViewById(R.id.com_list_view);
        personAdapter = new AreaAdapter(getActivity(), area_list);
        area_listView.setAdapter(personAdapter);
        area_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewFlipper.setDisplayedChild(0);
                Settings.setSignup_Area_id(getActivity(), area_list.get(position).getId(), area_list.get(position).getArea(getActivity()), area_list.get(position).getArea(getActivity()));
                com_area.setText(Settings.getArea_name(getActivity()));
                area_tv.setText(Settings.getArea_name(getActivity()));
                delivery_charges();
            }
        });
        com_status=(MyTextView)view.findViewById(R.id.status);
        com_status.setText(restaurants.status);
        com_cuisines=(MyTextView)view.findViewById(R.id.cuisines);
        String temp="";
        for(int i=0;i<restaurants.category.size();i++){
            if(i==0)
                temp = restaurants.category.get(i).getTitle(getActivity());
            else
                temp=temp+","+restaurants.category.get(i).getTitle(getActivity());
        }
        com_cuisines.setText(temp);


        com_wrk_hours=(MyTextView)view.findViewById(R.id.working_hours);
        com_wrk_hours.setText(restaurants.hours);
        com_delivery_time=(MyTextView)view.findViewById(R.id.delivery_time);
        com_delivery_time.setText(restaurants.time);
        com_min_order=(MyTextView)view.findViewById(R.id.minimum_order);
        com_min_order.setText(restaurants.min+" KD ");
        com_delivery_charges=(MyTextView)view.findViewById(R.id.rest_deli_charges);
        delivery_charges();

        com_payment_type=(LinearLayout)view.findViewById(R.id.payment_type);
        for(int i=0;i<restaurants.payment.size();i++){
            ImageView temp_img = new ImageView(getActivity());
            temp_img.setAdjustViewBounds(true);
            Picasso.with(getActivity()).load(restaurants.payment.get(i).image).into(temp_img);
            com_payment_type.addView(temp_img);
        }
        mCallBack.clear_cart();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.setDisplayedChild(0);
                        return true;
                    }else{
                        loaded = true;
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public void dis_area(){

    }

    public void delivery_charges(){
        String url;
        if(Settings.getArea_id(getActivity()).equals("-1")) {
            com_delivery_charges.setText("0.000" + " KD ");
        }else {

            url = Settings.SERVERURL + "charges.php?" + "rest_id=" + restaurants.res_id + "&area=" + Settings.getArea_id(getActivity());
            Log.e("url--->", url);
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
            progressDialog.setCancelable(false);
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Log.e("orders response is: ", jsonObject.toString());
                    try {
                        String deliv_charges = jsonObject.getString("price");
                        com_delivery_charges.setText(deliv_charges + " KD ");
                        Settings.setDelivery_charges(getActivity(), deliv_charges);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Log.e("response is:", error.toString());
                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "server_not_connected"), Toast.LENGTH_SHORT).show();
                    if (progressDialog != null)
                        progressDialog.dismiss();
                }

            });

// Access the RequestQueue through your singleton class.
            AppController.getInstance().addToRequestQueue(jsObjRequest);
        }
    }
    private void getarea() {
        String url = null;
        try {
            url = Settings.SERVERURL + "areas.php?member_id="+Settings.getUserid(getActivity())+"&rest_id="+restaurants.res_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                Log.e("orders response is: ", jsonArray.toString());

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if(i==0){
                            JSONObject sub = jsonArray.getJSONObject(i);
                            String id = jsonArray.getJSONObject(i).getString("id");
                            String area = jsonArray.getJSONObject(i).getString("title");
                            String area_ar = jsonArray.getJSONObject(i).getString("title_ar");
                            Area person = new Area(id,area,area_ar,true);


                            Log.e("titleee", sub.getString("title"));

                            JSONArray jsonArray1=sub.getJSONArray("areas");
                            if(jsonArray1.length()>0){
                                area_list.add(person);
                            }
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                String idt = jsonArray1.getJSONObject(j).getString("id");
                                String areat = jsonArray1.getJSONObject(j).getString("title");
                                String areat_ar = jsonArray1.getJSONObject(j).getString("title_ar");
                                Area persont = new Area(idt,areat,areat_ar,false);
                                area_list.add(persont);

                            }
                        }else {
                            JSONObject sub = jsonArray.getJSONObject(i);
                            String id = jsonArray.getJSONObject(i).getString("id");
                            String area = jsonArray.getJSONObject(i).getString("title");
                            String area_ar = jsonArray.getJSONObject(i).getString("title_ar");
                            Area person = new Area(id, area, area_ar, true);
//                            area_list.add(person);

                            Log.e("titleee", sub.getString("title"));

                            JSONArray jsonArray1 = sub.getJSONArray("areas");
                            if(jsonArray1.length()>0) {
                                area_list.add(person);
                            }
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                String idt = jsonArray1.getJSONObject(j).getString("id");
                                String areat = jsonArray1.getJSONObject(j).getString("title");
                                String areat_ar = jsonArray1.getJSONObject(j).getString("title_ar");
                                Area persont = new Area(idt, areat, areat_ar, false);
                                area_list.add(persont);
                            }
                        }
                    }
                    personAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}