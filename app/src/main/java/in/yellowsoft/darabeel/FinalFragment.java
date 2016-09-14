package in.yellowsoft.darabeel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Chinni on 04-05-2016.
 */
public class FinalFragment extends Fragment {
    ArrayList<CartItem> cart_items;
    ImageView cash_image,knet_image,cash_img,knet_img,credit_card_image,drop_down,plus_spl_comments,plue_img_coupon,later_img,now_img,date_time;
    MyTextView areaa,block,street,building,floor,apartment,mobile,sub_total,discount,delivery_charges,grand_total,summery,
            tv_cash,tv_knet,tv_credit_card,payment_option,delivery_option,tv_proceed_pay,stat_sub_total,sta_discount,stat_delivery_charges,sta_dara,dara,address_guest,
            stat_grand_total,stat_tv_area,stat_tv_block,stat_tv_street,stat_tv_building,stat_tv_floor,stat_tv_apartment,addr_name_tv,
            stat_tv_mobile,tv_edit,cancel_tv,save_tv,tv_add,tv_spl_com,tv_coupon_code,tv_submit,date_tv,time_tv,gues_fname,
            guest_lname,guest_email,guest_home_ph,guest_work_ph,add_add,add_area,later_tv,now_tv,cancel_add_tv,sta_reward_title,
            sta_reward_tv,reward_tv,use_tv,dnt_use_tv;
    MyEditText e_area, e_block, e_street, e_building, e_floor,e_aprtment,e_mobile,spl_comment,coupon_code,addr_name,
            et_fname,et_lname,et_email,et_work_ph,et_home_ph,add_et_addresss_name,add_et_block,add_et_street,add_et_floor,
            add_et_buillding,add_et_flat,add_et_mobile,add_et_directions;
    LinearLayout edit,add,cancel,save_ll,submit,ll_cash,ll_knet,ll_credit_card,ll_proceed_pay,date_lll,time_ll,drop_down_ll,
            address_guest_ll,mobile_ll_final,spl_com_ll,coupon_ll,add_add_ll,cancel_add_address_fin,later,now,dt_ll,
            reward_pop,use_ll,dnt_use_ll;
    JSONObject jsonObjecttosend = new JSONObject();
    FinalPageAdapter finalPageAdapter;
    String rest_id,product_id,quantity,pricee,coupon,date="0",time="0",date1,time1,t1;
    String check;
    Float total = 0f;
    Float dara_charges=0f;
    Float del_total = 0f;
    Float grn_total = 0f;
    String delivery,reward_price,rew_price="0";
    boolean islater=false;
    int hour,minutes;
    Float g_total = 0f;
    Float dis_amount = 0f;
    ViewFlipper viewFlipper;
    JSONObject place_order_object = new JSONObject();
    String pay_met="";
    private int mYear, mMonth, mDay, mHour, mMinute;
    boolean iseditfield=false;
    boolean loaded=false;
    boolean isaddfield=false;
    LinearLayout personal_details;
    ArrayList<String> address_id;
    ArrayList<String> address_title;
    String addrs_id,selected_area_id;
    String spl_req;
    ArrayList<Addresss> address_list;
    int click=1,click1=1;
    FragmentTouchListner mCallBack;
    AlertDialogManager alert = new AlertDialogManager();
    public interface FragmentTouchListner {
        public void text_back_butt_final_add(String header);
        public void text_back_butt(String header);
        public void gotoinvoicepage(JsonOrders invoice);
        public  Animation get_animation(Boolean enter,Boolean loaded);
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter,loaded);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.final_screen, container, false);
        return rootview;
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


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
        String head=Settings.getword(getActivity(), "final");
        mCallBack.text_back_butt(head);
        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper6);
        time="";
        date="";
        time1="";
        date1="";
        get_reward();
        address_list=new ArrayList<>();
        cart_items=new ArrayList<>();
        cart_items=(ArrayList)getArguments().getSerializable("cart_items");
        address_id= new ArrayList<>();
        address_title=new ArrayList<>();
        address_list=new ArrayList<>();
        mobile_ll_final=(LinearLayout)view.findViewById(R.id.mobile_ll_final);
        sta_reward_title=(MyTextView)view.findViewById(R.id.sta_title_reward);
        sta_reward_title.setText(Settings.getword(getActivity(),"title_reward"));
        reward_tv=(MyTextView)view.findViewById(R.id.reward_tv);
//        reward_tv.setText(Settings.getword(getActivity(),"reward_price"));

        sta_reward_tv=(MyTextView)view.findViewById(R.id.sta_reward_tv);
        sta_reward_tv.setText(Settings.getword(getActivity(),"reward_price"));

        use_tv=(MyTextView)view.findViewById(R.id.final_use);
        use_tv.setText(Settings.getword(getActivity(),"use"));
        dnt_use_tv=(MyTextView)view.findViewById(R.id.dnt_use);
        dnt_use_tv.setText(Settings.getword(getActivity(),"dont_use"));
        reward_pop=(LinearLayout)view.findViewById(R.id.reward_pop);
        use_ll=(LinearLayout)view.findViewById(R.id.final_use_ll);
        dnt_use_ll=(LinearLayout)view.findViewById(R.id.final_dnt_use_ll);

        summery=(MyTextView)view.findViewById(R.id.summery);
        summery.setText(Settings.getword(getActivity(),"summary"));
        gues_fname=(MyTextView)view.findViewById(R.id.guest_fname);
        gues_fname.setText(Settings.getword(getActivity(),"title_first_name"));
        guest_lname=(MyTextView)view.findViewById(R.id.guest_lname);
        guest_lname.setText(Settings.getword(getActivity(),"title_last_name"));
        guest_home_ph=(MyTextView)view.findViewById(R.id.guest_home_ph);
        guest_home_ph.setText(Settings.getword(getActivity(),"mobile"));
        guest_work_ph=(MyTextView)view.findViewById(R.id.guest_work_ph);
        guest_work_ph.setText(Settings.getword(getActivity(),"extra_mobile"));
        guest_email=(MyTextView)view.findViewById(R.id.guest_email);
        guest_email.setText(Settings.getword(getActivity(),"email_address"));
        et_fname = (MyEditText) view.findViewById(R.id.et_guest_fname);
        et_lname = (MyEditText) view.findViewById(R.id.et_guest_lname);
        et_email = (MyEditText) view.findViewById(R.id.et_guest_email);
        et_home_ph = (MyEditText) view.findViewById(R.id.et_guest_home_ph);
        et_work_ph = (MyEditText) view.findViewById(R.id.et_guest_work_ph);
        personal_details=(LinearLayout)view.findViewById(R.id.personal_details_rl);
        if(Settings.getUserid(getActivity()).equals("-1")){
            personal_details.setVisibility(View.VISIBLE);
        }else {
            personal_details.setVisibility(View.GONE);
        }
        areaa=(MyTextView)view.findViewById(R.id.tv_area);
        block=(MyTextView)view.findViewById(R.id.tv_block);
        street=(MyTextView)view.findViewById(R.id.tv_street);
        building=(MyTextView)view.findViewById(R.id.tv_buildng);
        floor=(MyTextView)view.findViewById(R.id.tv_floor);
        apartment=(MyTextView)view.findViewById(R.id.tv_flat);
        mobile=(MyTextView)view.findViewById(R.id.tv_mobile);
