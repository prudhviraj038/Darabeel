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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Reviewsfragment extends Fragment {
    String head;
    Restaurants restaurants;
    FragmentTouchListner mCallBack;
    ImageView review_res_logo;
    LinearLayout review_rating;
    ListView listView;
    ReviewsAdapter reviewsAdapter;
    boolean loaded=false;
    public interface FragmentTouchListner {
        public void review_page_head(String header);
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
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter,loaded);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reviews_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        restaurants = (Restaurants)getArguments().getSerializable("restaurant");
        review_rating=(LinearLayout)view.findViewById(R.id.review_rating);
        Settings.set_rating(getActivity(), restaurants.rating, review_rating);
        review_res_logo=(ImageView)view.findViewById(R.id.res_review_logo);
        Picasso.with(getActivity()).load(restaurants.image).into(review_res_logo);
        listView=(ListView)view.findViewById(R.id.reviews_list);
        reviewsAdapter=new ReviewsAdapter(getActivity(),restaurants);
        listView.setAdapter(reviewsAdapter);
        head = restaurants.getTitle(getActivity());
        mCallBack.review_page_head(head);
    }
}
