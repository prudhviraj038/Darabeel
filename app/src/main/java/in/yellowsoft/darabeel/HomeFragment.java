package in.yellowsoft.darabeel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.app.Fragment;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 04-05-2016.
 */
public class HomeFragment extends Fragment {
    HorizontalGridView horizontalGridView;
    GridElementAdapter gridElementAdapter;
    ArrayList<Restaurants> restaurantses;
    ArrayList<Category> categories;
    boolean loaded=false;
    LinearLayout ll_search_sweets,what_you_want,choose_area,l1,l2,l3,l4;
    MyTextView what_you_wanr_txt,area_txt,tv_search_sweets,we_recommend,select_cat,t1,t2,t3,t4;
    ImageView im1,im2,im3,im4;
    ListView cat_listView;
    CategoryAdapter categoryAdapter;
    ViewFlipper viewFlipper;
    Boolean isareaselected;
    AlertDialogManager alert = new AlertDialogManager();
    ArrayList<Area> area_list;
    AreaAdapter personAdapter ;
    String cat="0",head_home;
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter,loaded);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.home_fagment_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        Settings.setArea_id(getActivity(),"-1","","");
        horizontalGridView = (HorizontalGridView) view.findViewById(R.id.view5);
        cat_listView = (ListView) view.findViewById(R.id.category_listview);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        restaurantses = new ArrayList<>();
        categories = new ArrayList<>();
        head_home=Settings.getword(getActivity(),"home");
        mCallBack.home_head(head_home);
        getRestaurants();
        im1=(ImageView)view.findViewById(R.id.im1);
        im2=(ImageView)view.findViewById(R.id.im2);
        im3=(ImageView)view.findViewById(R.id.im3);
        im4=(ImageView)view.findViewById(R.id.im4);
        t1=(MyTextView)view.findViewById(R.id.t1);
        t2=(MyTextView)view.findViewById(R.id.t2);
        t3=(MyTextView)view.findViewById(R.id.t3);
        t4=(MyTextView)view.findViewById(R.id.t4);
        l1=(LinearLayout)view.findViewById(R.id.l1);
        l2=(LinearLayout)view.findViewById(R.id.l2);
        l3=(LinearLayout)view.findViewById(R.id.l3);
        l4=(LinearLayout)view.findViewById(R.id.l4);
//        gridElementAdapter = new GridElementAdapter(getActivity(),restaurantses,this);
//        horizontalGridView.setAdapter(gridElementAdapter);
        what_you_wanr_txt = (MyTextView) view.findViewById(R.id.tv_what_do_you_want);
        what_you_wanr_txt.setText(Settings.getword(getActivity(), "what_do_you_want"));
        select_cat = (MyTextView) view.findViewById(R.id.select_cat_text);
        select_cat.setText(Settings.getword(getActivity(), "title_select_category"));

        categoryAdapter = new CategoryAdapter(getActivity(), categories);
        cat_listView.setAdapter(categoryAdapter);
        cat_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                what_you_wanr_txt.setText(categories.get(i).getTitle(getActivity()));
                cat = categories.get(i).id;
                Log.e("cat", cat);
                viewFlipper.setDisplayedChild(0);
            }
        });
        area_txt = (MyTextView) view.findViewById(R.id.tv_choose_area);
//        if(Settings.getArea_id(getActivity()).equals("-1")){
            area_txt.setText(Settings.getword(getActivity(), "choose_area"));
//        }else{
//            area_txt.setText(Settings.getArea_name(getActivity()));
//        }
        area_list = new ArrayList<>();
        ListView area_listView = (ListView)view.findViewById(R.id.area_listView);
        personAdapter = new AreaAdapter(getActivity(), area_list);
        area_listView.setAdapter(personAdapter);
        area_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewFlipper.setDisplayedChild(0);
                Settings.setArea_id(getActivity(), area_list.get(position).getId(), area_list.get(position).getArea(getActivity()), area_list.get(position).getArea(getActivity()));
                area_txt.setText(Settings.getArea_name(getActivity()));
            }
        });
        tv_search_sweets = (MyTextView) view.findViewById(R.id.tv_search_sweets);
        tv_search_sweets.setText(Settings.getword(getActivity(), "choose"));
        ll_search_sweets = (LinearLayout) view.findViewById(R.id.ll_search_sweets);
        ll_search_sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Settings.getArea_id(getActivity()).equals("-1")) {
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_area"), false);
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_area"), Toast.LENGTH_SHORT).show();
                } else {
                    mCallBack.companyfragment(cat);
                }
            }
        });
        what_you_want = (LinearLayout) view.findViewById(R.id.ll_what_do_you_want);
        choose_area = (LinearLayout) view.findViewById(R.id.ll_choose_area);

        choose_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getarea();
                viewFlipper.setDisplayedChild(2);
            }
        });

        what_you_want.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategories();
                viewFlipper.setDisplayedChild(1);
            }
        });
        we_recommend = (MyTextView) view.findViewById(R.id.we_recommend);
        we_recommend.setText(Settings.getword(getActivity(), "trending"));

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 2) {
                        viewFlipper.setDisplayedChild(0);
                    } else if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.setDisplayedChild(0);
                    } else
                        return false;
