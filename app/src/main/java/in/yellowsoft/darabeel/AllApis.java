package in.yellowsoft.darabeel;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chinni on 05-05-2016.
 */
public class AllApis {
    AlertDialogManager alert = new AlertDialogManager();
    String fname,lname,phone,email,password,area,block,street,house,floor,flat,name,message;
    Context context;

    public void signup(final Context context,final String fname,final String lname,final String phone,final String email,
                       final String password, final String area, final String block, final String street, final String house,
                       final String floor, final String flat,final LoginFragment.FragmentTouchListner mcallback){
        this.context=context;
        this.fname=fname;
        this.lname=lname;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.area=area;
        this.block=block;
        this.street=street;
        this.house=house;
        this.floor=floor;
        this.flat=flat;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("please wait.. we are processing your order");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"member.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String reply=jsonObject.getString("status");
                            if(reply.equals("Failed")) {
                                String msg = jsonObject.getString("message");
                                alert.showAlertDialog(context, "Info", msg, true);
//                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                            else {
                                String mem_id=jsonObject.getString("member_id");
                                String code=jsonObject.getString("code");
                                String msg=jsonObject.getString("message");
//                                Settings.setUserid(context, mem_id, name);
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                update_gcm(mem_id, context);;
                                mcallback.register(code,mem_id);
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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("phone",phone);
                params.put("email",email);
                params.put("password",password);
                params.put("area",area);
                params.put("block",block);
                params.put("street",street);
                params.put("house",house);
                params.put("floor",floor);
                params.put("flat",flat);
                params.put("android_token",flat);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void login(final Context context,String email,String password, final LoginFragment.FragmentTouchListner mCallback){
        this.context=context;
        this.email=email;
        this.password=password;
        String url = Settings.SERVERURL+"login.php?";
        try {
            url = url + "email="+ URLEncoder.encode(email, "utf-8")+
                    "&password="+URLEncoder.encode(password,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(context);
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
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String mem_id=jsonObject.getString("member_id");
                        String name=jsonObject.getString("name");
                        Settings.setUserid(context, mem_id, name);
                        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                        update_gcm(mem_id,context);
                        mCallback.after_login();
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
                Toast.makeText(context, "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    public void contact_us(final Context context,String name,String email,String phone,String message ) {
        this.context = context;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Settings.getword(context, "please_wait"));
        progressDialog.show();
        String url = null;

        try {
            url = Settings.SERVERURL + "contact-form.php?phone=+965" + phone +
                    "&name=" + URLEncoder.encode(name, "utf-8") +
                    "&email=" + URLEncoder.encode(email, "utf-8")
                    + "&message=" + URLEncoder.encode(message, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("register url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Log.e("response is", jsonObject.toString());
                try {
                    Log.e("response is", jsonObject.getString("response"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String result = jsonObject1.getString("status");
                    if (result.equals("Success")) {
                        //finish();
                        Toast.makeText(context, Settings.getword(context, "contact_us_message_sent"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, Settings.getword(context, "please_try_again"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("error response is:", error.toString());
                if (progressDialog != null)
                    progressDialog.dismiss();
                Toast.makeText(context, Settings.getword(context, "please_try_again"), Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    public void update_gcm(String memberid , final Context context) {
        this.context = context;
        String url = null;
        url = CommonUtilities.SERVER_URL+"?member_id="+Settings.getUserid(context)+ "&device_token=" + Settings.get_gcmid(context);
        Log.e("register url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response is", jsonObject.toString());
                try {
                    Log.e("response is", jsonObject.getString("response"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String result = jsonObject1.getString("status");
                    if (result.equals("Success")) {
                        //finish();
                        //  Toast.makeText(context, Settings.getword(context, "contact_us_message_sent"), Toast.LENGTH_SHORT).show();

                    } else {
                        //  Toast.makeText(context, Settings.getword(context, "please_try_again"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("error response is:", error.toString());
                //  Toast.makeText(context, Settings.getword(context, "please_try_again"), Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}


