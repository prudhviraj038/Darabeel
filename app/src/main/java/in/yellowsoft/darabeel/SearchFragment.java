package in.yellowsoft.darabeel;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
public class SearchFragment extends Fragment {
    CompanylistAdapter companylistAdapter;
    FragmentTouchListner mCallBack;
    ProgressBar progressBar;
    EditText search;
    String search1;
    boolean loaded=false;
    ImageView search_img;
    TextView no_products;
    public interface FragmentTouchListner {
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
        View rootview = inflater.inflate(R.layout.search_fragment, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        no_products = (TextView) view.findViewById(R.id.no_products);
        no_products.setText(Settings.getword(getActivity(),"no_products"));
        search=(EditText) view.findViewById(R.id.search_edit);
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
                if(search1.length()>0){
                    getRestaurants();
                }
                else{
                    restaurants.clear();
                    companylistAdapter.notifyDataSetChanged();
                }
            }
        });
        search_img=(ImageView)view.findViewById(R.id.search_image);
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search1=search.getText().toString();
                getRestaurants();
            }
        });
        GridView gridView=(GridView)view.findViewById(R.id.search_grid);
        companylistAdapter=new CompanylistAdapter(getActivity(),restaurants);
        gridView.setAdapter(companylistAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.songselected(restaurants.get(position));
            }
        });

//        Log.e("id","list.php?cid="+getArguments().getString("id"));

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
    }


    ArrayList<Restaurants> restaurants = new ArrayList<>();

    private void getRestaurants(){
        String url;

        progressBar.setVisibility(View.VISIBLE);
        try {
            url = Settings.SERVERURL + "restaurants.php?search="+ URLEncoder.encode(search1, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Settings.SERVERURL + "restaurants.php?search="+search1;
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
                no_products.setVisibility(View.VISIBLE);
            }
            else
                no_products.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}