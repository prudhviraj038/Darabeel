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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAddressfragment extends Fragment {
    String head,adds_id;
    FragmentTouchListner mCallBack;
    boolean loaded=false;
    TextView et_my_area, my_alias, my_area, my_block, my_street, my_building, my_floor, my_flat, my_mobile, update,
            delete_tv,edit_email, edit_tv, cp_submit_tv, add_new_address_tv;
    EditText et_my_alias, et_my_block, et_my_street, et_my_building, et_my_floor, et_my_flat, et_my_mobile;
    LinearLayout my_order_ll, my_address_ll, edit_address, change_pass, settings, logout, edit, cp_submit, add_new_address_ll,
            update_ll,delete, area_list_my_acc, language,area_list_ll;
    ViewFlipper viewFlipper;
    AreaAdapter personAdapter;
    ArrayList<Area> area_list;
    ListView my_address_list;
    ArrayList<Addresss> address_list;
    boolean isadd = false;
    ProgressBar progressBar;
    MyAccountAddressAdapter myAccountAddressAdapter;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public  Animation get_animation(Boolean enter, Boolean loaded);
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
        return inflater.inflate(R.layout.my_address_page, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        address_list = new ArrayList<>();
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar4);
        get_address_list();
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper8);
        viewFlipper.setDisplayedChild(0);
        my_alias = (TextView) view.findViewById(R.id.sta_my_alias_tv);
        my_alias.setText(Settings.getword(getActivity(), "text_alias"));
        my_area = (TextView) view.findViewById(R.id.sta_my_area_tv);
        my_area.setText(Settings.getword(getActivity(), "area"));
        my_block = (TextView) view.findViewById(R.id.sta_my_block_tv);
        my_block.setText(Settings.getword(getActivity(), "block"));
        my_street = (TextView) view.findViewById(R.id.sta_my_street_tv);
        my_street.setText(Settings.getword(getActivity(), "street_name"));
        my_building = (TextView) view.findViewById(R.id.sta_my_building_tv);
        my_building.setText(Settings.getword(getActivity(), "building_name"));
        my_floor = (TextView) view.findViewById(R.id.sta_my_floor_tv);
        my_floor.setText(Settings.getword(getActivity(), "floor"));
        my_flat = (TextView) view.findViewById(R.id.sta_my_flat_tv);
        my_flat.setText(Settings.getword(getActivity(), "apartment"));
        my_mobile = (TextView) view.findViewById(R.id.sta_my_mobile_tv);
        my_mobile.setText(Settings.getword(getActivity(), "mobile"));
        update = (TextView) view.findViewById(R.id.update_tv);
        update.setText(Settings.getword(getActivity(), "update"));
        delete_tv = (TextView) view.findViewById(R.id.delete_my_address);
        delete_tv.setText(Settings.getword(getActivity(), "delete_address"));
        et_my_alias = (EditText) view.findViewById(R.id.my_alias_et);
        et_my_area = (TextView) view.findViewById(R.id.my_area_et);
        et_my_block = (EditText) view.findViewById(R.id.my_block_et);
        et_my_street = (EditText) view.findViewById(R.id.my_street_et);
        et_my_building = (EditText) view.findViewById(R.id.my_buiding_et);
        et_my_floor = (EditText) view.findViewById(R.id.my_floor_et);
        et_my_flat = (EditText) view.findViewById(R.id.my_flat_et);
        et_my_mobile = (EditText) view.findViewById(R.id.my_mobile_et);
        area_list_my_acc = (LinearLayout) view.findViewById(R.id.area_list_my_acc);
        update_ll = (LinearLayout) view.findViewById(R.id.update_ll);
        delete = (LinearLayout) view.findViewById(R.id.delete_my_address_ll);
