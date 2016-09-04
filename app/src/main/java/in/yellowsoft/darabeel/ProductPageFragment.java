package in.yellowsoft.darabeel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

/**
 * Created by Chinni on 04-05-2016.
 */
public class ProductPageFragment extends Fragment {
    Products  products;
    int quantity=1,count=0,seek_value;
    int click=2,click1=1;
    boolean loaded=false;
    OptionsAdapter optionsAdapter;
    ListView group_list;
    GridView optionas_grid;
    LinearLayout rating,group_ll,minus,pluse,option_ll,add_to_cart_ll,option_img_ll,options_ll_list,spl_request_ll,add_more_ll,go_to_cart_ll,alert_add_cart;
    ImageView plus_img_option,plus_spl_request;
    MyEditText et_spl_request;
    MyTextView pro_name,pro_items,add_cart,number,spl_request,cost,pro_cost,option_title,add_more,go_to_cart,add_text;
    ProductPageAdapter productPageAdapter ;
    ArrayList<Area> groupItems;
    private SliderLayout mDemoSlider;
    String head;
    FragmentTouchListner mCallBack;
    AlertDialogManager alert = new AlertDialogManager();
    public interface FragmentTouchListner {
        public void product_page();
        public void add_to_cart(Products products, String quantity, String spl_req);
        public void cart_icon_dis(String header);
        public  Animation get_animation(Boolean enter,Boolean loaded);
        public int get_cart_count(String product_id);
        public void back_to_product();
//        public void text_back_butt(String header);
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
        View rootview = inflater.inflate(R.layout.product_page_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
//        about_image=(ImageView)view.findViewById(R.id.about_img);

        plus_img_option=(ImageView)view.findViewById(R.id.plus_option_listt);
        plus_spl_request=(ImageView)view.findViewById(R.id.plus_img_spl_request);
        option_img_ll=(LinearLayout)view.findViewById(R.id.option_ll_img);
        options_ll_list=(LinearLayout)view.findViewById(R.id.option_ll);
        alert_add_cart=(LinearLayout)view.findViewById(R.id.alert_cart_add);
        add_more_ll=(LinearLayout)view.findViewById(R.id.add_more_ll);
        go_to_cart_ll=(LinearLayout)view.findViewById(R.id.go_to_cart_ll);
        spl_request_ll=(LinearLayout)view.findViewById(R.id.spl_request_product_ll);
        products = (Products) getArguments().getSerializable("product");
        head=products.getTitle(getActivity());
//        mCallBack.text_back_butt(head);
        mDemoSlider = (SliderLayout)view.findViewById(R.id.product_background_image);
        for(int i=0;i<products.images.size();i++){
            DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
            defaultSliderView.image(products.images.get(i).img).setScaleType(BaseSliderView.ScaleType.CenterCrop);
            mDemoSlider.addSlider(defaultSliderView);
        }
        optionas_grid=(GridView)view.findViewById(R.id.options_list);
        optionsAdapter=new OptionsAdapter(getActivity(),products);
        optionas_grid.setAdapter(optionsAdapter);
        setGridViewHeightBasedOnItems(optionas_grid);
//        rating=(LinearLayout)view.findViewById(R.id.rating_product_page);
//        Settings.set_rating(getActivity(), restaurants.rating, rating);
//        review=(MyTextView)view.findViewById(R.id.review_product_page);
//        review.setText("(" + restaurants.reviews + ")");
        option_title = (MyTextView) view.findViewById(R.id.choice_product_page);
        option_title.setText(products.getoption_title(getActivity()));
        pro_name = (MyTextView) view.findViewById(R.id.product_name);
        add_more = (MyTextView) view.findViewById(R.id.add_more_pp);
        pro_cost = (MyTextView) view.findViewById(R.id.product_page_price);
        pro_cost.setText(products.price+" KD");
        add_more.setText(Settings.getword(getActivity(), "text_continue"));
        go_to_cart = (MyTextView) view.findViewById(R.id.go_to_cart_tv);
        go_to_cart.setText(Settings.getword(getActivity(), "text_checkout"));
        add_text = (MyTextView) view.findViewById(R.id.add_to_cart_msg);
        add_text.setText(Settings.getword(getActivity(), "message_open_cart"));
        spl_request = (MyTextView) view.findViewById(R.id.spl_request_product);
        spl_request.setText(Settings.getword(getActivity(), "special_request"));
        pro_items = (MyTextView) view.findViewById(R.id.product_items);
        et_spl_request = (MyEditText) view.findViewById(R.id.about_product);
        et_spl_request.setHint(Settings.getword(getActivity(),"special_request"));
        add_cart = (MyTextView) view.findViewById(R.id.add_cart);
//        add_cart.setText(Settings.getword(getActivity(), "add_to_cart"));
        cost = (MyTextView) view.findViewById(R.id.sta_cost_pro);
        cost.setText(Settings.getword(getActivity(), "cost"));
        add_to_cart_ll = (LinearLayout) view.findViewById(R.id.ll_add_to_cart);
        minus = (LinearLayout) view.findViewById(R.id.ll_minus);
        pluse = (LinearLayout) view.findViewById(R.id.ll_plus);
        number = (MyTextView) view.findViewById(R.id.number_pro);
        mCallBack.product_page();
        groupItems = new ArrayList<>();
        group_list = (ListView) view.findViewById(R.id.group_list);
        productPageAdapter = new ProductPageAdapter(getActivity(), products,this);
        group_list.setAdapter(productPageAdapter);
        plus_img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (options_ll_list.getVisibility()==View.VISIBLE) {
                    options_ll_list.setVisibility(View.GONE);
                    plus_img_option.setImageResource(R.drawable.plus_for_dara);
                    setGridViewHeightBasedOnItems(optionas_grid);
                    click = 2;
                } else {
                    options_ll_list.setVisibility(View.VISIBLE);
                    plus_img_option.setImageResource(R.drawable.minus_for_dara);
                    setGridViewHeightBasedOnItems(optionas_grid);
                    click = 1;
                }
            }
        });
        plus_spl_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click1==1){
                    spl_request_ll.setVisibility(View.VISIBLE);
                    plus_spl_request.setImageResource(R.drawable.minus_for_dara);
                    click1=2;
                }else{
                    spl_request_ll.setVisibility(View.GONE);
                    plus_spl_request.setImageResource(R.drawable.plus_for_dara);
                    click1=1;
                }
            }
        });
