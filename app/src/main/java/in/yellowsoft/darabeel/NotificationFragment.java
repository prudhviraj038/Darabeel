package in.yellowsoft.darabeel;

import android.app.Activity;
import android.app.ProgressDialog;
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
public class NotificationFragment extends Fragment {
    ArrayList<Promotions> restaurants;
    String id;
    boolean loaded=false;
    NotificationAdapter promotionAdapter;
    FragmentTouchListner mCallBack;
    AlertDialogManager alert = new AlertDialogManager();
    public interface FragmentTouchListner {
        public void five_items();
        public void songselected(Restaurants restaurant);
        public void gotoordrerspage();
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
        View rootview = inflater.inflate(R.layout.notification_fragment_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        restaurants = new ArrayList<>();
        promotionAdapter=new NotificationAdapter(getActivity(),restaurants,this);
        GridView offers=(GridView)view.findViewById(R.id.offer_list);
        offers.setAdapter(promotionAdapter);
       offers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(types.get(position).equals("order"))
                   mCallBack.gotoordrerspage();
                   //getRestaurant("4");
               else
                   getRestaurant(typesid.get(position));
           }
       });
        getRestaurants();
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



    private void getRestaurants(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url;
        restaurants.clear();
        url = Settings.SERVERURL + "notifications.php?member_id="+Settings.getUserid(getActivity());
        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                display_data(jsonArray);
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
    ArrayList<String> types = new ArrayList<>();
    ArrayList<String> typesid = new ArrayList<>();

    private void display_data(JSONArray jsonArray){
        Log.e("reponse", jsonArray.toString());
        try {
            for (int i=0;i<jsonArray.length();i++){
                JSONObject tmp_json = jsonArray.getJSONObject(i);
                Promotions temp = new Promotions(
                        tmp_json.getString("title"),
                        tmp_json.getString("title"),
                        tmp_json.getString("message"),
                        tmp_json.getString("message"),
                        tmp_json.getString("time"),
                        tmp_json.getString("image")
                        );

                restaurants.add(temp);
                types.add(tmp_json.getString("type"));
                typesid.add(tmp_json.getString("type_id"));
            }
            promotionAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getRestaurant(String id){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url;
        restaurants.clear();
        url = Settings.SERVERURL + "restaurants.php?rest_id="+id;
        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                display_res_data(jsonArray);
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

    private void display_res_data(JSONArray jsonArray){
        Log.e("reponse", jsonArray.toString());
        try {
            Restaurants temp = null;
            for (int i=0;i<jsonArray.length();i++){
                JSONObject tmp_json = jsonArray.getJSONObject(i);
                temp = new Restaurants(tmp_json.getString("id")
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
            }
                    if(null != temp)
                         mCallBack.songselected(temp);
                    else
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "no_products"), false);
//                        Toast.makeText(getActivity(),Settings.getword(getActivity(),"no_products"),Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}