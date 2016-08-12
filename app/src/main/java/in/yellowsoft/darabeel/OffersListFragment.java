package in.yellowsoft.darabeel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Chinni on 04-05-2016.
 */
public class OffersListFragment extends Fragment {
    String head;
    TextView show_menu;
    ImageView res_img;
    LinearLayout rating_ll,show_menu_ll;
    Restaurants restaurants;
    OffersAdapter offersAdapter;
    FragmentTouchListner mCallBack;
    boolean loaded=false;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void songselected(Restaurants restaurant);
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
        View rootview = inflater.inflate(R.layout.offer_list_screen1, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        restaurants=(Restaurants)getArguments().getSerializable("restaurants");
        head=Settings.getword(getActivity(),"promotions");
        offersAdapter=new OffersAdapter(getActivity(),restaurants,this);
        GridView offers=(GridView)view.findViewById(R.id.offer_list1);
        offers.setAdapter(offersAdapter);
        mCallBack.text_back_butt(head);
//        mCallBack.songselected(restaurants);
        show_menu = (TextView) view.findViewById(R.id.promotion_price);
        show_menu.setText(Settings.getword(getActivity(),"show_menu"));
        res_img = (ImageView) view.findViewById(R.id.offer_img_promtions);
        Picasso.with(getActivity()).load(restaurants.image).fit().into(res_img);
        rating_ll=(LinearLayout)view.findViewById(R.id.res_promotion_rating);
        Settings.set_rating(getActivity(),restaurants.rating, rating_ll);
        show_menu_ll=(LinearLayout)view.findViewById(R.id.show_mwnu_pro_ll);
        show_menu_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.songselected(restaurants);
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