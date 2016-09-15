package in.yellowsoft.darabeel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Chinni on 04-05-2016.
 */
public class CompanyListFragment extends Fragment {
    CompanylistAdapter companylistAdapter;
    String id,head,type;
    MyTextView no_res;
    MyEditText search;
    String search1;
    boolean p1=false;
    boolean p2=false;
    ArrayList<Days> dayses;
    String sta="",pay1="",pay2="",rate="",date="",temp="";
    LinearLayout lv_ll,filter_ll,open_ll,busy_ll,close_ll,cash_ll,knet_ll,r1_ll,r2_ll,r3_ll,r4_ll,r5_ll,submit,reset_ll,d1,d2,d3,d4,d5,d6,d7,d8;
    ImageView drop_img,open_img,busy_img,close_img,cash_img,knet_img,r1_img,r2_img,r3_img,r4_img,r5_img,d1_im,d2_im,d3_im,d4_im,d5_im,d6_im,d7_im,d8_im;
    MyTextView open_tv,busy_tv,close_tv,cash_tv,knet_tv,sta_status,sta_rate,sta_pay,submit_tv,sta_del_date;
    LinearLayout s_ll;
    ListView time_list;
    DateAdapter dateAdapter;
    ImageView filter,search_img;
    boolean loaded=false;
    FragmentTouchListner mCallBack;
    ProgressBar progressBar;
    public interface FragmentTouchListner {
//        public void five_items();
//        public void text_back_butt(String header);
        public void filter(String head);
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
                    + " must implement Listner");
        }
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter,loaded);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.company_list_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
         head="Restaurants List";
