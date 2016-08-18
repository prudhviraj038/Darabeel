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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 13-05-2016.
 */
public class AreaFragment extends Fragment {
    ArrayList<Area> area_list;
    AreaAdapter personAdapter ;
    Restaurants restaurants;
    String sid;
    boolean loaded=false;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void songselected(Restaurants restaurants);
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
        View rootview = inflater.inflate(R.layout.area_fragment_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        area_list = new ArrayList<>();
        restaurants = (Restaurants)getArguments().getSerializable("restaurants");
        sid=(String)getArguments().getSerializable("id");
        ListView area_listView = (ListView)view.findViewById(R.id.area_listView1);
        personAdapter = new AreaAdapter(getActivity(),area_list);
        area_listView.setAdapter(personAdapter);
        area_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Settings.setArea_id(getActivity(), area_list.get(position).getId(), area_list.get(position).getArea(getActivity()), area_list.get(position).getArea_ar());
                    mCallBack.songselected(restaurants);

            }
        });


        String url = null;
        try {
//            if(sid.equals("-1")) {
//                url = Settings.SERVERURL + "areas.php?";
//            }else if(sid.equals("-2")){
//                url = Settings.SERVERURL + "areas.php?";
//            }else
            url = Settings.SERVERURL + "areas.php?"+"rest_id="+sid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                Log.e("orders response is: ", jsonArray.toString());

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String id = jsonArray.getJSONObject(i).getString("id");
                        String area = jsonArray.getJSONObject(i).getString("title");
                        String area_ar = jsonArray.getJSONObject(i).getString("title_ar");
                        Area person = new Area(id,area,area_ar,true);
                        area_list.add(person);

                        Log.e("titleee", sub.getString("title"));

                        JSONArray jsonArray1=sub.getJSONArray("areas");
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            String idt = jsonArray1.getJSONObject(j).getString("id");
                            String areat = jsonArray1.getJSONObject(j).getString("title");
                            String areat_ar = jsonArray1.getJSONObject(j).getString("title_ar");
                            Area persont = new Area(idt,areat,areat_ar,false);
                            area_list.add(persont);
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
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);


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
}