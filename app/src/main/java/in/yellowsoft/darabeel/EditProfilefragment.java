package in.yellowsoft.darabeel;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfilefragment extends Fragment {
    String head,member_id, adds_id, first_name, last_name, mobile, work_phone, home_phone;
    FragmentTouchListner mCallBack;
    boolean loaded=false;
    ViewFlipper viewFlipper;
    EditText et_fname,et_lname,et_phone,et_home,et_work;
    LinearLayout edit;
    TextView edit_tv;
    ProgressBar progressBar;
    ImageView tick_fname,tick_lname;
    AlertDialogManager alert = new AlertDialogManager();
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
        return inflater.inflate(R.layout.edit_profile_page, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar6);
        get_user_details();
        tick_fname=(ImageView)view.findViewById(R.id.tick_fname_my_acc);
        tick_lname=(ImageView)view.findViewById(R.id.tick_lname_my_acc);
//        loaded=true;
        et_fname = (EditText) view.findViewById(R.id.edit_fname);
        et_fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    tick_fname.setVisibility(View.VISIBLE);
                } else
                    tick_fname.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_lname = (EditText) view.findViewById(R.id.edit_lname);
        et_lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    tick_lname.setVisibility(View.VISIBLE);
                } else
                    tick_lname.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_phone = (EditText) view.findViewById(R.id.edit_mobile);
        et_home = (EditText) view.findViewById(R.id.edit_home_phone);
        et_work = (EditText) view.findViewById(R.id.edit_work_phone);
//        et_street = (EditText) view.findViewById(R.id.edit_street);
//        et_street.setHint(Settings.getword(getActivity(), "street") + "*");
//        et_house = (EditText) view.findViewById(R.id.edit_house);
//        et_house.setHint(Settings.getword(getActivity(), "house") + "*");
//        et_floor = (EditText) view.findViewById(R.id.edit_floor);
//        et_floor.setHint(Settings.getword(getActivity(), "floor_number"));
//        et_flat = (EditText) view.findViewById(R.id.edit_flat);
//        et_flat.setHint(Settings.getword(getActivity(), "flat_number"));
        edit = (LinearLayout) view.findViewById(R.id.edit_ll);
        edit_tv = (TextView) view.findViewById(R.id.edit_tv);
        edit_tv.setText(Settings.getword(getActivity(),"edit"));
//        edit_email = (TextView) view.findViewById(R.id.edit_email_tv);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_pro();
            }
        });

    }
    public  void edit_pro() {
        final String fname = et_fname.getText().toString();
        final String lname = et_lname.getText().toString();
        final String phone = et_phone.getText().toString();
        final String home = et_home.getText().toString();
        final String work = et_work.getText().toString();
        Log.e("phone", et_phone.getText().toString());

        if (fname.equals(""))
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_name"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_name"), Toast.LENGTH_SHORT).show();
        else if (lname.equals(""))
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_lastname"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_lastname"), Toast.LENGTH_SHORT).show();
        else if (phone.equals(""))
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_mobile"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_mobile"), Toast.LENGTH_SHORT).show();
//        else if (home.equals(""))
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_home_ph"), Toast.LENGTH_SHORT).show();
//        else if (work.equals(""))
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_work_ph"), Toast.LENGTH_SHORT).show();

        else {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
            progressDialog.show();
            progressDialog.setCancelable(false);
            String url = Settings.SERVERURL+"edit-profile.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
//                        String reply=jsonObject.getString("status");
                        if(!jsonObject.has("id")) {
                            String msg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String msg=jsonObject.getString("fname");
                            Toast.makeText(getActivity(),Settings.getword(getActivity(),"message_updated_profile"), Toast.LENGTH_SHORT).show();
                            viewFlipper.setDisplayedChild(0);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("member_id",Settings.getUserid(getActivity()));
                    params.put("fname",fname);
                    params.put("lname",lname);
                    params.put("mobile",phone);
                    params.put("home_phone",home);
                    params.put("work_phone",work);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
    public void  get_user_details(){
        String url = Settings.SERVERURL+"user-details.php?member_id="+Settings.getUserid(getActivity());
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
//        progressDialog.show();
//        progressDialog.setCancelable(false);
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                Log.e("response is: ", jsonObject.toString());
                try {
                    first_name=jsonObject.getString("fname");
                    last_name=jsonObject.getString("lname");
                    mobile=jsonObject.getString("mobile");
                    work_phone=jsonObject.getString("work_phone");
                    home_phone=jsonObject.getString("home_phone");

                    et_fname.setText(first_name);
                    et_lname.setText(last_name);
                    et_phone.setText(mobile);
                    et_home.setText(home_phone);
                    et_work.setText(work_phone);
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
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
}
