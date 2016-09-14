package in.yellowsoft.darabeel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import android.widget.LinearLayout;


public class ContactUsFragment extends Fragment {
    MyTextView contact_us_txt,address_contact;
    MyEditText name,email_address,phone_number,message;
    AllApis allApis = new AllApis();
    boolean loaded=false;
    FragmentTouchListner mCallback;
    String head;
    AlertDialogManager alert = new AlertDialogManager();
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public  Animation get_animation(Boolean enter,Boolean loaded);

    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallback.get_animation(enter,loaded);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contct_us_screen, container, false);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
//        loaded=true;
        LinearLayout contact_us=(LinearLayout)v.findViewById(R.id.contact_us);

        contact_us_txt=(MyTextView)v.findViewById(R.id.contact_us_tv);
        contact_us_txt.setText(Settings.getword(getActivity(),"contact_us"));

        address_contact=(MyTextView)v.findViewById(R.id.address_contact);
        address_contact.setText(Html.fromHtml(Settings.getSettings(getActivity(), "contact" + Settings.get_lan(getActivity()))));
        head=String.valueOf(Settings.getword(getActivity(),"contact_us"));
        mCallback.text_back_butt(head);
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
        name = (MyEditText) v.findViewById(R.id.et_contctus_name);
        email_address = (MyEditText) v.findViewById(R.id.et_contactus_email);
        phone_number = (MyEditText) v.findViewById(R.id.et_contactus_mobile);
        message = (MyEditText) v.findViewById(R.id.et_contact_msg);
        name.setHint(Settings.getword(getActivity(), "contact_us_name"));
        email_address.setHint(Settings.getword(getActivity(), "contact_us_email"));
        phone_number.setHint(Settings.getword(getActivity(), "mobile_number"));
        message.setHint(Settings.getword(getActivity(), "contact_us_message"));


        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
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

  private void validatedata(){
      final String namee = name.getText().toString();
      final String email = email_address.getText().toString();
      final String phone = phone_number.getText().toString();
      final String msg = message.getText().toString();
        if(namee.equals("")){
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_name"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_name"), Toast.LENGTH_SHORT).show();
        }else if(email.equals("")){
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_email"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_email"),Toast.LENGTH_SHORT).show();
        }else if(phone.equals("")){
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_mobile"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_mobile"),Toast.LENGTH_SHORT).show();
        }else if(msg.equals("")){
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_message"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_message"),Toast.LENGTH_SHORT).show();
        }else{
            allApis.contact_us(getActivity(), namee, email, phone, msg);
        }
    }
}

