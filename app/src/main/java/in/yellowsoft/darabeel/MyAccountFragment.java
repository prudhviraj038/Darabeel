package in.yellowsoft.darabeel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Chinni on 04-05-2016.
 */
public class MyAccountFragment extends Fragment implements ViewPager.OnPageChangeListener  {
    TextView sta_logout, sta_my_orders, sta_my_address, sta_edit_address, sta_change_pass, setting_tv,language_tv;
    LinearLayout my_order_ll, my_address_ll, edit_address, change_pass, settings, logout,area_list_my_acc, language;
    String head, member_id;
    int posi;
    boolean loade=false;
    ImageView my_ord_img, my_addr_img, edit_profile_img, settings_img, change_password_img;
    AllApis allApis = new AllApis();
    FragmentManager fragmentManager;
    FragmentTouchListner mCallBack;
    int loaded = 0;

    ViewPager viewPager;
    AlertDialogManager alert = new AlertDialogManager();
    public interface FragmentTouchListner {
        public void logout();
        public  Animation get_animation(Boolean enter,Boolean loaded);
        public void language();
        public void area_list(String id);
        public void text_back_butt_logout(String head);
//        public void text_back_butt(String head);
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter,loade);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.my_account_screen, container, false);
        return rootview;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (true) {
            loaded++;
            final View view = getView();
//            loade=true;
            head = Settings.getword(getActivity(),"my_account");
            mCallBack.text_back_butt_logout(head);
            fragmentManager = getActivity().getSupportFragmentManager();
            viewPager=(ViewPager)view.findViewById(R.id.pager);
            ScreenSlidePagerAdapter screenSlidePagerAdapter=new ScreenSlidePagerAdapter(fragmentManager);
            viewPager.setAdapter(screenSlidePagerAdapter);
            viewPager.setOnPageChangeListener(this);
//            viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper3);
            member_id = Settings.getUserid(getActivity());
            my_ord_img = (ImageView) view.findViewById(R.id.my_order_img);
            my_addr_img = (ImageView) view.findViewById(R.id.my_address_img);
            edit_profile_img = (ImageView) view.findViewById(R.id.edit_profile_img);
            settings_img = (ImageView) view.findViewById(R.id.settings_img);
            change_password_img = (ImageView) view.findViewById(R.id.change_pass_img);
            sta_my_orders = (TextView) view.findViewById(R.id.my_orders_tv);
            sta_my_orders.setText(Settings.getword(getActivity(), "my_orders"));
            sta_my_address = (TextView) view.findViewById(R.id.my_address_tv);
            sta_my_address.setText(Settings.getword(getActivity(), "address_list"));
            sta_edit_address = (TextView) view.findViewById(R.id.edit_address_my_acc_tv);
            sta_edit_address.setText(Settings.getword(getActivity(), "edit_profile"));
            sta_change_pass = (TextView) view.findViewById(R.id.change_password_my_acc_tv);
            sta_change_pass.setText(Settings.getword(getActivity(), "change_password"));
            setting_tv = (TextView) view.findViewById(R.id.setting_tv);
            setting_tv.setText(Settings.getword(getActivity(), "settings"));

//            order details

//            close1=(ImageView)view.findViewById(R.id.close_my_acc);
//            close1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    area_list_ll.setVisibility(View.GONE);
//                }
//            });
//            select_area_txt = (TextView) view.findViewById(R.id.select_area_my_acc);
//            select_area_txt.setText(Settings.getword(getActivity(), "area"));
            my_order_ll = (LinearLayout) view.findViewById(R.id.my_orders_ll);
            my_order_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    head = Settings.getword(getActivity(),"my_orders");
                    mCallBack.text_back_butt_logout(head);
                    viewPager.setCurrentItem(0);
                }
            });
            my_address_ll = (LinearLayout) view.findViewById(R.id.my_address_ll);
            my_address_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    head = Settings.getword(getActivity(),"address_list");
                    mCallBack.text_back_butt_logout(head);
                    viewPager.setCurrentItem(1);
//                    my_ord_img.setImageResource(R.drawable.my_orders);
//                    my_addr_img.setImageResource(R.drawable.blueaddresses);
//                    edit_profile_img.setImageResource(R.drawable.login_details);
//                    settings_img.setImageResource(R.drawable.settings_icon);
//                    change_password_img.setImageResource(R.drawable.lock);
//                    sta_my_orders.setTextColor(Color.parseColor("#512918"));
//                    sta_my_address.setTextColor(Color.parseColor("#d4005a"));
//                    sta_edit_address.setTextColor(Color.parseColor("#512918"));
//                    sta_change_pass.setTextColor(Color.parseColor("#512918"));
//                    setting_tv.setTextColor(Color.parseColor("#512918"));
                }
            });
            edit_address = (LinearLayout) view.findViewById(R.id.edit_profile);
            edit_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    head = Settings.getword(getActivity(),"edit_profile");
                    mCallBack.text_back_butt_logout(head);
                    viewPager.setCurrentItem(2);
//                    my_ord_img.setImageResource(R.drawable.my_orders);
//                    my_addr_img.setImageResource(R.drawable.addresses);
//                    edit_profile_img.setImageResource(R.drawable.bluelogin_details);
//                    settings_img.setImageResource(R.drawable.settings_icon);
//                    change_password_img.setImageResource(R.drawable.lock);
//                    sta_my_orders.setTextColor(Color.parseColor("#512918"));
//                    sta_my_address.setTextColor(Color.parseColor("#512918"));
//                    sta_edit_address.setTextColor(Color.parseColor("#d4005a"));
//                    sta_change_pass.setTextColor(Color.parseColor("#512918"));
//                    setting_tv.setTextColor(Color.parseColor("#512918"));
                }
            });
            change_pass = (LinearLayout) view.findViewById(R.id.change_password_my_acc);
            change_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    head = Settings.getword(getActivity(),"change_password");
                    mCallBack.text_back_butt_logout(head);
                    viewPager.setCurrentItem(4);