//        addr_name_tv=(MyTextView)view.findViewById(R.id.et_addr_name_tv);
//        addr_name = (MyEditText) view.findViewById(R.id.et_addr_name);
        e_area = (MyEditText) view.findViewById(R.id.et_area);
        e_block = (MyEditText) view.findViewById(R.id.et_block);
        e_street = (MyEditText) view.findViewById(R.id.et_street);
        e_building = (MyEditText) view.findViewById(R.id.et_building);
        e_floor = (MyEditText) view.findViewById(R.id.et_floor);
        e_aprtment = (MyEditText) view.findViewById(R.id.et_flat);
        e_mobile = (MyEditText) view.findViewById(R.id.et_mobile);

        add_et_block = (MyEditText) view.findViewById(R.id.block_add_fin);
        add_et_street = (MyEditText) view.findViewById(R.id.street_add_fin);
        add_et_buillding = (MyEditText) view.findViewById(R.id.buillding_add_fin);
        add_et_floor = (MyEditText) view.findViewById(R.id.floor_add_fin);
        add_et_flat = (MyEditText) view.findViewById(R.id.flat_add_fine);
        add_et_mobile = (MyEditText) view.findViewById(R.id.mobile_add_fin);
        add_et_directions = (MyEditText) view.findViewById(R.id.extra_direction_fin);
        add_et_addresss_name = (MyEditText) view.findViewById(R.id.address_name_fin);
        add_area=(MyTextView)view.findViewById(R.id.area_add_fin);
        add_add=(MyTextView)view.findViewById(R.id.add_address_final_tv);
        add_add.setText(Settings.getword(getActivity(),"add_address"));
        add_add_ll=(LinearLayout)view.findViewById(R.id.add_address_final_ll);
        cancel_add_address_fin=(LinearLayout)view.findViewById(R.id.cancel_final_add_address_ll);
        cancel_add_address_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(0);
                String head2=Settings.getword(getActivity(), "final");
                mCallBack.text_back_butt(head2);
            }
        });

        e_area.setText(Settings.getArea_name(getActivity()));
        areaa.setText(Settings.getArea_name(getActivity()));
//        if (!Settings.get_Address_json(getActivity()).equals("-1")) {
//            try {
//                JSONObject jsonObject = new JSONObject(Settings.get_Address_json(getActivity()));
////                e_area.setText(jsonObject.getString("area"));
//                e_block.setText(jsonObject.getString("block"));
//                e_street.setText(jsonObject.getString("street"));
//                e_building.setText(jsonObject.getString("building"));
//                e_floor.setText(jsonObject.getString("floor"));
//                e_aprtment.setText(jsonObject.getString("flat"));
//                e_mobile.setText(jsonObject.getString("mobile"));
////                areaa.setText(jsonObject.getString("area"));
//                block.setText(jsonObject.getString("block"));
//                street.setText(jsonObject.getString("street"));
//                building.setText(jsonObject.getString("building"));
//                floor.setText(jsonObject.getString("floor"));
//                apartment.setText(jsonObject.getString("flat"));
//                mobile.setText(jsonObject.getString("mobile"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        sub_total=(MyTextView)view.findViewById(R.id.sub_total_final);
        discount=(MyTextView)view.findViewById(R.id.discount_final);
        delivery_charges=(MyTextView)view.findViewById(R.id.delivery_charges_final);
        delivery_charges.setText(Settings.getDelivery_charges(getActivity())+" KD");

        dara=(MyTextView)view.findViewById(R.id.dara_charges_final);
        dara.setText(Settings.getSettings(getActivity(), "darabeel_charges")+" KD");
        grand_total=(MyTextView)view.findViewById(R.id.grand_total_final);
        stat_sub_total=(MyTextView)view.findViewById(R.id.static_sub_total);
        stat_sub_total.setText(Settings.getword(getActivity(), "sub_total"));
        sta_discount=(MyTextView)view.findViewById(R.id.sta_discount);
        sta_discount.setText(Settings.getword(getActivity(),"discount"));
        stat_delivery_charges=(MyTextView)view.findViewById(R.id.static_delivery_charges);
        stat_delivery_charges.setText(Settings.getword(getActivity(),"delivery_charges"));
        stat_grand_total=(MyTextView)view.findViewById(R.id.static_grand_total);
        stat_grand_total.setText(Settings.getword(getActivity(),"grand_total"));
        sta_dara=(MyTextView)view.findViewById(R.id.sta_dara_charges_final);
        Log.e("dara_c", Settings.getword(getActivity(), "darabeel_charges"));
        sta_dara.setText(Settings.getword(getActivity(), "darabeel_charges"));
        tv_cash=(MyTextView)view.findViewById(R.id.tv_cash_final);
        tv_cash.setText(Settings.getword(getActivity(), "cash"));
        tv_knet=(MyTextView)view.findViewById(R.id.tv_knet_final);
        tv_knet.setText(Settings.getword(getActivity(),"knet"));
        tv_credit_card=(MyTextView)view.findViewById(R.id.tv_credit_card_final);
        tv_credit_card.setText(Settings.getword(getActivity(),"credit_cards"));

        payment_option=(MyTextView)view.findViewById(R.id.tv_payment_option);
        payment_option.setText(Settings.getword(getActivity(),"payment_option"));

        delivery_option=(MyTextView)view.findViewById(R.id.tv_delivery_time_option);
        delivery_option.setText(Settings.getword(getActivity(),"delivery_option"));

        tv_proceed_pay=(MyTextView)view.findViewById(R.id.tv_proceed_pay);
        tv_proceed_pay.setText(Settings.getword(getActivity(),"proceed_to_pay"));
        address_guest=(MyTextView)view.findViewById(R.id.address_guest);
        address_guest.setText(Settings.getword(getActivity(),"address"));
