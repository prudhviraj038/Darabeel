package in.yellowsoft.darabeel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 04-05-2016.
 */
public class LoginFragment extends Fragment {
    String head,write;
    TextView et_area;
    EditText et_fname, et_lname, et_phone, etr_email, etr_password,et_block,et_street,et_house,et_floor,et_flat,et_email,et_password;
    LinearLayout register,area_list_signup;
    AllApis allApis=new AllApis();
    ViewFlipper viewFlipper;
    boolean loaded=false;
    FragmentTouchListner mCallBack;
    ArrayList<Area> area_list;
    AreaAdapter personAdapter ;
    ImageView tick_login_email,tick_login_pass,tick_fname,tick_lname,tick_signup_email,tick_signup_pass,tick_mobile,tick_block,tick_street,tick_house;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public interface FragmentTouchListner {
        public void after_login();
        public void text_back_butt(String header);
        public void register(String code,String id);
        public void  area_list(String id);
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
        return inflater.inflate(R.layout.login_screen, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        head = Settings.getword(getActivity(), "title_login");
        mCallBack.text_back_butt(head);
        tick_fname=(ImageView)view.findViewById(R.id.tick_signup_fname);
        tick_lname=(ImageView)view.findViewById(R.id.tick_signup_lname);
        tick_mobile=(ImageView)view.findViewById(R.id.tick_signup_mobile);
        tick_signup_email=(ImageView)view.findViewById(R.id.tick_signup_email);
        tick_signup_pass=(ImageView)view.findViewById(R.id.tick_signup_pass);
        tick_block=(ImageView)view.findViewById(R.id.tick_signup_block);
        tick_street=(ImageView)view.findViewById(R.id.tick_signup_street);
        tick_house=(ImageView)view.findViewById(R.id.tick_signup_house);
        tick_login_email=(ImageView)view.findViewById(R.id.tick_login_email);
        tick_login_pass=(ImageView)view.findViewById(R.id.tick_login_password);
        final LinearLayout login = (LinearLayout) view.findViewById(R.id.login);
        TextView tv_login = (TextView) view.findViewById(R.id.login_tv);
        tv_login.setText(Settings.getword(getActivity(),"title_login"));
        TextView fpassword = (TextView) view.findViewById(R.id.forgot_pass);
        fpassword.setText(Settings.getword(getActivity(),"forgot_password"));
        TextView new_here1 = (TextView) view.findViewById(R.id.signup_newhere);
        new_here1.setText(Settings.getword(getActivity(),"new_here"));
        TextView register_tv = (TextView) view.findViewById(R.id.signup);
        register_tv.setText(Settings.getword(getActivity(),"register"));
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper2);
        et_email = (EditText) view.findViewById(R.id.login_email);
        et_email.setHint(Settings.getword(getActivity(), "email_address"));
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().matches(emailPattern)) {
                    tick_login_email.setVisibility(View.VISIBLE);
                } else
                    tick_login_email.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_password = (EditText) view.findViewById(R.id.login_password);
        et_password.setHint(Settings.getword(getActivity(), "password"));
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() > 5) {
                    tick_login_pass.setVisibility(View.VISIBLE);
                } else
                    tick_login_pass.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                if (email.equals(""))
                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_email"), Toast.LENGTH_SHORT).show();
                else if (!email.matches(emailPattern))
                    Toast.makeText(getActivity(), "Please Enter Valid Email id", Toast.LENGTH_SHORT).show();
                else if (password.equals(""))
                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_password"), Toast.LENGTH_SHORT).show();
                else if (password.length() < 5)
                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "password_lenth"), Toast.LENGTH_SHORT).show();
                else {
                    allApis.login(getActivity(), email, password, mCallBack);
                }
            }
        });
        new_here1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                head = Settings.getword(getActivity(), "title_account_info");
                mCallBack.text_back_butt(head);
                viewFlipper.setDisplayedChild(1);

            }
        });
        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(Settings.getword(getActivity(), "forgot_password_sent"));
                LinearLayout layout = new LinearLayout(getActivity());
                final EditText input = new EditText(getActivity());
                input.setMinLines(2);
                input.setHint(Settings.getword(getActivity(),"email_address"));
                input.setVerticalScrollBarEnabled(true);
                input.setBackgroundResource(R.drawable.pink_rounded_corners_e);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.setMargins(20, 10, 20, 10);
                input.setLayoutParams(buttonLayoutParams);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(input);

                alert.setView(layout);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        write = input.getText().toString();
                        if (write.equals(""))
                            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_email"), Toast.LENGTH_SHORT).show();
                        else if (!write.matches(emailPattern))
                            Toast.makeText(getActivity(), "Please Enter Valid Email id", Toast.LENGTH_SHORT).show();
                        else
                            forgot_pass();
                    }
                });
                alert.show();
            }
        });
        et_fname = (EditText) view.findViewById(R.id.fname_signup);
        et_fname.setHint(Settings.getword(getActivity(), "first_name") + "*");
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
        et_lname = (EditText) view.findViewById(R.id.lname_signup);
        et_lname.setHint(Settings.getword(getActivity(), "last_name") + "*");
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

        et_phone = (EditText) view.findViewById(R.id.mobile_signup);
        et_phone.setHint(Settings.getword(getActivity(),"mobile_number")+"*");
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    tick_mobile.setVisibility(View.VISIBLE);
                } else
                    tick_mobile.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etr_email = (EditText) view.findViewById(R.id.email_signup);
        etr_email.setHint(Settings.getword(getActivity(),"email_address"));
        etr_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().matches(emailPattern)) {
                    tick_signup_email.setVisibility(View.VISIBLE);
                } else
                    tick_signup_email.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
         etr_password = (EditText) view.findViewById(R.id.pass_signup);
        etr_password.setHint(Settings.getword(getActivity(),"password")+"*");
        etr_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() > 6) {
                    tick_signup_pass.setVisibility(View.VISIBLE);
                } else
                    tick_signup_pass.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_area = (TextView) view.findViewById(R.id.area_signup);
        et_area.setText(Settings.getword(getActivity(), "area") + "*");
        area_list_signup = (LinearLayout) view.findViewById(R.id.area_list_signup);
        area_list_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getarea();
                viewFlipper.setDisplayedChild(2);
