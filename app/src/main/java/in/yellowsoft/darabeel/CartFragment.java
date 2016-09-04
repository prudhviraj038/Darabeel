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
import android.widget.LinearLayout;


import java.util.ArrayList;


/**
 * Created by Chinni on 04-05-2016.
 */
public class CartFragment extends Fragment {
    MyTextView min_order_amount,total_amount,stat_min_order,stat_total,tv_continuee,sta_how_do,sta_login,sta_guest,sta_cancel;
    GridView cart_list;
    ArrayList<CartItem> cart_items;
    LinearLayout cart_continue,cancel_ll,login_ll,guest_ll,cart_alert;
    Float total = 0f;
    boolean loaded=false;
    AlertDialogManager alert = new AlertDialogManager();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void to_login();
        public void final_page(ArrayList<CartItem> cartItem);
        public  Animation get_animation(Boolean enter,Boolean loaded);
        public void incart_page();
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
        View rootview = inflater.inflate(R.layout.cart_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
//        loaded=true;
        cart_items=new ArrayList<>();
        cart_items=(ArrayList)getArguments().getSerializable("cart_items");
        stat_min_order=(MyTextView)v.findViewById(R.id.static_min_order_amount);
        stat_min_order.setText(Settings.getword(getActivity(),"min_order_amount"));
        stat_total=(MyTextView)v.findViewById(R.id.static_total_amount);
        stat_total.setText(Settings.getword(getActivity(),"your_total_amount"));
        tv_continuee=(MyTextView)v.findViewById(R.id.tv_continue);
        tv_continuee.setText(Settings.getword(getActivity(),"continue"));
        min_order_amount=(MyTextView)v.findViewById(R.id.min_order_amount);

        sta_how_do=(MyTextView)v.findViewById(R.id.alert_cart_title);
        sta_how_do.setText(Settings.getword(getActivity(),"login_to_checkout"));
        sta_login=(MyTextView)v.findViewById(R.id.cart_login);
        sta_login.setText(Settings.getword(getActivity(),"login"));
        sta_guest=(MyTextView)v.findViewById(R.id.cart_guest);
        sta_guest.setText(Settings.getword(getActivity(),"guest_checkout"));
        sta_cancel=(MyTextView)v.findViewById(R.id.cart_cancel);
        sta_cancel.setText(Settings.getword(getActivity(),"cancel"));
        login_ll=(LinearLayout)v.findViewById(R.id.cart_login_ll);
        guest_ll=(LinearLayout)v.findViewById(R.id.cart_guest_ll);
        cancel_ll=(LinearLayout)v.findViewById(R.id.cart_cancel_ll);
        cart_alert=(LinearLayout)v.findViewById(R.id.cart_alert);


        total_amount=(MyTextView)v.findViewById(R.id.total_amount);
        for(int i=0;i<cart_items.size();i++){
            Float quty=0f;
            Float price=0f;
            quty=Float.parseFloat(cart_items.get(i).quantity);
            price=Float.parseFloat(cart_items.get(i).products.cart_price);
            total=total+(quty*price);
            Log.e("cart_count", String.valueOf(cart_items.size()));
            Log.e("cart_count",String.valueOf(cart_items.get(0).products.getTitle(getActivity())));
//            Log.e("cart_count",String.valueOf(cart_items.get(0).products.restaurant.getTitle(getActivity())));
            min_order_amount.setText(cart_items.get(0).products.restaurant.min +" KD");
        }
        total_amount.setText(String.valueOf(total)+" KD");
        CartPageAdapter cartPageAdapter=new CartPageAdapter(getActivity(),cart_items,this);
        cart_list=(GridView)v.findViewById(R.id.cart_grid);
        cart_list.setAdapter(cartPageAdapter);
        cart_continue=(LinearLayout)v.findViewById(R.id.continue_cart);
        String head=Settings.getword(getActivity(),"cart");
        mCallBack.text_back_butt(head);
        mCallBack.incart_page();
        cart_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart_items.size() == 0) {
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "no_items_in_cart"), false);
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "no_items_in_cart"), Toast.LENGTH_SHORT).show();
                } else {
                    if (!Settings.getUserid(getActivity()).equals("-1")) {
                        if (Float.parseFloat(cart_items.get(0).products.restaurant.min) <= total) {
                           mCallBack.final_page(cart_items);
                        } else {
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "total_amnt_greater_min_amnt"), false);
//                            Toast.makeText(getActivity(), Settings.getword(getActivity(), "total_amnt_greater_min_amnt"), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        cart_alert.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        login_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.to_login();
                cart_alert.setVisibility(View.GONE);
            }
        });
        guest_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Float.parseFloat(cart_items.get(0).products.restaurant.min) <= total) {
                    mCallBack.final_page(cart_items);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("cart_items", cart_items);
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FinalFragment finalFragment = new FinalFragment();
//                    finalFragment.setArguments(bundle);
//                    fragmentManager.beginTransaction().replace(R.id.container_main, finalFragment).addToBackStack(null).commit();
                } else {
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "total_amnt_greater_min_amnt"), false);
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "total_amnt_greater_min_amnt"), Toast.LENGTH_SHORT).show();
                }
                cart_alert.setVisibility(View.GONE);
            }
        });
        cancel_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart_alert.setVisibility(View.GONE);
            }
        });

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