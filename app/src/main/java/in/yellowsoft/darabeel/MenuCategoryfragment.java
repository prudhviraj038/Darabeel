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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuCategoryfragment extends Fragment {
    Restaurants restaurants;
    String head;
    ArrayList<Category> categories;
    LinearLayout rating;
    MyTextView review;
    boolean loaded=false;
    MenuCategoryAdapter menuCategoryAdapter;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void product_list(String rest_id,String cat_id,String cat_name,String type);
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
        return inflater.inflate(R.layout.menu_category_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
//        loaded=true;
        categories = new ArrayList<>();
        restaurants = (Restaurants)getArguments().getSerializable("restaurant");
        menuCategoryAdapter=new MenuCategoryAdapter(getActivity(),restaurants);
        review=(MyTextView)v.findViewById(R.id.review_res_page);
        review.setText("(" + restaurants.reviews + ")");
        rating=(LinearLayout)v.findViewById(R.id.rating_resta_page_ll);
        Settings.set_rating(getActivity(), restaurants.rating, rating);
        MyTextView resta_name=(MyTextView)v.findViewById(R.id.resta_name_cat_page);
        MyTextView sta_menu_cat=(MyTextView)v.findViewById(R.id.sta_menu_category);
        sta_menu_cat.setText(Settings.getword(getActivity(),"title_select_category"));
        MyTextView resta_items=(MyTextView)v.findViewById(R.id.resta_items_cat_page);
        ImageView res_img=(ImageView)v.findViewById(R.id.company_image);
        ImageView res_back_img=(ImageView)v.findViewById(R.id.company_background_image_cat);
        ListView cat_list=(ListView)v.findViewById(R.id.menu_cat_list);
        cat_list.setAdapter(menuCategoryAdapter);
        cat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallBack.product_list(restaurants.res_id,restaurants.menu.get(i).id,restaurants.menu.get(i).getTitle(getActivity()),"2");
            }
        });
            head=String.valueOf(Html.fromHtml(Settings.getword(getActivity(), "menu")));
            mCallBack.text_back_butt(head);

        resta_name.setText(restaurants.getTitle(getActivity()));
        resta_items.setText(restaurants.getsdescription(getActivity()));
        Picasso.with(getActivity()).load(restaurants.image).fit().into(res_img);
        Picasso.with(getActivity()).load(restaurants.banner).into(res_back_img);

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
//    private void getCategories(){
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        String url;
//        url = Settings.SERVERURL + "restaurants_cat.php";
//        Log.e("url", url);
//        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray jsonArray) {
//                categories.clear();
//                display_cat_list(jsonArray);
//                if(progressDialog!=null)
//                    progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // TODO Auto-generated method stub
//                Log.e("response is:", error.toString());
//                if(progressDialog!=null)
//                    progressDialog.dismiss();
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsObjRequest);
//    }


//
//    private void display_cat_list(JSONArray jsonArray) {
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            try {
//                JSONObject tempjson = jsonArray.getJSONObject(i);
//                Category category = new Category(tempjson.getString("title"), tempjson.getString("title_ar"), tempjson.getString("id"));
//                categories.add(category);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        menuCategoryAdapter.notifyDataSetChanged();
//    }

}