//            area_list_ll = (LinearLayout) view.findViewById(R.id.area_list_ll);
        add_new_address_ll = (LinearLayout) view.findViewById(R.id.add_new_address_ll);
        add_new_address_tv = (TextView) view.findViewById(R.id.add_new_address_tv);
        add_new_address_tv.setText(Settings.getword(getActivity(), "add_address"));
        my_address_list = (ListView) view.findViewById(R.id.my_address_list);
        myAccountAddressAdapter = new MyAccountAddressAdapter(getActivity(), address_list);
        my_address_list.setAdapter(myAccountAddressAdapter);
        my_address_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewFlipper.setDisplayedChild(1);
                isadd = false;
                delete.setVisibility(View.VISIBLE);
                adds_id = address_list.get(i).id;
                et_my_alias.setText(address_list.get(i).alias);
                et_my_area.setText(address_list.get(i).getTitle(getActivity()));
                et_my_block.setText(address_list.get(i).block);
                et_my_street.setText(address_list.get(i).street);
                et_my_building.setText(address_list.get(i).house);
                et_my_floor.setText(address_list.get(i).floor);
                et_my_flat.setText(address_list.get(i).flat);
                et_my_mobile.setText(address_list.get(i).phone);

            }
        });

        add_new_address_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setDisplayedChild(1);
                isadd = true;
                delete.setVisibility(View.GONE);
                update.setText(Settings.getword(getActivity(), "add"));
                et_my_alias.setText("");
                et_my_area.setText(Settings.getword(getActivity(), "area"));
                et_my_block.setText("");
                et_my_street.setText("");
                et_my_building.setText("");
                et_my_floor.setText("");
                et_my_flat.setText("");
                et_my_mobile.setText("");

            }
        });
        area_list = new ArrayList<>();
        ListView area_listView = (ListView) view.findViewById(R.id.edit_area_list);
        personAdapter = new AreaAdapter(getActivity(), area_list);
        area_listView.setAdapter(personAdapter);
        area_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewFlipper.setDisplayedChild(1);
                Settings.setEditAdd_Area_id(getActivity(), area_list.get(position).getId(), area_list.get(position).getArea(getActivity()), area_list.get(position).getArea_ar());
                et_my_area.setText(Settings.getEditAdd_Area_name(getActivity()));
            }
        });
        update_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isadd)
                    add_address("-1");
                else
                    add_address(adds_id);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_address();
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
        area_list_my_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getarea();
                viewFlipper.setDisplayedChild(2);
//                    mCallBack.area_list("-1");
            }
        });
    }
    private void get_address_list(){
        String url=Settings.SERVERURL+"address-list.php?"+"member_id="+Settings.getUserid(getActivity());
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
//                if(progressDialog!=null)
//                    progressDialog.dismiss();
                Log.e("response is: ", jsonArray.toString());
                progressBar.setVisibility(View.GONE);
                address_list.clear();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String ar_id = sub.getString("id");
                        String ar_name = sub.getString("alias");
                        String add_name = sub.getString("name");
                        String phone = sub.getString("phone");
                        JSONObject json_area=sub.getJSONObject("area");
                        String addr_area_id = json_area.getString("id");
                        String addr_area_title = json_area.getString("title");
                        String addr_area_title_ar = json_area.getString("title_ar");
                        String block = sub.getString("block");
                        String street = sub.getString("street");
                        String building = sub.getString("building");
                        String floor = sub.getString("floor");
                        String app_flat = sub.getString("app_flat");
                        Addresss address=new Addresss(ar_id,ar_name,add_name,phone,addr_area_id,addr_area_title,addr_area_title_ar,block,
                                street,building,floor,app_flat);
                        address_list.add(address);
                    }
                    myAccountAddressAdapter.notifyDataSetChanged();
                    if(jsonArray.length()==0){
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_wait"), Toast.LENGTH_SHORT).show();
                        add_new_address_ll.performClick();
                    }
//                    else{
//                        drop_down_ll.performClick();
//                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"),Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
    private void add_address(final String id){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"add-address.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Success")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        my_address_ll.performClick();
//                        viewFlipper.setDisplayedChild(4);
                    }
                    else {
                        String msg=jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("alias",et_my_alias.getText().toString());
                params.put("area",Settings.getEditAdd_Area_id(getActivity()));
                params.put("block",et_my_block.getText().toString());
                params.put("street",et_my_street.getText().toString());
                params.put("building",et_my_building.getText().toString());
                params.put("floor",et_my_floor.getText().toString());
                params.put("app_flat",et_my_flat.getText().toString());
                params.put("phone",et_my_mobile.getText().toString());
                params.put("member_id",Settings.getUserid(getActivity()));
                if(!id.equals("-1"))
                    params.put("address_id",adds_id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void  delete_address(){
        String url = Settings.SERVERURL+"del-address.php?address_id="+adds_id+"&member_id="+Settings.getUserid(getActivity());
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Success")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        my_address_ll.performClick();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    private void getarea() {
        String url = null;
        try {
            url = Settings.SERVERURL + "areas.php";
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
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}