//                    my_ord_img.setImageResource(R.drawable.my_orders);
//                    my_addr_img.setImageResource(R.drawable.addresses);
//                    edit_profile_img.setImageResource(R.drawable.login_details);
//                    settings_img.setImageResource(R.drawable.settings_icon);
//                    change_password_img.setImageResource(R.drawable.lock_blue);
//                    sta_my_orders.setTextColor(Color.parseColor("#512918"));
//                    sta_my_address.setTextColor(Color.parseColor("#512918"));
//                    sta_edit_address.setTextColor(Color.parseColor("#512918"));
//                    sta_change_pass.setTextColor(Color.parseColor("#d4005a"));
//                    setting_tv.setTextColor(Color.parseColor("#512918"));
                }
            });

            settings = (LinearLayout) view.findViewById(R.id.settings);
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(3);
//                    my_ord_img.setImageResource(R.drawable.my_orders);
//                    my_addr_img.setImageResource(R.drawable.addresses);
//                    edit_profile_img.setImageResource(R.drawable.login_details);
//                    settings_img.setImageResource(R.drawable.settings_icon_blue);
//                    change_password_img.setImageResource(R.drawable.lock);
//                    sta_my_orders.setTextColor(Color.parseColor("#512918"));
//                    sta_my_address.setTextColor(Color.parseColor("#512918"));
//                    sta_edit_address.setTextColor(Color.parseColor("#512918"));
//                    sta_change_pass.setTextColor(Color.parseColor("#512918"));
//                    setting_tv.setTextColor(Color.parseColor("#d4005a"));
                }
            });
////            sta_logout = (TextView) view.findViewById(R.id.logout_tv);
//            sta_logout.setText(Settings.getword(getActivity(), "logout"));
//            logout = (LinearLayout) view.findViewById(R.id.logout);
//            logout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Settings.setUserid(getActivity(), "-1", "");
//                    mCallBack.logout();
//
//                }
//            });
//            language_tv = (TextView) view.findViewById(R.id.language_tv);
//            language_tv.setText(Settings.getword(getActivity(), "language"));
//            language = (LinearLayout) view.findViewById(R.id.language);
//            language.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mCallBack.language();
//                }
//            });

//            my_ord_img.setImageResource(R.drawable.bluemy_orders);
//            my_addr_img.setImageResource(R.drawable.addresses);
//            edit_profile_img.setImageResource(R.drawable.login_details);
//            settings_img.setImageResource(R.drawable.settings_icon);
//            change_password_img.setImageResource(R.drawable.lock);
//            sta_my_orders.setTextColor(Color.parseColor("#d4005a"));
//            sta_my_address.setTextColor(Color.parseColor("#512918"));
//            sta_edit_address.setTextColor(Color.parseColor("#512918"));
//            sta_change_pass.setTextColor(Color.parseColor("#512918"));
//            setting_tv.setTextColor(Color.parseColor("#512918"));
        }
        my_ord_img.setImageResource(R.drawable.bluemy_orders);
        sta_my_orders.setTextColor(Color.parseColor("#d4005a"));
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        my_ord_img.setImageResource(R.drawable.my_orders);
        my_addr_img.setImageResource(R.drawable.addresses);
        edit_profile_img.setImageResource(R.drawable.login_details);
        settings_img.setImageResource(R.drawable.settings_icon);
        change_password_img.setImageResource(R.drawable.lock);
        sta_my_orders.setTextColor(Color.parseColor("#512918"));
        sta_my_address.setTextColor(Color.parseColor("#512918"));
        sta_edit_address.setTextColor(Color.parseColor("#512918"));
        sta_change_pass.setTextColor(Color.parseColor("#512918"));
        setting_tv.setTextColor(Color.parseColor("#512918"));
        my_order_ll.setBackgroundColor(Color.parseColor("#d3d3d3"));
        my_address_ll.setBackgroundColor(Color.parseColor("#d3d3d3"));
        edit_address.setBackgroundColor(Color.parseColor("#d3d3d3"));
        change_pass.setBackgroundColor(Color.parseColor("#d3d3d3"));
        settings.setBackgroundColor(Color.parseColor("#d3d3d3"));
        switch (position) {
            case 0:
                my_ord_img.setImageResource(R.drawable.bluemy_orders);
                sta_my_orders.setTextColor(Color.parseColor("#d4005a"));
                my_order_ll.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                my_addr_img.setImageResource(R.drawable.blueaddresses);
                sta_my_address.setTextColor(Color.parseColor("#d4005a"));
                my_address_ll.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                edit_profile_img.setImageResource(R.drawable.bluelogin_details);
                sta_edit_address.setTextColor(Color.parseColor("#d4005a"));
                edit_address.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 3:
                settings_img.setImageResource(R.drawable.settings_icon_blue);
                setting_tv.setTextColor(Color.parseColor("#d4005a"));
                settings.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 4:
                change_password_img.setImageResource(R.drawable.lock_blue);
                sta_change_pass.setTextColor(Color.parseColor("#d4005a"));
                change_pass.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    MyOrdersfragment fragment = new MyOrdersfragment();
                    return fragment;
                case 1:
                    MyAddressfragment fragment1 = new MyAddressfragment();
                    return fragment1;
                case 2:
                    EditProfilefragment fragment2 = new EditProfilefragment();
                    return fragment2;
                case 3:
                    Settingsfragment fragment3 = new Settingsfragment();
                    return fragment3;
                case 4:
                    ChangePasswordfragment fragment4 = new ChangePasswordfragment();
                    return fragment4;

            }
           return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}