//        addr_name_tv.setText(Settings.getword(getActivity(),"address"));
        stat_tv_area=(MyTextView)view.findViewById(R.id.static_tv_area);
        stat_tv_area.setText(Settings.getword(getActivity(),"area"));
        stat_tv_block=(MyTextView)view.findViewById(R.id.static_tv_block);
        stat_tv_block.setText(Settings.getword(getActivity(),"block"));
        stat_tv_street=(MyTextView)view.findViewById(R.id.static_tv_street);
        stat_tv_street.setText(Settings.getword(getActivity(),"street_name"));
        stat_tv_building=(MyTextView)view.findViewById(R.id.static_tv_building);
        stat_tv_building.setText(Settings.getword(getActivity(),"building_name"));
        stat_tv_floor=(MyTextView)view.findViewById(R.id.static_tv_floor);
        stat_tv_floor.setText(Settings.getword(getActivity(),"floor"));
        stat_tv_apartment=(MyTextView)view.findViewById(R.id.static_tv_aprtment);
        stat_tv_apartment.setText(Settings.getword(getActivity(),"apartment"));
        stat_tv_mobile=(MyTextView)view.findViewById(R.id.static_tv_mobile);
        stat_tv_mobile.setText(Settings.getword(getActivity(),"mobile"));
        tv_edit=(MyTextView)view.findViewById(R.id.tv_edit_address);
        tv_edit.setText(Settings.getword(getActivity(),"edit_address"));
        tv_add=(MyTextView)view.findViewById(R.id.tv_add_address);
        tv_add.setText(Settings.getword(getActivity(),"add_address"));
        cancel_tv=(MyTextView)view.findViewById(R.id.cancel);
        cancel_tv.setText(Settings.getword(getActivity(),"cancel"));
        save_tv=(MyTextView)view.findViewById(R.id.save_tv);
        save_tv.setText(Settings.getword(getActivity(),"save"));
        tv_spl_com=(MyTextView)view.findViewById(R.id.tv_spl_comments);
        tv_spl_com.setText(Settings.getword(getActivity(),"special_comments"));
        tv_coupon_code=(MyTextView)view.findViewById(R.id.tv_coupon);
        tv_coupon_code.setText(Settings.getword(getActivity(),"coupon_code"));
        tv_submit=(MyTextView)view.findViewById(R.id.tv_submit_final);
        tv_submit.setText(Settings.getword(getActivity(),"submit"));
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        date_tv=(MyTextView)view.findViewById(R.id.delivery_date);
        String md1;
        if (mDay < 10)
            md1 = "0" + mDay;
        else
            md1 = String.valueOf(mDay);
        String mm1;
        if (mMonth < 10)
            mm1 = "0" + mMonth;
        else
            mm1 = String.valueOf(mMonth);
        date_tv.setText(md1+" - "+mm1+" - "+mYear);
        time_tv=(MyTextView)view.findViewById(R.id.delivery_time);
        hour = mHour;
        minutes = mMinute;
        String timeSet = "";
        if (hour > 12) {
            hour -= 12;
            timeSet = "PM";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "AM";
        } else if (hour == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String  hour1;
        if (hour < 10)
            hour1 = "0" + hour;
        else
            hour1 = String.valueOf(hour);
        String min = "";
        if (minutes < 10)
            min = "0" + minutes ;
        else
            min = String.valueOf(minutes);
        time_tv.setText(new StringBuilder().append(hour1).append(':')
                .append(min ).append(" ").append(timeSet).toString());
        plus_spl_comments=(ImageView)view.findViewById(R.id.plus_img_spl_comm);
        plue_img_coupon=(ImageView)view.findViewById(R.id.plus_img_coupon);
        spl_com_ll=(LinearLayout)view.findViewById(R.id.spl_comm_ll);
        coupon_ll=(LinearLayout)view.findViewById(R.id.coupon_ll_select);
        plus_spl_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click == 1) {
                    spl_com_ll.setVisibility(View.VISIBLE);
                    plus_spl_comments.setImageResource(R.drawable.minus_for_dara);
                    click = 2;
                } else {
                    spl_com_ll.setVisibility(View.GONE);
                    plus_spl_comments.setImageResource(R.drawable.plus_for_dara);
                    click = 1;
                }
            }
        });
        plue_img_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click1 == 1) {
                    coupon_ll.setVisibility(View.VISIBLE);
                    plue_img_coupon.setImageResource(R.drawable.minus_for_dara);
                    click1 = 2;
                } else {
                    coupon_ll.setVisibility(View.GONE);
                    plue_img_coupon.setImageResource(R.drawable.plus_for_dara);
                    click1 = 1;
                }
            }
        });

        for(int i=0;i<cart_items.size();i++){
            spl_req=cart_items.get(i).spl_request;
            product_id=cart_items.get(i).products.res_id;
            rest_id=cart_items.get(i).products.restaurant.res_id;
            quantity=cart_items.get(i).quantity;
            pricee=cart_items.get(i).products.cart_price;
            Float quty=0f;
            Float price=0f;
            quty=Float.parseFloat(cart_items.get(i).quantity);
            price=Float.parseFloat(cart_items.get(i).products.cart_price);
            total=total+(quty*price);

        }
        discount.setText("0.000 KD");
        sub_total.setText(String.format("%.3f", total) + " KD");
        del_total = Float.parseFloat(Settings.getDelivery_charges(getActivity()));
        dara_charges=Float.parseFloat(Settings.getSettings(getActivity(), "darabeel_charges"));

        grn_total= total+del_total+dara_charges;
        g_total=grn_total;
        grand_total.setText(String.format("%.3f", g_total) + " KD");

        spl_comment = (MyEditText) view.findViewById(R.id.spl_instructions);
        spl_comment.setHint(Settings.getword(getActivity(), "special_comments"));
        coupon_code = (MyEditText) view.findViewById(R.id.coupon_codes);
        coupon_code.setHint(Settings.getword(getActivity(), "coupon_code"));

        later=(LinearLayout)view.findViewById(R.id.later_ll);
        now=(LinearLayout)view.findViewById(R.id.now_ll);
        later_tv=(MyTextView)view.findViewById(R.id.later_tv);
        later_tv.setText(Settings.getword(getActivity(), "later"));
        now_tv=(MyTextView)view.findViewById(R.id.now_tv);
        now_tv.setText(Settings.getword(getActivity(), "now"));
        cancel_add_tv=(MyTextView)view.findViewById(R.id.cancel_final_add_address_tv);
        cancel_add_tv.setText(Settings.getword(getActivity(), "cancel"));
        later_img=(ImageView)view.findViewById(R.id.later_img);
        now_img=(ImageView)view.findViewById(R.id.now_img);
        date_time=(ImageView)view.findViewById(R.id.date_time1);
        dt_ll=(LinearLayout)view.findViewById(R.id.date_time);
        date_lll=(LinearLayout)view.findViewById(R.id.date);
        time_ll=(LinearLayout)view.findViewById(R.id.time);
        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now_img.setImageResource(R.drawable.ic_option_pink);
                later_img.setImageResource(R.drawable.ic_option_brown);
