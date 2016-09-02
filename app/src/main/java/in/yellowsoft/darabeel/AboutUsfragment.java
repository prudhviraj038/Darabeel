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


public class AboutUsfragment extends Fragment {
    String head;
    FragmentTouchListner mCallBack;
    boolean loaded=false;
    public interface FragmentTouchListner {
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
                    + " must implement Listneddr");
        }
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter,loaded);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_us_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
//        loaded=true;
        MyTextView about_us=(MyTextView)v.findViewById(R.id.aboutus_descri);
        MyTextView title_about_us=(MyTextView)v.findViewById(R.id.tittle_aboutus);
            head=String.valueOf(Html.fromHtml(Settings.getword(getActivity(), "about_us")));
            mCallBack.text_back_butt(head);
            title_about_us.setText(Html.fromHtml( Settings.getword(getActivity(),"about_us")));
            about_us.setText(Html.fromHtml(Settings.getSettings(getActivity(),"about"+Settings.get_lan(getActivity()))));

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
}
