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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Chinni on 04-05-2016.
 */
public class VerificationFragment extends Fragment {
    String head,code;
    boolean loaded=false;
    FragmentTouchListner mCallBack;
    EditText enter_code;
    TextView submit_tv;
    LinearLayout submit_ll;
    ImageView tick;
    AlertDialogManager alert = new AlertDialogManager();
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void after_login();
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
        View rootview = inflater.inflate(R.layout.sms_verification, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        code=(String)getArguments().getString("code");
        tick=(ImageView)view.findViewById(R.id.tick_verification);
        enter_code=(EditText)view.findViewById(R.id.eenter_your_code);
        enter_code.setHint(Settings.getword(getActivity(),"enter_coupon"));
        submit_ll=(LinearLayout)view.findViewById(R.id.submit_verif_ll);
        submit_tv=(TextView)view.findViewById(R.id.submit_verif_tv);
        submit_tv.setText(Settings.getword(getActivity(), "submit"));
        head=Settings.getword(getActivity(),"title_verfication");
        mCallBack.text_back_butt(head);
        enter_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    tick.setVisibility(View.VISIBLE);
                } else
                    tick.setVisibility(View.GONE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        submit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("code_vr", code);
                if (enter_code.getText().toString().equals("")) {
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "enter_verification_code"), true);
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "enter_verification_code"), Toast.LENGTH_SHORT).show();
                } else if (!enter_code.getText().toString().equals(code)) {
                    alert.showAlertDialog(getActivity(), "Info",Settings.getword(getActivity(),"valid_verification_code"), true);
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "valid_verification_code"), Toast.LENGTH_SHORT).show();
                } else {
                    mCallBack.after_login();
                }
            }
        });
        enter_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().equals("6")) {
                    tick.setVisibility(View.VISIBLE);
                } else
                    tick.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
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
    }

}