//        setListViewHeightBasedOnItems(group_list);


//        about_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//                alert.setTitle("About Product");
//                alert.setMessage(products.getdescription(getActivity()));
//               alert.setCancelable(true);
//            alert.show();
//        }
//
//
//        });


        pro_name.setText(products.getTitle(getActivity()));
        pro_items.setText(products.getdescription(getActivity()));
        group_ll=(LinearLayout)view.findViewById(R.id.group_ll);
        option_ll=(LinearLayout)view.findViewById(R.id.option_llll);
        Log.e("group_size",String.valueOf(products.groups.size()+String.valueOf(products.groups.size())));
        if(products.groups.size()==0){
            group_ll.setVisibility(View.GONE);
        }else{
            group_ll.setVisibility(View.VISIBLE);
        }
        if(products.options.size()==0){
            option_ll.setVisibility(View.GONE);
        }else{
            option_ll.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < products.groups.size(); i++) {
            Area person = new Area(products.groups.get(i).group, products.groups.get(i).group, products.groups.get(i).group, true);
            groupItems.add(person);
            for (int j = 0; j < products.groups.get(i).addons.size(); j++) {
                Area person1 = new Area(products.groups.get(i).addons.get(j).price, products.groups.get(i).addons.get(j).addon, products.groups.get(i).addons.get(j).addon, false);
                groupItems.add(person1);
            }
        }
        productPageAdapter.notifyDataSetChanged();
