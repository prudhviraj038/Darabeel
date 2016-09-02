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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 04-05-2016.
 */
public class PromotionsListFragment extends Fragment {

   ArrayList<PromotionList> promotionLists;
    ProgressBar progressBar;
    String id,head;
    boolean loaded=false;
    MyTextView select_cat;
    ArrayList<Category> categories;
    ListView cat_listView;
    CategoryAdapter categoryAdapter;
    PromotionAdapter promotionAdapter;
    ViewFlipper viewFlipper;
    FragmentTouchListner mCallBack;
    Restaurants restaurantses;
    public interface FragmentTouchListner {
      public void five_items();
        public void songselected(Restaurants restaurant);
        public void text_back_butt(String header);
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
        View rootview = inflater.inflate(R.layout.offer_list_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        categories = new ArrayList<>();
        promotionLists = new ArrayList<>();
//        getCategories();
        head=Settings.getword(getActivity(),"promotions");
        mCallBack.text_back_butt(head);
        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper4);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar2);
        cat_listView = (ListView) view.findViewById(R.id.category_list);
        categoryAdapter = new CategoryAdapter(getActivity(), categories);
        getRestaurants();
        cat_listView.setAdapter(categoryAdapter);
        cat_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                viewFlipper.setDisplayedChild(1);
                id=categories.get(i).id;
//                getRestaurants();
                Log.e("id",id);
            }
        });
        GridView offers=(GridView)view.findViewById(R.id.offer_list);
        promotionAdapter=new PromotionAdapter(getActivity(),promotionLists,this);
        offers.setAdapter(promotionAdapter);
        offers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getRes(promotionLists.get(position).rest.get(0).id);
            }
        });


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
//        mCallBack.five_items();
    }

    private void getRes(String id) {
        String url;
        url = Settings.SERVERURL + "restaurants.php?rest_id=" + id;
        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
//                progressBar.setVisibility(View.GONE);
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
                        mCallBack.songselected(temp);
                    }
                    promotionAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
//                progressBar.setVisibility(View.GONE);

            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }


    private void getRestaurants(){
        String url;
        promotionLists.clear();
        url = Settings.SERVERURL + "promotions.php";
//        +"&category_id="+id;
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
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    private void display_data(JSONArray jsonArray){
        Log.e("reponse", jsonArray.toString());
        try {
            for (int i=0;i<jsonArray.length();i++){
                JSONObject tmp_json = jsonArray.getJSONObject(i);
                PromotionList temp = new PromotionList(tmp_json.getString("title"),
                        tmp_json.getString("title_ar"),
                        tmp_json.getString("description"),
                        tmp_json.getString("description_ar"),
                        tmp_json.getString("price"),
                        tmp_json.getString("image"),
                        tmp_json.getJSONArray("restaurant"));
                promotionLists.add(temp);
            }
            promotionAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}