//                date1=mDay+"-"+mMonth+"-"+mYear;
//                time1=mHour+":"+mMinute;
                delivery="1";
                islater=false;
                date="";
                time="";
                date1="0";
                time1="0";
                t1="0";

            }
        });

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                later_img.setImageResource(R.drawable.ic_option_pink);
                now_img.setImageResource(R.drawable.ic_option_brown);
                delivery="2";
                islater=true;
            }
        });

            dt_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (islater) {
                        final Calendar c1 = Calendar.getInstance();
                        mHour = c1.get(Calendar.HOUR_OF_DAY);
                        mMinute = c1.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String temp = String.valueOf(hourOfDay);
                                if (temp.length() < 2)
                                    temp = "0" + temp;
                                String temp1 = String.valueOf(minute);
                                if (temp1.length() < 2)
                                    temp1 = "0" + temp1;
                                t1=temp+":"+temp1;
                                hour = hourOfDay;
                                minutes = minute;
                                String timeSet = "";
                                if (hour > 12) {
                                    hour -= 12;
                                    timeSet = "PM";
                                } else if (hour == 0) {
                                    hour += 12;
                                    timeSet = "AM";
                                } else if (hour == 12)
                                    timeSet = "PM";
                                else
                                    timeSet = "AM";
                                String  hour1;
                                if (hour < 10)
                                    hour1 = "0" + hour;
                                else
                                    hour1 = String.valueOf(hour);
                                String min = "";
                                if (minutes < 10)
                                    min = "0" + minutes;
                                else
                                    min = String.valueOf(minutes);

                                // Append in a StringBuilder
//                                time1 = new StringBuilder().append(hour1).append(':')
//                                        .append(min).append(" ").append(timeSet).toString();
                                time1=hour1+":"+min+" "+timeSet;
                                time = time1;
//                        time1 = temp+":"+temp1;
                                time_tv.setText(time1);
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();


                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String temp = String.valueOf(monthOfYear + 1);
                                if (temp.length() < 2)
                                    temp = "0" + temp;
                                String temp1 = String.valueOf(dayOfMonth);
                                if (temp1.length() < 2)
                                    temp1 = "0" + temp1;
                                date = temp1 + "-" + temp + "-" + year;
                                date1 = temp1 + "-" + temp + "-" + year;
                                date_tv.setText(date);
//                        time_ll.performClick();
                            }
                        }, mYear, mMonth, mDay);
                        datePickerDialog.show();


                    }
                }
            });

//        time_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               );
//            }
//        });
        selected_area_id=Settings.getArea_id(getActivity());
        get_address_list();
        edit=(LinearLayout)view.findViewById(R.id.edit_address);
//        address_guest_ll=(LinearLayout)view.findViewById(R.id.address_guest_ll);
        drop_down=(ImageView)view.findViewById(R.id.drop_down);
        if(Settings.getUserid(getActivity()).equals("-1")){
            drop_down.setVisibility(View.GONE);
        }else{
            drop_down.setVisibility(View.VISIBLE);
        }
        drop_down_ll=(LinearLayout)view.findViewById(R.id.drop_down_ll);
        drop_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTextView title1 = new MyTextView(getActivity());
                title1.setText(Settings.getword(getActivity(), "select_address"));
                title1.setPadding(10, 10, 10, 10);
                title1.setGravity(Gravity.CENTER);
// title.setTextColor(getResources().getColor(R.color.greenBG));
                title1.setTextSize(18);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCustomTitle(title1);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.alert_list_item, address_title);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit.setVisibility(View.VISIBLE);
                        addrs_id = address_id.get(which);
                        selected_area_id = address_list.get(which).area_id;
//                        addr_name.setText(address_list.get(which).alias);
                        delivery_charges();
                        address_guest.setText(address_list.get(which).alias);
                        e_area.setText(address_list.get(which).getTitle(getActivity()));
                        e_block.setText(address_list.get(which).block);
                        e_street.setText(address_list.get(which).street);
                        e_building.setText(address_list.get(which).house);
                        e_floor.setText(address_list.get(which).floor);
                        e_aprtment.setText(address_list.get(which).flat);
                        e_mobile.setText(address_list.get(which).phone);
                        areaa.setText(address_list.get(which).getTitle(getActivity()));
                        block.setText(address_list.get(which).block);
                        street.setText(address_list.get(which).street);
                        building.setText(address_list.get(which).house);
                        floor.setText(address_list.get(which).floor);
                        apartment.setText(address_list.get(which).flat);
                        mobile.setText(address_list.get(which).phone);

                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        add=(LinearLayout)view.findViewById(R.id.add_address);
        cancel=(LinearLayout)view.findViewById(R.id.cancel_ll);
        save_ll=(LinearLayout)view.findViewById(R.id.save_ll);
        submit=(LinearLayout)view.findViewById(R.id.submit_final);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coupon=coupon_code.getText().toString();
                if (coupon.equals(""))
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "enter_valid_coupon_code"), false);
//                    Toast.makeText(getActivity(),"Please enter valid coupon code",Toast.LENGTH_SHORT).show();
                else
                set_coupon();
            }

            });
        ll_cash=(LinearLayout)view.findViewById(R.id.cash_final);
        ll_knet=(LinearLayout)view.findViewById(R.id.knet_final);
        ll_credit_card=(LinearLayout)view.findViewById(R.id.credit_card_final);
        if(cart_items.size()!=0){
            for(int i=0;i<cart_items.get(0).products.restaurant.payment.size();i++) {
                Log.e("sizeeeee",cart_items.get(0).products.restaurant.payment.get(i).id);
                if (cart_items.get(0).products.restaurant.payment.get(i).id.equals("1")){ ll_cash.setVisibility(View.VISIBLE);}
                else if (cart_items.get(0).products.restaurant.payment.get(i).id.equals("2")) {ll_knet.setVisibility(View.VISIBLE);}
                else if (cart_items.get(0).products.restaurant.payment.get(i).id.equals("6")) {ll_cash.setVisibility(View.VISIBLE);ll_knet.setVisibility(View.VISIBLE);}
                else {}
            }
        }
        cash_image=(ImageView)view.findViewById(R.id.cash_img);
        knet_image=(ImageView)view.findViewById(R.id.knet_img);
        credit_card_image=(ImageView)view.findViewById(R.id.credit_card_img);
        cash_img=(ImageView)view.findViewById(R.id.cash_img_dis);
        knet_img=(ImageView)view.findViewById(R.id.knet_img_dis);