//         mCallBack.text_back_butt(head);
        mCallBack.filter(head);
        dayses=new ArrayList<>();
        get_dates();
        drop_img=(ImageView)view.findViewById(R.id.drop_img);
        lv_ll=(LinearLayout)view.findViewById(R.id.lv_ll);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        type=(String)getArguments().getSerializable("cat");
        id=(String)getArguments().getSerializable("url");
        ListView listView=(ListView)view.findViewById(R.id.com_list);
        time_list=(ListView)view.findViewById(R.id.time_lv);
        dateAdapter=new DateAdapter(getActivity(),dayses);
        time_list.setAdapter(dateAdapter);
        sta_del_date=(MyTextView)view.findViewById(R.id.sta_del_date);
        sta_del_date.setText(Settings.getword(getActivity(), "text_delivery_time"));
        no_res=(MyTextView)view.findViewById(R.id.no_res);
        s_ll=(LinearLayout)view.findViewById(R.id.s_ll);
        search=(MyEditText) view.findViewById(R.id.search_com_list_et);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search1 = s.toString();
                if (search1.length() > 0) {
                    getResta();
                } else {
                    restaurants.clear();
                    companylistAdapter.notifyDataSetChanged();
                }
            }
        });
        drop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lv_ll.getVisibility()==View.VISIBLE){
                    lv_ll.setVisibility(View.GONE);
                    setListViewHeightBasedOnItems(time_list);
                }else{
                    lv_ll.setVisibility(View.VISIBLE);
                    setListViewHeightBasedOnItems(time_list);
                }
            }
        });
        search_img=(ImageView)view.findViewById(R.id.s_img);
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search1 = search.getText().toString();
                getResta();
            }
        });
        time_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                date=dayses.get(position).str;
                sta_del_date.setText(dayses.get(position).getTitle(getActivity()));
                lv_ll.setVisibility(View.GONE);
            }
        });
        filter_ll=(LinearLayout)view.findViewById(R.id.filter_lll);
        open_ll=(LinearLayout)view.findViewById(R.id.open_ll);
        busy_ll=(LinearLayout)view.findViewById(R.id.busy_ll);
        close_ll=(LinearLayout)view.findViewById(R.id.close_ll);
        cash_ll=(LinearLayout)view.findViewById(R.id.cash_lll);
        knet_ll=(LinearLayout)view.findViewById(R.id.knet_ll);
        r1_ll=(LinearLayout)view.findViewById(R.id.r1_ll);
        r2_ll=(LinearLayout)view.findViewById(R.id.r2_ll);
        r3_ll=(LinearLayout)view.findViewById(R.id.r3_ll);
        r4_ll=(LinearLayout)view.findViewById(R.id.r4_ll);
        r5_ll=(LinearLayout)view.findViewById(R.id.r5_ll);
        d1=(LinearLayout)view.findViewById(R.id.d1_ll);
        d2=(LinearLayout)view.findViewById(R.id.d2_ll);
        d3=(LinearLayout)view.findViewById(R.id.d3_ll);
        d4=(LinearLayout)view.findViewById(R.id.d4_ll);
        d5=(LinearLayout)view.findViewById(R.id.d5_ll);
        d6=(LinearLayout)view.findViewById(R.id.d6_ll);
        d7=(LinearLayout)view.findViewById(R.id.d7_ll);
        d8=(LinearLayout)view.findViewById(R.id.d8_ll);
        submit=(LinearLayout)view.findViewById(R.id.sub_ll);
        reset_ll=(LinearLayout)view.findViewById(R.id.reset_ll);

        open_img=(ImageView)view.findViewById(R.id.open_img);
        busy_img=(ImageView)view.findViewById(R.id.busy_img);
        close_img=(ImageView)view.findViewById(R.id.close_img);
        cash_img=(ImageView)view.findViewById(R.id.cash_im);
        knet_img=(ImageView)view.findViewById(R.id.knet_im);
        r1_img=(ImageView)view.findViewById(R.id.r1_im);
        r2_img=(ImageView)view.findViewById(R.id.r2_im);
        r3_img=(ImageView)view.findViewById(R.id.r3_im);
        r4_img=(ImageView)view.findViewById(R.id.r4_im);
        r5_img=(ImageView)view.findViewById(R.id.r5_im);
        d1_im=(ImageView)view.findViewById(R.id.d1_im);
        d2_im=(ImageView)view.findViewById(R.id.d2_im);
        d3_im=(ImageView)view.findViewById(R.id.d3_im);
        d4_im=(ImageView)view.findViewById(R.id.d4_im);
        d5_im=(ImageView)view.findViewById(R.id.d5_im);
        d6_im=(ImageView)view.findViewById(R.id.d6_im);
        d7_im=(ImageView)view.findViewById(R.id.d7_im);
        d8_im=(ImageView)view.findViewById(R.id.d8_im);

        sta_status=(MyTextView)view.findViewById(R.id.sta_status_pop_tv);
        sta_status.setText(Settings.getword(getActivity(),"choose_status"));
        sta_rate=(MyTextView)view.findViewById(R.id.sta_rate_tv);
        sta_rate.setText(Settings.getword(getActivity(),"rating"));
        sta_pay=(MyTextView)view.findViewById(R.id.sta_pay_tv);
        sta_pay.setText(Settings.getword(getActivity(),"choose_payment"));
        open_tv=(MyTextView)view.findViewById(R.id.open_tv);
        open_tv.setText(Settings.getword(getActivity(),"status_open"));
        busy_tv=(MyTextView)view.findViewById(R.id.busy_tv);
        busy_tv.setText(Settings.getword(getActivity(),"status_busy"));
        close_tv=(MyTextView)view.findViewById(R.id.close_tv);
        close_tv.setText(Settings.getword(getActivity(),"status_close"));
        cash_tv=(MyTextView)view.findViewById(R.id.cash_tvv);
        cash_tv.setText(Settings.getword(getActivity(),"cash"));
        knet_tv=(MyTextView)view.findViewById(R.id.knet_tvv);
        knet_tv.setText(Settings.getword(getActivity(),"knet"));
        submit_tv=(MyTextView)view.findViewById(R.id.sub_pop);
        submit_tv.setText(Settings.getword(getActivity(), "submit"));


        reset_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sta="";rate="";date="";pay1="";pay2="";
                p1=false; p2=false;
                sta_del_date.setText(Settings.getword(getActivity(), "text_delivery_time"));
                open_img.setImageResource(R.drawable.ic_option_brown);
                busy_img.setImageResource(R.drawable.ic_option_brown);
                close_img.setImageResource(R.drawable.ic_option_brown);
                cash_img.setImageResource(R.drawable.ic_option_brown);
                knet_img.setImageResource(R.drawable.ic_option_brown);
                r1_img.setImageResource(R.drawable.ic_option_brown);
                r2_img.setImageResource(R.drawable.ic_option_brown);
                r3_img.setImageResource(R.drawable.ic_option_brown);
                r4_img.setImageResource(R.drawable.ic_option_brown);
                r5_img.setImageResource(R.drawable.ic_option_brown);
                d1_im.setImageResource(R.drawable.ic_option_brown);
                d2_im.setImageResource(R.drawable.ic_option_brown);
                d3_im.setImageResource(R.drawable.ic_option_brown);
                d4_im.setImageResource(R.drawable.ic_option_brown);
                d5_im.setImageResource(R.drawable.ic_option_brown);
                d6_im.setImageResource(R.drawable.ic_option_brown);
                d7_im.setImageResource(R.drawable.ic_option_brown);
                d8_im.setImageResource(R.drawable.ic_option_brown);