//                mCallBack.area_list("-2");
            }
        });
        area_list = new ArrayList<>();
        ListView area_listView = (ListView)view.findViewById(R.id.signup_area_list);
        personAdapter = new AreaAdapter(getActivity(), area_list);
        area_listView.setAdapter(personAdapter);
        area_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewFlipper.setDisplayedChild(1);
                Settings.setSignup_Area_id(getActivity(), area_list.get(position).getId(), area_list.get(position).getArea(getActivity()), area_list.get(position).getArea(getActivity()));
                et_area.setText(Settings.getSignup_Area_name(getActivity()));
            }
        });
        et_block = (EditText) view.findViewById(R.id.block_signup);
        et_block.setHint(Settings.getword(getActivity(), "block") + "*");
        et_block.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    tick_block.setVisibility(View.VISIBLE);
                } else
                    tick_block.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_street = (EditText) view.findViewById(R.id.street_signup);
        et_street.setHint(Settings.getword(getActivity(),"street")+"*");
        et_street.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    tick_street.setVisibility(View.VISIBLE);
                } else
                    tick_street.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_house = (EditText) view.findViewById(R.id.house_signup);
        et_house.setHint(Settings.getword(getActivity(),"house")+"*");
        et_house.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    tick_house.setVisibility(View.VISIBLE);
                } else
                    tick_house.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_floor = (EditText) view.findViewById(R.id.floor_signup);
        et_floor.setHint(Settings.getword(getActivity(),"floor_number"));
        et_flat = (EditText) view.findViewById(R.id.flat_signup);
        et_flat.setHint(Settings.getword(getActivity(),"flat_number"));
        register = (LinearLayout) view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up();
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        head = Settings.getword(getActivity(), "title_login");
                        mCallBack.text_back_butt(head);
                        viewFlipper.setDisplayedChild(0);
                        return true;
                    }
//                    loaded = true;
                    return false;
                }
                return false;
            }
        });
    }
        private void sign_up(){
            final String fname = et_fname.getText().toString();
            final String lname = et_lname.getText().toString();
            final String phone ="+965"+ et_phone.getText().toString();
            final String email = etr_email.getText().toString();
            final String password = etr_password.getText().toString();
            final String area = Settings.getSignup_Area_id(getActivity());
            final String block = et_block.getText().toString();
            final String street = et_street.getText().toString();
            final String house = et_house.getText().toString();
            final String floor = et_floor.getText().toString();
            final String flat = et_flat.getText().toString();

            if (fname.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_name"), Toast.LENGTH_SHORT).show();
            else if (lname.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_lastname"), Toast.LENGTH_SHORT).show();
            else if (phone.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_mobile"), Toast.LENGTH_SHORT).show();
            else if (email.equals(""))
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"please_enter_email"), Toast.LENGTH_SHORT).show();
            else if (!email.matches(emailPattern))
                Toast.makeText(getActivity(), "Please Enter Valid Email id", Toast.LENGTH_SHORT).show();
            else if (password.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_password"), Toast.LENGTH_SHORT).show();
            else if (password.length() < 5)
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"password_lenth"), Toast.LENGTH_SHORT).show();
            else if (area.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_area"), Toast.LENGTH_SHORT).show();
            else if (block.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_block"), Toast.LENGTH_SHORT).show();
            else if (street.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_street"), Toast.LENGTH_SHORT).show();
            else if (house.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_building"), Toast.LENGTH_SHORT).show();
            else if (floor.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_floor"), Toast.LENGTH_SHORT).show();
            else if (flat.equals(""))
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_flat"), Toast.LENGTH_SHORT).show();
            else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("first_name",fname);
                    jsonObject.put("last_name",lname);
                    jsonObject.put("mobile", phone);
                    jsonObject.put("email", email);
                    jsonObject.put("password", password);
                    jsonObject.put("area", Settings.getSignup_Area_id(getActivity()));
                    jsonObject.put("block", block);
                    jsonObject.put("street", street);
                    jsonObject.put("building", house);
                    jsonObject.put("floor", floor);
                    jsonObject.put("flat", flat);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                Settings.set_Address_json(getActivity(),jsonObject.toString());
                allApis.signup(getActivity(),fname,lname,phone,email,password,area,block,street,house,floor,flat,mCallBack);
            }

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
    public void forgot_pass(){
        String url = Settings.SERVERURL+"forget-password.php?email="+write;
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failed")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }

}