//        Picasso.with(getActivity()).load(cart_items.get(0).products.restaurant.payment.get(1).image).into(knet_img);
        for(int i=0;i<cart_items.get(0).products.restaurant.payment.size();i++){
            if(i==0){
                if(cart_items.get(0).products.restaurant.payment.get(i).id.equals("1")){
                    Picasso.with(getActivity()).load(cart_items.get(0).products.restaurant.payment.get(i).image).into(cash_img);
                    ll_cash.setVisibility(View.VISIBLE);
                    ll_knet.setVisibility(View.GONE);
                }else{
                    Picasso.with(getActivity()).load(cart_items.get(0).products.restaurant.payment.get(i).image).into(knet_img);
                    ll_knet.setVisibility(View.VISIBLE);
                    ll_cash.setVisibility(View.GONE);
                }
            }else{
                Picasso.with(getActivity()).load(cart_items.get(0).products.restaurant.payment.get(i).image).into(knet_img);
                ll_knet.setVisibility(View.VISIBLE);
//                ll_cash.setVisibility(View.GONE);
            }
        }

        ll_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_met = "cash";
                cash_image.setImageResource(R.drawable.ic_option_pink);
                knet_image.setImageResource(R.drawable.ic_option_brown);
                credit_card_image.setImageResource(R.drawable.ic_option_brown);

            }
        });
        ll_knet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_met = "knet";
                cash_image.setImageResource(R.drawable.ic_option_brown);
                knet_image.setImageResource(R.drawable.ic_option_pink);
                credit_card_image.setImageResource(R.drawable.ic_option_brown);
            }
        });
        ll_credit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_met = "credit";
                cash_image.setImageResource(R.drawable.ic_option_brown);
                knet_image.setImageResource(R.drawable.ic_option_brown);
                credit_card_image.setImageResource(R.drawable.ic_option_pink);
            }
        });


        ll_proceed_pay=(LinearLayout)view.findViewById(R.id.proceed_pay);

        finalPageAdapter=new FinalPageAdapter(getActivity(),cart_items);
        ListView final_listview =(ListView)view.findViewById(R.id.final_listview);
        final_listview.setAdapter(finalPageAdapter);
        setListViewHeightBasedOnItems(final_listview);
        if(Settings.getUserid(getActivity()).equals("-1")){
            edit.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            save_ll.setVisibility(View.GONE);
//            address_guest_ll.setVisibility(View.GONE);
            address_guest.setText(Settings.getword(getActivity(),"address"));
            mobile_ll_final.setVisibility(View.GONE);

            block.setVisibility(View.GONE);
            street.setVisibility(View.GONE);
            building.setVisibility(View.GONE);
            floor.setVisibility(View.GONE);
            apartment.setVisibility(View.GONE);
            mobile.setVisibility(View.GONE);
            e_block.setVisibility(View.VISIBLE);
            e_street.setVisibility(View.VISIBLE);
            e_building.setVisibility(View.VISIBLE);
            e_floor.setVisibility(View.VISIBLE);
            e_aprtment.setVisibility(View.VISIBLE);
            e_mobile.setVisibility(View.VISIBLE);
//                  e_area.setText("");
            e_block.setText("");
            e_street.setText("");
            e_building.setText("");
            e_floor.setText("");
            e_aprtment.setText("");
            e_mobile.setText("");
//            addr_name.setText("");

        }else {
//            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
//            address_guest_ll.setVisibility(View.VISIBLE);
            address_guest.setText(Settings.getword(getActivity(), "address"));
            mobile_ll_final.setVisibility(View.VISIBLE);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                iseditfield=false;
//                isaddfield=false;
                block.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                building.setVisibility(View.VISIBLE);
                floor.setVisibility(View.VISIBLE);
                apartment.setVisibility(View.VISIBLE);
                mobile.setVisibility(View.VISIBLE);
                address_guest.setVisibility(View.VISIBLE);
//                address_guest.setText(Settings.getword(getActivity(), "address"));
//                addr_name_tv.setVisibility(View.VISIBLE);
                e_block.setVisibility(View.GONE);
                e_street.setVisibility(View.GONE);
                e_building.setVisibility(View.GONE);
                e_floor.setVisibility(View.GONE);
                e_aprtment.setVisibility(View.GONE);
                e_mobile.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                save_ll.setVisibility(View.GONE);

                add.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
               }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                save_ll.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);

                block.setVisibility(View.GONE);
                street.setVisibility(View.GONE);
                building.setVisibility(View.GONE);
                floor.setVisibility(View.GONE);
                apartment.setVisibility(View.GONE);
                mobile.setVisibility(View.GONE);
//                addr_name.setVisibility(View.GONE);
//                addr_name_tv.setText(Settings.getword(getActivity(), "address"));
//                addr_name_tv.setVisibility(View.VISIBLE);
                e_block.setVisibility(View.VISIBLE);
                e_street.setVisibility(View.VISIBLE);
                e_building.setVisibility(View.VISIBLE);
                e_floor.setVisibility(View.VISIBLE);
                e_aprtment.setVisibility(View.VISIBLE);
                e_mobile.setVisibility(View.VISIBLE);
            }
        });
        save_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if (addr_name.getText().toString().equals(""))