//                    String constraint = sta + "@_@" + pay + "@_@" + pay + "@_@" + rate;
//                    companylistAdapter.getFilter().filter(null);
                get_filter_Resta();
//                    filter_ll.setVisibility(View.GONE);
            }
        });
        open_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sta="Open";
                open_img.setImageResource(R.drawable.ic_option_pink);
                busy_img.setImageResource(R.drawable.ic_option_brown);
                close_img.setImageResource(R.drawable.ic_option_brown);
            }
        });
        close_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sta="Closed";
                open_img.setImageResource(R.drawable.ic_option_brown);
                busy_img.setImageResource(R.drawable.ic_option_brown);
                close_img.setImageResource(R.drawable.ic_option_pink);
            }
        });
        busy_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sta="Busy";
                open_img.setImageResource(R.drawable.ic_option_brown);
                busy_img.setImageResource(R.drawable.ic_option_pink);
                close_img.setImageResource(R.drawable.ic_option_brown);
            }
        });
        cash_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1){
                    pay1="";
                    cash_img.setImageResource(R.drawable.ic_option_brown);
                    p1=false;
                }else{
                    pay1="1";
                    cash_img.setImageResource(R.drawable.ic_option_pink);
                    p1=true;
            }}
        });
        knet_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2){
                    pay2="";
                    knet_img.setImageResource(R.drawable.ic_option_brown);
                    p2=false;
                }else {
                    pay2 = "2";
                    knet_img.setImageResource(R.drawable.ic_option_pink);
                    p2 = true;
                }
            }
        });
        if(p1&&p2){
            pay1="1";
            pay2="2";
        }
        r1_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate = "1";
                r1_img.setImageResource(R.drawable.ic_option_pink);
                r2_img.setImageResource(R.drawable.ic_option_brown);
                r3_img.setImageResource(R.drawable.ic_option_brown);
                r4_img.setImageResource(R.drawable.ic_option_brown);
                r5_img.setImageResource(R.drawable.ic_option_brown);
            }
        });
        r2_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate = "2";
                r1_img.setImageResource(R.drawable.ic_option_brown);
                r2_img.setImageResource(R.drawable.ic_option_pink);
                r3_img.setImageResource(R.drawable.ic_option_brown);
                r4_img.setImageResource(R.drawable.ic_option_brown);
                r5_img.setImageResource(R.drawable.ic_option_brown);
            }
        });
        r3_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate = "3";
                r1_img.setImageResource(R.drawable.ic_option_brown);
                r2_img.setImageResource(R.drawable.ic_option_brown);
                r3_img.setImageResource(R.drawable.ic_option_pink);
                r4_img.setImageResource(R.drawable.ic_option_brown);
                r5_img.setImageResource(R.drawable.ic_option_brown);
            }
        });
        r4_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate = "4";
                r1_img.setImageResource(R.drawable.ic_option_brown);
                r2_img.setImageResource(R.drawable.ic_option_brown);
                r3_img.setImageResource(R.drawable.ic_option_brown);
                r4_img.setImageResource(R.drawable.ic_option_pink);
                r5_img.setImageResource(R.drawable.ic_option_brown);
            }
        });
        r5_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate = "5";
                r1_img.setImageResource(R.drawable.ic_option_brown);
                r2_img.setImageResource(R.drawable.ic_option_brown);
                r3_img.setImageResource(R.drawable.ic_option_brown);
                r4_img.setImageResource(R.drawable.ic_option_brown);
                r5_img.setImageResource(R.drawable.ic_option_pink);
            }
        });
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date="1";
                d1_im.setImageResource(R.drawable.ic_option_pink);
                d2_im.setImageResource(R.drawable.ic_option_brown);
                d3_im.setImageResource(R.drawable.ic_option_brown);
                d4_im.setImageResource(R.drawable.ic_option_brown);
                d5_im.setImageResource(R.drawable.ic_option_brown);
                d6_im.setImageResource(R.drawable.ic_option_brown);
                d7_im.setImageResource(R.drawable.ic_option_brown);
                d8_im.setImageResource(R.drawable.ic_option_brown);
            }
        });
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date="2";
                d1_im.setImageResource(R.drawable.ic_option_brown);
                d2_im.setImageResource(R.drawable.ic_option_pink);
                d3_im.setImageResource(R.drawable.ic_option_brown);
                d4_im.setImageResource(R.drawable.ic_option_brown);
                d5_im.setImageResource(R.drawable.ic_option_brown);
                d6_im.setImageResource(R.drawable.ic_option_brown);
                d7_im.setImageResource(R.drawable.ic_option_brown);
                d8_im.setImageResource(R.drawable.ic_option_brown);
            }
        });
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date="3";
                d1_im.setImageResource(R.drawable.ic_option_brown);
                d2_im.setImageResource(R.drawable.ic_option_brown);
                d3_im.setImageResource(R.drawable.ic_option_pink);
                d4_im.setImageResource(R.drawable.ic_option_brown);
                d5_im.setImageResource(R.drawable.ic_option_brown);
                d6_im.setImageResource(R.drawable.ic_option_brown);
                d7_im.setImageResource(R.drawable.ic_option_brown);
                d8_im.setImageResource(R.drawable.ic_option_brown);
            }
        });
        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date="4";
                d1_im.setImageResource(R.drawable.ic_option_brown);
                d2_im.setImageResource(R.drawable.ic_option_brown);
                d3_im.setImageResource(R.drawable.ic_option_brown);
                d4_im.setImageResource(R.drawable.ic_option_pink);
                d5_im.setImageResource(R.drawable.ic_option_brown);
                d6_im.setImageResource(R.drawable.ic_option_brown);
                d7_im.setImageResource(R.drawable.ic_option_brown);
                d8_im.setImageResource(R.drawable.ic_option_brown);
            }
        });
        d5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date="5";
                d1_im.setImageResource(R.drawable.ic_option_brown);
                d2_im.setImageResource(R.drawable.ic_option_brown);
                d3_im.setImageResource(R.drawable.ic_option_brown);
                d4_im.setImageResource(R.drawable.ic_option_brown);
                d5_im.setImageResource(R.drawable.ic_option_pink);
                d6_im.setImageResource(R.drawable.ic_option_brown);
                d7_im.setImageResource(R.drawable.ic_option_brown);
                d8_im.setImageResource(R.drawable.ic_option_brown);
            }
        });
        d6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date="6";
                d1_im.setImageResource(R.drawable.ic_option_brown);
                d2_im.setImageResource(R.drawable.ic_option_brown);
                d3_im.setImageResource(R.drawable.ic_option_brown);
                d4_im.setImageResource(R.drawable.ic_option_brown);
                d5_im.setImageResource(R.drawable.ic_option_brown);
                d6_im.setImageResource(R.drawable.ic_option_pink);
                d7_im.setImageResource(R.drawable.ic_option_brown);
                d8_im.setImageResource(R.drawable.ic_option_brown);
            }
        });
        d7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date="7";
                d1_im.setImageResource(R.drawable.ic_option_brown);
                d2_im.setImageResource(R.drawable.ic_option_brown);
                d3_im.setImageResource(R.drawable.ic_option_brown);
                d4_im.setImageResource(R.drawable.ic_option_brown);
                d5_im.setImageResource(R.drawable.ic_option_brown);
                d6_im.setImageResource(R.drawable.ic_option_brown);
                d7_im.setImageResource(R.drawable.ic_option_pink);
                d8_im.setImageResource(R.drawable.ic_option_brown);
            }
        });
        d8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date="8";
                d1_im.setImageResource(R.drawable.ic_option_brown);
                d2_im.setImageResource(R.drawable.ic_option_brown);
                d3_im.setImageResource(R.drawable.ic_option_brown);
                d4_im.setImageResource(R.drawable.ic_option_brown);
                d5_im.setImageResource(R.drawable.ic_option_brown);
                d6_im.setImageResource(R.drawable.ic_option_brown);
                d7_im.setImageResource(R.drawable.ic_option_brown);
                d8_im.setImageResource(R.drawable.ic_option_pink);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (sta == null && pay == null && rate == null) {
//                    filter_ll.setVisibility(View.GONE);
//                } else {
//                    String constraint = sta + "@_@" + pay + "@_@" + pay + "@_@" + rate;
//                    companylistAdapter.getFilter().filter(constraint);
                get_filter_Resta();
                filter_ll.setVisibility(View.GONE);

            }
        });
        setListViewHeightBasedOnItems(time_list);
        companylistAdapter=new CompanylistAdapter(getActivity(),restaurants);