//                            viewFlipper.setDisplayedChild(0);
                    return true;
                }
                return false;
            }
        });

    }

    private void getarea() {
        String url = null;
        try {
            url = Settings.SERVERURL + "areas.php?member_id="+Settings.getUserid(getActivity());
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
                area_list.clear();
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
                            area_list.add(person);

                            Log.e("titleee", sub.getString("title"));

                            JSONArray jsonArray1 = sub.getJSONArray("areas");
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



    private void display_data(JSONArray jsonArray){
        Log.e("reponse", jsonArray.toString());
                try {
                        for (int i=0;i<jsonArray.length();i++) {
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
                            restaurantses.add(temp);
                            if (restaurantses.size()==1) {
                                Picasso.with(getActivity()).load(restaurantses.get(0).image).placeholder(R.drawable.logo).into(im1);
                                t1.setText(restaurantses.get(0).getTitle(getActivity()));
                                l1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Settings.setArea_id(getActivity(),"-1","","");
                                        mCallBack.songselected(restaurantses.get(0));
                                    }
                                });
                            }else if(restaurantses.size()==2) {
                                Picasso.with(getActivity()).load(restaurantses.get(1).image).into(im2);
                                t2.setText(restaurantses.get(1).getTitle(getActivity()));
                                l2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Settings.setArea_id(getActivity(),"-1","","");
                                        mCallBack.songselected(restaurantses.get(1));
                                    }
                                });
                            }else if(restaurantses.size()==3) {
                                Settings.setArea_id(getActivity(),"-1","","");
                                Picasso.with(getActivity()).load(restaurantses.get(2).image).into(im3);
                                t3.setText(restaurantses.get(2).getTitle(getActivity()));
                                l3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mCallBack.songselected(restaurantses.get(2));
                                    }
                                });
                            }else if(restaurantses.size()==4) {
                                Settings.setArea_id(getActivity(),"-1","","");
                                Picasso.with(getActivity()).load(restaurantses.get(3).image).into(im4);
                                t4.setText(restaurantses.get(3).getTitle(getActivity()));
                                l4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mCallBack.songselected(restaurantses.get(3));
                                    }
                                });
                            }else {

                            }
                        }

//                    gridElementAdapter.notifyDataSetChanged();
                                        } catch (JSONException e) {
                        e.printStackTrace();
                }

        }

        private void getRestaurants(){
                String url;
                restaurantses.clear();
                url = Settings.SERVERURL + "restaurants.php?type=trending";
                Log.e("url", url);
                JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray jsonArray) {
                                display_data(jsonArray);
                        }
                }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.e("response is:", error.toString());

                        }
                });

// Access the RequestQueue through your singleton class.
                AppController.getInstance().addToRequestQueue(jsObjRequest);
        }


    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void songselected(Restaurants restaurants);
        public void companyfragment(String cat);
        public void home_head(String head);
        public void clear_backstack();
        public Animation get_animation(Boolean enter,Boolean loaded);
    }
        public void res_selected(int position) {

        mCallBack.songselected(restaurantses.get(position));
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

    private void getCategories(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url;
//        restaurantses.clear();
        url = Settings.SERVERURL + "restaurants_cat.php";
        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                categories.clear();
                display_cat_list(jsonArray);
                if(progressDialog!=null)
                    progressDialog.dismiss();
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
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }



    private void display_cat_list(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tempjson = jsonArray.getJSONObject(i);
                Category category = new Category(tempjson.getString("title"), tempjson.getString("title_ar"), tempjson.getString("id"));
                categories.add(category);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        categoryAdapter.notifyDataSetChanged();
    }



}