//                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_address_name"), false);
////                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_address_name"), Toast.LENGTH_SHORT).show();

                   if (e_block.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_block"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_block"), Toast.LENGTH_SHORT).show();
                    else if (e_street.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_street"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_street"), Toast.LENGTH_SHORT).show();
                    else if (e_building.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_building"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_building"), Toast.LENGTH_SHORT).show();
                   else if (e_mobile.getText().toString().equals(""))
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_mobile"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_mobile"), Toast.LENGTH_SHORT).show();
                    else {
                        add_address(addrs_id);
                    }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setDisplayedChild(1);
                String head1=Settings.getword(getActivity(), "add_address");
                mCallBack.text_back_butt_final_add(head1);
                add_area.setText(Settings.getArea_name(getActivity()));
            }
        });
        add_add_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaa.setText(Settings.getArea_name(getActivity()));
                block.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                building.setVisibility(View.VISIBLE);
                floor.setVisibility(View.VISIBLE);
                apartment.setVisibility(View.VISIBLE);
                mobile.setVisibility(View.VISIBLE);

                e_block.setVisibility(View.GONE);
                e_street.setVisibility(View.GONE);
                e_building.setVisibility(View.GONE);
                e_floor.setVisibility(View.GONE);
                e_aprtment.setVisibility(View.GONE);
                e_mobile.setVisibility(View.GONE);

                block.setText(add_et_block.getText().toString());
                street.setText(add_et_street.getText().toString());
                building.setText(add_et_buillding.getText().toString());
                floor.setText(add_et_floor.getText().toString());
                apartment.setText(add_et_flat.getText().toString());
                mobile.setText(add_et_mobile.getText().toString());

                e_block.setText(add_et_block.getText().toString());
                e_street.setText(add_et_street.getText().toString());
                e_building.setText(add_et_buillding.getText().toString());
                e_floor.setText(add_et_floor.getText().toString());
                e_aprtment.setText(add_et_flat.getText().toString());
                e_mobile.setText(add_et_mobile.getText().toString());
                address_guest.setText(add_et_addresss_name.getText().toString());


                if (add_et_addresss_name.getText().toString().equals(""))
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_address_name"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_address_name"), Toast.LENGTH_SHORT).show();
                else if (add_et_block.getText().toString().equals(""))
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_block"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_block"), Toast.LENGTH_SHORT).show();
                else if (add_et_street.getText().toString().equals(""))
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_street"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_street"), Toast.LENGTH_SHORT).show();
                else if (add_et_buillding.getText().toString().equals(""))
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_building"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_building"), Toast.LENGTH_SHORT).show();
                else if (add_et_mobile.getText().toString().equals(""))
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_mobile"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_mobile"), Toast.LENGTH_SHORT).show();
                else {
                    add_address("-1");

                }
            }
        });

        ll_proceed_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.getUserid(getActivity()).equals("-1")) {
                    if (et_fname.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_name"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_name"), Toast.LENGTH_SHORT).show();
                    else if (et_lname.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_lastname"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_lastname"), Toast.LENGTH_SHORT).show();
                    else if (et_home_ph.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_mobile"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_mobile"), Toast.LENGTH_SHORT).show();
                    else if (e_block.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_block"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_block"), Toast.LENGTH_SHORT).show();
                    else if (e_street.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_street"), false);
//                         Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_street"), Toast.LENGTH_SHORT).show();
                    else if (e_building.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_building"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_building"), Toast.LENGTH_SHORT).show();
//                    else if (e_mobile.getText().toString().equals(""))
//                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(),"please_enter_mobile"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_mobile"), Toast.LENGTH_SHORT).show();
                    else if (time1.equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_delivery_time"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_delivery_time"), Toast.LENGTH_SHORT).show();
                    else if (date1.equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_delivery_date"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_delivery_date"), Toast.LENGTH_SHORT).show();
                    else if (pay_met.equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_payment"), false);
                    else {
                        if (delivery.equals("1"))
//                            reward_pop.setVisibility(View.VISIBLE);
                            place_set_data();
                        else
                            check_date_time();

                    }
                } else {
                    if (e_block.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_block"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_block"), Toast.LENGTH_SHORT).show();
                    else if (e_street.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_street"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_street"), Toast.LENGTH_SHORT).show();
                    else if (e_building.getText().toString().equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_building"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_building"), Toast.LENGTH_SHORT).show();
