package in.yellowsoft.darabeel;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Settingsfragment extends Fragment {
    String head;
    LinearLayout eng,arabic;
    TextView choose;
    FragmentTouchListner mCallBack;
    boolean loaded=false;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void language_set(String header);
//        public  Animation get_animation(Boolean enter, Boolean loaded);
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
        return inflater.inflate(R.layout.settings_page, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
//        loaded=true;
//        head=String.valueOf(Html.fromHtml(Settings.getword(getActivity(), "about_us")));
//        mCallBack.text_back_butt(head);
        eng=(LinearLayout)v.findViewById(R.id.sett_eng);
        arabic=(LinearLayout)v.findViewById(R.id.set_arabic);
        choose=(TextView)v.findViewById(R.id.choose_lang_settings);
        choose.setText(Settings.getword(getActivity(),"choose_language"));
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.set_user_language(getActivity(), "en");
                Settings.set_isfirsttime(getActivity(), "en");
                mCallBack.language_set("en");
            }
        });
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.set_user_language(getActivity(), "ar");
                Settings.set_isfirsttime(getActivity(), "ar");
                mCallBack.language_set("ar");
            }
        });

    }
}