//        number.setText(String.valueOf(quantity));
//        SeekBar seek = (SeekBar)view.findViewById(R.id.materialSeekBar);
//        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // TODO Auto-generated method stub
//                quantity = progress;
//                number.setText(String.valueOf(progress));
//                Log.e("progress", String.valueOf(quantity));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//            }
//        });



        number.setText(String.valueOf(quantity));
        pluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(String.valueOf(quantity));
                quantity++;
                number.setText(String.valueOf(quantity));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    number.setText(String.valueOf(quantity));
                } else {
                    number.setText(String.valueOf(quantity));
                }
            }
        });


        add_to_cart_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Settings.getUserid(getActivity()).equals("-1"))
//                    mCallBack.login();
//                else {
                boolean addon_ok = true;
                for (int i = 0; i < products.groups.size(); i++) {
                    count = 0;
                    for (int j = 0; j < products.groups.get(i).addons.size(); j++) {
                        if (products.groups.get(i).addons.get(j).isselected) {
                            count++;
                        }
                    }
//                            Toast.makeText(getActivity(),"cont updated",Toast.LENGTH_SHORT).show();
                    if (count < Integer.parseInt(products.groups.get(i).min)) {
                        alert.showAlertDialog(getActivity(), "Info", "you should select " + Integer.parseInt(products.groups.get(i).min) + "addons in " + products.groups.get(i).group, false);
//                            Toast.makeText(getActivity(), "you should select " +Integer.parseInt(products.groups.get(i).min)+"addons in "+products.groups.get(i).group, Toast.LENGTH_SHORT).show();
                        addon_ok = false;
                    }

                }
                if (addon_ok) {
                    Log.e("status",products.restaurant.status);
                  if(products.restaurant.status.equals("Open")) {
//
                      if(Integer.parseInt(products.qut) <quantity + mCallBack.get_cart_count(products.res_id)){

                          if(mCallBack.get_cart_count(products.res_id)>0)
                              alert.showAlertDialog(getActivity(), "Info",Settings.getword(getActivity(),"out_stock")+" select "+String.valueOf(Integer.parseInt(products.qut)-mCallBack.get_cart_count(products.res_id))+" products, " + String.valueOf(mCallBack.get_cart_count(products.res_id)) + " products already in cart" , true);

                          else
                          alert.showAlertDialog(getActivity(), "Info",Settings.getword(getActivity(),"out_stock")+" select "+products.qut+" products", true);


                      }else{
                          mCallBack.add_to_cart(products, String.valueOf(quantity),et_spl_request.getText().toString());
//                          alert_add_cart.setVisibility(View.VISIBLE);
                      }

                  }else
                      alert.showAlertDialog(getActivity(),
                              "Info",
                              Settings.getword(getActivity(),"close_msg"), true);

                }
            }
//            }
        });
//        add_more_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCallBack.add_to_cart(products, String.valueOf(quantity),et_spl_request.getText().toString());
//                mCallBack.back_to_product();
//                alert_add_cart.setVisibility(View.GONE);
//            }
//        });
//        go_to_cart_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCallBack.add_to_cart(products, String.valueOf(quantity),et_spl_request.getText().toString());
//                alert_add_cart.setVisibility(View.GONE);
//            }
//        });
//

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
        mCallBack.cart_icon_dis(head);
    }
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
    public static boolean setGridViewHeightBasedOnItems(GridView gridView) {

        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            int lastItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, gridView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
                lastItemsHeight=item.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            if(numberOfItems%2==1)
                totalItemsHeight=totalItemsHeight+lastItemsHeight;

            params.height = totalItemsHeight/2;
            gridView.setLayoutParams(params);
            gridView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
    public  void call_list(){
       // setListViewHeightBasedOnItems(group_list);
    }

}