//                    else if (e_mobile.getText().toString().equals(""))
//                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(),"please_enter_mobile"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"please_enter_mobile"), Toast.LENGTH_SHORT).show();
                    else if (time1.equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_delivery_time"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_delivery_time"), Toast.LENGTH_SHORT).show();
                    else if (date1.equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_delivery_date"), false);
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_delivery_date"), Toast.LENGTH_SHORT).show();
                    else if (pay_met.equals(""))
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_payment"), false);
                    else {
                        if (delivery.equals("1")) {
                            if (reward_price.equals("0")) {
                                reward_pop.setVisibility(View.GONE);
                                place_set_data();
                            }else{
                                reward_pop.setVisibility(View.VISIBLE);
                            }
                        }else
                            check_date_time();
                    }
                }
            }
        });
        use_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(g_total<Float.parseFloat(reward_price)){

                    pay_met = "cash";
                    Float temp=Float.parseFloat(reward_price)-g_total;
                    rew_price=String.format("%.3f",g_total);
                 //   alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "thank_you_reward")+" : "+temp, false);
                    g_total=0f;
                    place_set_data();

                    reward_pop.setVisibility(View.GONE);

                }else if(g_total==Float.parseFloat(reward_price)) {
                    g_total = g_total - Float.parseFloat(reward_price);
                    pay_met = "cash";
                    rew_price=reward_price;
                  //  alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "thank_you_reward")+" : "+"0", false);
                    place_set_data();
                    reward_pop.setVisibility(View.GONE);
                }else{
                    g_total = g_total - Float.parseFloat(reward_price);
                    rew_price=reward_price;
                  //  alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "thank_you_reward")+" : "+"0", false);
                    place_set_data();
                    reward_pop.setVisibility(View.GONE);
                }
            }
        });
        dnt_use_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reward_price="0";
                reward_pop.setVisibility(View.GONE);
                place_set_data();
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.setDisplayedChild(0);
                        String head2=Settings.getword(getActivity(), "final");
                        mCallBack.text_back_butt(head2);
                        return true;
                    }
                    loaded = true;
                    return false;
                }
                return false;
            }
        });
    }
    public  void check_date_time(){
        String url = null;
        Log.e("date",date1);
        Log.e("time",t1);
        try {
            url = Settings.SERVERURL+"delivery-date-check.php?rest_id="+ URLEncoder.encode(cart_items.get(0).products.restaurant.res_id, "utf-8")+
                    "&date="+URLEncoder.encode(date1, "utf-8")+"&time="+URLEncoder.encode(t1, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());

                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Success")) {
                        String msg = jsonObject.getString("message"+Settings.get_lan(getActivity()));
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//                        place_set_data();
                        if(reward_price.equals("0")) {
                            reward_pop.setVisibility(View.GONE);
                            place_set_data();
                        }else{
                            reward_pop.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        alert.showAlertDialog(getActivity(), "Info",msg, false);
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


    }
    String reward_points;
    public  void get_reward(){
        String url = null;
            url = Settings.SERVERURL+"rewards.php?member_id="+Settings.getUserid(getActivity());
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());

                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Success")) {
//                        String msg = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        reward_price=jsonObject.getString("reward_price");
                        reward_points=jsonObject.getString("reward_points");
                        reward_tv.setText(String.format("%.3f",Float.parseFloat(reward_price)) + " KD");
//                        place_set_data();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        alert.showAlertDialog(getActivity(), "Info",msg, false);
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


    }
    public void delivery_charges(){
        String url;
        if(Settings.getArea_id(getActivity()).equals("-1")) {
        }else {

            url = Settings.SERVERURL + "charges.php?" + "rest_id=" +cart_items.get(0).products.restaurant.res_id+ "&area=" + selected_area_id;
            Log.e("url--->", url);
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
            progressDialog.setCancelable(false);
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Log.e("orders response is: ", jsonObject.toString());
                    try {
                        String deliv_charges = jsonObject.getString("price");
//                        com_delivery_charges.setText(deliv_charges + " KD ");
                        delivery_charges.setText(deliv_charges + " KD");
                        del_total=Float.parseFloat(deliv_charges);
                        grn_total= total+del_total+dara_charges;
                        g_total=grn_total;
                        grand_total.setText(String.format("%.3f", g_total) + " KD");
                        Settings.setDelivery_charges(getActivity(), deliv_charges);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Log.e("response is:", error.toString());
                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "server_not_connected"), Toast.LENGTH_SHORT).show();
                    if (progressDialog != null)
                        progressDialog.dismiss();
                }

            });

