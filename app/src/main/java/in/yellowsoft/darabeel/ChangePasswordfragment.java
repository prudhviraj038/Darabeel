package in.yellowsoft.darabeel;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import android.widget.LinearLayout;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordfragment extends Fragment {
    String head,member_id;
    FragmentTouchListner mCallBack;
    boolean loaded=false;
    MyTextView cp_submit_tv;
    LinearLayout cp_submit;
    MyEditText  cp_new_pass, cp_confirm_pass,current_password;
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
        return inflater.inflate(R.layout.change_password_page, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        current_password = (MyEditText) view.findViewById(R.id.old_password);
        current_password.setHint(Settings.getword(getActivity(), "current_password") + "*");
        cp_new_pass = (MyEditText) view.findViewById(R.id.cp_new_password);
        cp_new_pass.setHint(Settings.getword(getActivity(), "new_password") + "*");
        cp_confirm_pass = (MyEditText) view.findViewById(R.id.cp_confirm_password);
        cp_confirm_pass.setHint(Settings.getword(getActivity(), "confirm_password") + "*");
        cp_submit_tv = (MyTextView) view.findViewById(R.id.cp_submit_tv);
        cp_submit_tv.setText(Settings.getword(getActivity(),"change_password"));
        cp_submit = (LinearLayout) view.findViewById(R.id.cp_submit_ll);
        cp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_password();

            }
        });
        member_id = Settings.getUserid(getActivity());
    }
    public  void change_password(){
        final String old_pass = current_password.getText().toString();
        final String new_pass = cp_new_pass.getText().toString();
        final String confirm_pass = cp_confirm_pass.getText().toString();
        if (old_pass.length() < 5)
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_current_pasword"), false);
        else if (new_pass.length() < 5)
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "password_lenth"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "password_lenth"), Toast.LENGTH_SHORT).show();
        else if (!new_pass.equals(confirm_pass)){
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_same_password"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_same_password"), Toast.LENGTH_SHORT).show();
            cp_confirm_pass.setText("");
        }else{
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
            progressDialog.show();
            progressDialog.setCancelable(false);
            String url = Settings.SERVERURL+"change-password.php?";

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
                            Log.e("msg", msg);
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            cp_new_pass.setText("");
                            cp_confirm_pass.setText("");
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
                    params.put("member_id",member_id);
                    params.put("password",new_pass);
                    params.put("cpassword",confirm_pass);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }

}
