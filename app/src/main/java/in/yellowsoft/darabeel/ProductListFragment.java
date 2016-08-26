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
import android.widget.ListView;

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
public class ProductListFragment extends Fragment {
    MenuProductAdapter menuProductAdapter;
    ArrayList<Products> productses;
    ArrayList<Area> group_item;
    String res_id,cat_id,head;
    boolean loaded=false;
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter,loaded);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.product_list_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        if(loaded)
        Log.e("loaded","true");
        else
            Log.e("loaded","false");
//        loaded=true;
//        head=String.valueOf(Html.fromHtml(Settings.getword(getActivity(), "products")));
        productses = new ArrayList<>();
        group_item = new ArrayList<>();
        res_id=(String)getArguments().getSerializable("rest_id");
        Log.e("resta,cat",res_id+cat_id);
        cat_id=(String)getArguments().getSerializable("cat_id");
        head=(String)getArguments().getSerializable("cat_name");
        mCallBack.text_back_butt(head);
        ListView gridView=(ListView)view.findViewById(R.id.product_list);
        menuProductAdapter = new MenuProductAdapter(getActivity(),productses);
        gridView.setAdapter(menuProductAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallBack.product_selected(productses.get(i));
            }
        });
        getProducts();

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
    private void getProducts(){
        String url;
      //  restaurantses.clear();
        url = Settings.SERVERURL + "products.php?rest_id="+res_id+"&category="+cat_id;
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.setCancelable(false);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("products",jsonArray.toString());
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Products temp_product = new Products(jsonObject.getString("id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("title_ar"),
                                jsonObject.getString("stock"),
                                jsonObject.getString("price"),
                                jsonObject.getString("quantity"),
                                jsonObject.getString("about"),
                                jsonObject.getString("about_ar"),
                                jsonObject.getString("description"),
                                jsonObject.getString("description_ar"),
                                jsonObject.getString("options_title"),
                                jsonObject.getString("options_title_ar"),
                                jsonObject.getJSONArray("images"),
                                jsonObject.getJSONArray("restaurant"),
                                jsonObject.getJSONArray("category"),
                                jsonObject.getJSONArray("options"),
                                jsonObject.getJSONArray("group"));
                        productses.add(temp_product);
                        menuProductAdapter.notifyDataSetChanged();
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
                if(progressDialog!=null)
                    progressDialog.dismiss();
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void product_selected(Products products);
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
                    + " must implement LogoutUser");
        }
    }


}