// Access the RequestQueue through your singleton class.
            AppController.getInstance().addToRequestQueue(jsObjRequest);
        }
    }
        public  void place_set_data(){
                JSONObject address_object = new JSONObject();
                     JSONArray products_array = new JSONArray();
                    try {
                        if (Settings.getUserid(getActivity()).equals("-1")) {
                            address_object.put("first_name", et_fname.getText().toString());
                            address_object.put("last_name", et_lname.getText().toString());
                            Log.e("email",et_email.getText().toString());
                            address_object.put("email", et_email.getText().toString());
                            address_object.put("home_phone", et_work_ph.getText().toString());
                            address_object.put("mobile", et_home_ph.getText().toString());
                        }
                        address_object.put("area", selected_area_id);
                        address_object.put("block", e_block.getText().toString());
                        address_object.put("street", e_street.getText().toString());
                        address_object.put("building", e_building.getText().toString());
                        address_object.put("floor", e_floor.getText().toString());
                        address_object.put("flat", e_aprtment.getText().toString());
                        if (!Settings.getUserid(getActivity()).equals("-1")) {
                            address_object.put("mobile", e_mobile.getText().toString());
                        }
                        place_order_object.put("address", address_object);

                        place_order_object.put("comments", spl_comment.getText().toString());
                        place_order_object.put("coupon_code", coupon_code.getText().toString());
                        place_order_object.put("discount_amount", String.format("%.3f", dis_amount));
                        Log.e("dam", String.format("%.3f", dis_amount));
                        place_order_object.put("darabeel_charges", Settings.getSettings(getActivity(), "darabeel_charges"));
                        place_order_object.put("price", String.format("%.3f", total));
                        Log.e("sub", String.format("%.3f", total));
                        place_order_object.put("delivery_charges", Settings.getDelivery_charges(getActivity()));
                        Log.e("darac", Settings.getDelivery_charges(getActivity()));
                        place_order_object.put("total_price", String.format("%.3f", g_total));
                        Log.e("tp", String.format("%.3f", g_total));
                        place_order_object.put("payment_method", pay_met);

                        if (!Settings.getUserid(getActivity()).equals("-1")) {
                            place_order_object.put("reward_amount", String.format("%.3f", Float.parseFloat(rew_price)));
                        }
                        place_order_object.put("delivery", delivery);
                        place_order_object.put("delivery_date", date);
                        place_order_object.put("delivery_time", time);
                        place_order_object.put("member_id", Settings.getUserid(getActivity()));
                        place_order_object.put("restaurant_id", rest_id);
                        Log.e("cart", String.valueOf(cart_items.size()));
                        for (int i = 0; i < cart_items.size(); i++) {
                            JSONObject product = new JSONObject();
                            product.put("product_id", cart_items.get(i).products.res_id);
                            product.put("quantity", cart_items.get(i).quantity);
                            product.put("price", String.format("%.3f",Float.parseFloat(cart_items.get(i).quantity)*Float.parseFloat(cart_items.get(i).products.cart_price)));
                         Log.e("pr",String.format("%.3f",Float.parseFloat(cart_items.get(i).quantity)*Float.parseFloat(cart_items.get(i).products.cart_price)));
                            product.put("special_request", cart_items.get(i).spl_request);
                            JSONArray addon_array = new JSONArray();
                            for (int j = 0; j < cart_items.get(i).products.groups.size(); j++) {

                                for (int k = 0; k < cart_items.get(i).products.groups.get(j).addons.size(); k++) {
                                    JSONObject addon = new JSONObject();
                                    if (cart_items.get(i).products.groups.get(j).addons.get(k).isselected){
                                        addon.put("addon_id", cart_items.get(i).products.groups.get(j).addons.get(k).addon_id);
                                        Log.e("addonId", cart_items.get(i).products.groups.get(j).addons.get(k).addon_id);
                                        addon.put("price", String.format("%.3f",Float.parseFloat(cart_items.get(i).products.groups.get(j).addons.get(k).price)));
                                        Log.e("addonPrice", cart_items.get(i).products.groups.get(j).addons.get(k).price);
                                        addon_array.put(addon);
                                }
                                }
                            }
                            product.put("addons", addon_array);
                            Log.e("addons",addon_array.toString());
                            for (int l = 0; l < cart_items.get(i).products.options.size(); l++) {
                                JSONObject options = new JSONObject();
                                if (cart_items.get(i).products.options.get(l).isselected){
                                    options.put("options_id", cart_items.get(i).products.options.get(l).option_id);
                                    Log.e("optionId", cart_items.get(i).products.options.get(l).option_id);
                                    options.put("price", String.format("%.3f",cart_items.get(i).products.options.get(l).price));
                                    Log.e("optionPrice", cart_items.get(i).products.options.get(l).price);
                                    product.put("options", options);
                            }
                            }
                            products_array.put(product);
                        }
                        place_order_object.put("products", products_array);

                        if (pay_met.equals("cash")) {
                            place_order();
                            Log.e("order", place_order_object.toString());
                        } else {
                            Intent payment = new Intent(getActivity(), PaymentActivity.class);
                            payment.putExtra("cust_id", Settings.getUserid(getActivity()));
                            payment.putExtra("pack_price", String.valueOf(g_total));
                            startActivityForResult(payment, 7);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
        }
    public  void set_coupon(){
        String url = Settings.SERVERURL+"coupon-check.php?";
        try {
            url = url + "rest_id="+ URLEncoder.encode(cart_items.get(0).products.restaurant.res_id, "utf-8")+
                    "&cart_total="+URLEncoder.encode(String.valueOf(total),"utf-8")+
                    "&coupon="+URLEncoder.encode(coupon,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());

                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String dis_type=jsonObject.getString("discount_type");
                        String dis_value=jsonObject.getString("discount_value");
                        if(dis_type.equals("amount")) {
                            dis_amount=Float.parseFloat(dis_value);
                            grn_total=total-dis_amount;
                            g_total = grn_total+del_total+dara_charges;
                            grand_total.setText(String.format("%.3f", g_total) + " KD");
                            discount.setText(dis_value+" KD");
                            delivery_charges();
                        }else{
                            dis_amount=total/Float.parseFloat(dis_value);
                            grn_total=total-dis_amount;
                            g_total=grn_total+del_total+dara_charges;
                            grand_total.setText(String.format("%.3f",g_total) + " KD");
                            discount.setText(dis_value+" KD");
                            delivery_charges();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


    }

    public  void place_order(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"place-order.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("res",response);
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.has("id")){
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                JsonOrders jsonOrders=new JsonOrders(jsonObject);
//                                invoices.add(invoice);
                                mCallBack.gotoinvoicepage(jsonOrders);
                            }
                            else{
                                String reply=jsonObject.getString("status");
                                if(reply.equals("Failed")) {
                                    String msg = jsonObject.getString("message");
                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("member_id", Settings.getUserid(getActivity()));

                params.put("order", place_order_object.toString());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 7) {
            if(data.getStringExtra("msg").equals("OK"))
            {
                Toast.makeText(getActivity(),"Payment success",Toast.LENGTH_SHORT).show();
                place_order();
            }
            else
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"pay_failed"),Toast.LENGTH_SHORT).show();
        }
    }
    private void get_address_list(){
        String url=Settings.SERVERURL+"address-list.php?"+"member_id="+Settings.getUserid(getActivity())+"&rest_id="+rest_id;
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                if(progressDialog!=null)
                progressDialog.dismiss();
                Log.e("response is: ", jsonArray.toString());
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String ar_id = sub.getString("id");
                        String ar_name = sub.getString("alias");
                        String add_name = sub.getString("name");
                        String phone = sub.getString("phone");
                        JSONObject json_area=sub.getJSONObject("area");
                             String addr_area_id = json_area.getString("id");
                             String addr_area_title = json_area.getString("title");
                             String addr_area_title_ar = json_area.getString("title_ar");
                        String block = sub.getString("block");
                        String street = sub.getString("street");
                        String building = sub.getString("building");
                        String floor = sub.getString("floor");
                        String app_flat = sub.getString("app_flat");
                        Addresss address=new Addresss(ar_id,ar_name,add_name,phone,addr_area_id,addr_area_title,addr_area_title_ar,block,
                                street,building,floor,app_flat);
                        address_list.add(address);
                        address_id.add(ar_id);
                        address_title.add(ar_name);
                    }
                    if(!Settings.getUserid(getActivity()).equals("-1")) {
                        if (jsonArray.length() == 0) {
                            Toast.makeText(getActivity(), Settings.getword(getActivity(), "add_address"), Toast.LENGTH_SHORT).show();
                            add.performClick();
                            cancel.setVisibility(View.GONE);
                        } else {
//                            drop_down.performClick();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    private void add_address(final String id){
    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
    progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
    progressDialog.show();
    progressDialog.setCancelable(false);
    String url = Settings.SERVERURL+"add-address.php";
    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if(progressDialog!=null)
                progressDialog.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(response);
                String reply=jsonObject.getString("status");
                if(reply.equals("Success")) {
                    String msg = jsonObject.getString("message");
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    if(id.equals("-1")) {
                        viewFlipper.setDisplayedChild(0);
                        String head2=Settings.getword(getActivity(), "final");
                        mCallBack.text_back_butt(head2);
                        selected_area_id=Settings.getArea_id(getActivity());

                    }else{
                        add.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.GONE);
                        save_ll.setVisibility(View.GONE);
                    }
                }
                else {
                    String msg=jsonObject.getString("message");
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
        @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();
            if(id.equals("-1"))
            params.put("alias",add_et_addresss_name.getText().toString());
            else
            params.put("alias",address_guest.getText().toString());
            if(!id.equals("-1"))
                params.put("area",selected_area_id);
            else
                params.put("area",Settings.getArea_id(getActivity()));
            params.put("block",e_block.getText().toString());
            params.put("street",e_street.getText().toString());
            params.put("building",e_building.getText().toString());
            params.put("floor",e_floor.getText().toString());
            params.put("app_flat",e_aprtment.getText().toString());
            params.put("phone",e_mobile.getText().toString());
            params.put("member_id",Settings.getUserid(getActivity()));
            if(!id.equals("-1"))
                params.put("address_id",id);
            return params;
        }
    };
    AppController.getInstance().addToRequestQueue(stringRequest);
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
}