//        mCallBack.five_items();
        listView.setAdapter(companylistAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.songselected(restaurants.get(position));
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        getRestaurants();
        if(filter_ll.getVisibility()==View.GONE){
        }

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    loaded = true;
                    return false;
                }
                return false;
            }
        });
        temp=Settings.getArea_id(getActivity());
        if(temp.equals("-1")){
            temp="";
        }
    }
    public  void filter(){
        if(filter_ll.getVisibility()==View.VISIBLE) {
            filter_ll.setVisibility(View.GONE);
        }
        else
            filter_ll.setVisibility(View.VISIBLE);
    }

    ArrayList<Restaurants> restaurants = new ArrayList<>();

    private void getRestaurants(){
        String url;
//        restaurants.clear();
        if (id.equals("cat")) {
            if(type.equals("0")){
                if(Settings.get_user_language(getActivity()).equals("ar")){
                    url = Settings.SERVERURL + "restaurants.php?lang=ar&" + "area_id=" + Settings.getArea_id(getActivity());
                }else {
                    url = Settings.SERVERURL + "restaurants.php?" + "area_id=" + Settings.getArea_id(getActivity());
                }
                Log.e("url", url);
            }else {
                if(Settings.get_user_language(getActivity()).equals("ar")) {
                    url = Settings.SERVERURL + "restaurants.php?lang=ar&" + "category_id=" + type + "&area_id=" + Settings.getArea_id(getActivity());
                }else{
                    url = Settings.SERVERURL + "restaurants.php?" + "category_id=" + type + "&area_id=" + Settings.getArea_id(getActivity());
                }
                Log.e("url", url);
            }
        }else{
            if(Settings.get_user_language(getActivity()).equals("ar")) {
                url = Settings.SERVERURL + "restaurants.php?lang=ar&type="+type;
            }else {
                url = Settings.SERVERURL + "restaurants.php?type="+type;
            }

            Log.e("url", url);
        }
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);
                display_dat(jsonArray);
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

    private void display_dat(JSONArray jsonArray){
        Log.e("reponse", jsonArray.toString());
        restaurants.clear();
        try {
            for (int i=0;i<jsonArray.length();i++){
                JSONObject tmp_json = jsonArray.getJSONObject(i);
                Restaurants temp = new Restaurants(tmp_json.getString("id")
                        ,tmp_json.getString("title"),
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
                restaurants.add(temp);
            }
            if(restaurants.size()==0){
                no_res.setVisibility(View.VISIBLE);
                no_res.setText("No Restaurants near to your selected area");
            }
            companylistAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void getResta(){
        String url;

        progressBar.setVisibility(View.VISIBLE);
        try {
            if(Settings.get_user_language(getActivity()).equals("ar")) {
                url = Settings.SERVERURL + "restaurants.php?lang=ar&search=" + URLEncoder.encode(search1, "utf-8");
            }else {
                url = Settings.SERVERURL + "restaurants.php?search=" + URLEncoder.encode(search1, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            if(Settings.get_user_language(getActivity()).equals("ar")) {
                url = Settings.SERVERURL + "restaurants.php?lang=ar&search="+search1;
            }else {
                url = Settings.SERVERURL + "restaurants.php?search="+search1;
            }

            e.printStackTrace();
        }
        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);
                display_data(jsonArray);
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
        AppController.getInstance().cancelPendingRequests("search");
        AppController.getInstance().addToRequestQueue(jsObjRequest,"search");
    }

    private void display_data(JSONArray jsonArray){
        Log.e("reponse", jsonArray.toString());
        try {
            restaurants.clear();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject tmp_json = jsonArray.getJSONObject(i);
                Restaurants temp = new Restaurants(tmp_json.getString("id")
                        ,tmp_json.getString("title"),
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
                restaurants.add(temp);
            }
            companylistAdapter.notifyDataSetChanged();
            if(restaurants.size()==0){
                no_res.setVisibility(View.VISIBLE);
                no_res.setText(Settings.getword(getActivity(), "no_products"));
            }
            else
                no_res.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void get_filter_Resta(){
        String url = null;
//        restaurants.clear();
        progressBar.setVisibility(View.VISIBLE);

        try {
            if(Settings.get_user_language(getActivity()).equals("ar")) {
                url = Settings.SERVERURL + "restaurants.php?lang=ar&area_id=" +temp +"&cur_status="+ URLEncoder.encode(sta, "utf-8")+
                        "&del_time="+ URLEncoder.encode(date, "utf-8")+ "&payment_type1="+ URLEncoder.encode(pay1, "utf-8")+
                        "&payment_type2="+ URLEncoder.encode(pay2, "utf-8");
            }else {
                url = Settings.SERVERURL + "restaurants.php?area_id=" +temp +"&cur_status="+ URLEncoder.encode(sta, "utf-8")+
                        "&del_time="+ URLEncoder.encode(date, "utf-8")+ "&payment_type1="+ URLEncoder.encode(pay1, "utf-8")+
                        "&payment_type2="+ URLEncoder.encode(pay2, "utf-8");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url", url);

//        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);
                display_data1(jsonArray);
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
        AppController.getInstance().cancelPendingRequests("search");
        AppController.getInstance().addToRequestQueue(jsObjRequest, "search");
    }

    private void display_data1(JSONArray jsonArray){
        Log.e("reponse", jsonArray.toString());
        try {
            restaurants.clear();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject tmp_json = jsonArray.getJSONObject(i);
                Restaurants temp = new Restaurants(tmp_json.getString("id")
                        ,tmp_json.getString("title"),
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
                if(!rate.equals("")){
//                    companylistAdapter.getFilter().filter(rate);
                    if(Float.parseFloat(tmp_json.getString("rating"))>=Float.parseFloat(rate)){
                        restaurants.add(temp);
                    }else{
                        no_res.setVisibility(View.VISIBLE);
                    }

                }else{
                    restaurants.add(temp);
                }


            }
            companylistAdapter.notifyDataSetChanged();
//            if(!rate.equals("")){
//                companylistAdapter.getFilter().filter(rate);
//            }
            if(restaurants.size()==0){
                no_res.setVisibility(View.VISIBLE);
                no_res.setText(Settings.getword(getActivity(), "no_products"));
            }
            else
                no_res.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void get_dates() {
        String url = null;
        try {
            url = Settings.SERVERURL + "delivery_times.php";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
//                dayses.clear();
                Log.e("orders response is: ", jsonArray.toString());

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject sub = jsonArray.getJSONObject(i);
//                            String id = jsonArray.getJSONObject(i).getString("id");
//                            String area = jsonArray.getJSONObject(i).getString("title");
//                            String area_ar = jsonArray.getJSONObject(i).getString("title_ar");
                            Days person = new Days(jsonArray.getJSONObject(i));
                            dayses.add(person);

                    }
                    dateAdapter.notifyDataSetChanged();
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
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
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