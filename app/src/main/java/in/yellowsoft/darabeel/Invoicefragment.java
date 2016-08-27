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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Invoicefragment extends Fragment {
    TextView sta_ord_id, sta_ord_date, Sta_ord_name, sta_ord_mobile, sta_ord_area, sta_ord_block, sta_ord_street, sta_ord_bilding,sta_delivery_date,
            sta_discount_oreder, discount_order, sta_or_det_floor, sta_or_det_flat, or_det_floor,or_det_flat,sta_sub_total, sta_dc, sta_grand_total, ord_date, ord_id, ord_name,
            ord_mobile, ord_area, ord_block, ord_street, ord_building,ord_sub_total, ord_dc, ord_grand_total,tq,delivery_date,sta_dara,dara,home_tv;
    ListView ord_detail_list;
    String head;
    LinearLayout home_ll;
    JsonOrders orderses;
    boolean loaded=false;
    InvoiceDetailsAdapter invoiceDetailsAdapter;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void clear_cart();
        public  Animation get_animation(Boolean enter,Boolean loaded);
        public void to_home();
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
        return inflater.inflate(R.layout.invoice_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
//        loaded=true;
//        String head=Settings.getword(getActivity(), "");
//        mCallBack.text_back_butt(head);
        orderses=(JsonOrders)getArguments().getSerializable("invoice");
        tq = (TextView) view.findViewById(R.id.tv_tq);
        tq.setText(Settings.getword(getActivity(), "order_confirmation_message"));
        sta_ord_id = (TextView) view.findViewById(R.id.sta_tq_order_id);
        sta_ord_id.setText(Settings.getword(getActivity(), "order_id"));
        sta_ord_date = (TextView) view.findViewById(R.id.sta_tq_order_date);
        sta_ord_date.setText(Settings.getword(getActivity(), "order_date"));
        sta_delivery_date = (TextView) view.findViewById(R.id.sta_tq_delivery_date);
        sta_delivery_date.setText(Settings.getword(getActivity(), "delivery_date"));
        Sta_ord_name = (TextView) view.findViewById(R.id.sta_tq_order_name);
        Sta_ord_name.setText(Settings.getword(getActivity(), "contact_us_name"));
        sta_ord_mobile = (TextView) view.findViewById(R.id.sta_tq_order_mobile);
        sta_ord_mobile.setText(Settings.getword(getActivity(), "mobile"));
        sta_ord_area = (TextView) view.findViewById(R.id.sta_tq_order_area);
        sta_ord_area.setText(Settings.getword(getActivity(), "area"));
        sta_ord_block = (TextView) view.findViewById(R.id.sta_tq_order_block);
        sta_ord_block.setText(Settings.getword(getActivity(), "block"));
        sta_ord_street = (TextView) view.findViewById(R.id.sta_tq_order_street);
        sta_ord_street.setText(Settings.getword(getActivity(), "street_name"));
        sta_ord_bilding = (TextView) view.findViewById(R.id.sta_tq_order_building);
        sta_ord_bilding.setText(Settings.getword(getActivity(), "building_name"));
        sta_or_det_floor = (TextView) view.findViewById(R.id.sta_tq_order_floor);
        sta_or_det_floor.setText(Settings.getword(getActivity(), "floor_number"));
        sta_or_det_flat = (TextView) view.findViewById(R.id.sta_tq_order_flat);
        sta_or_det_flat.setText(Settings.getword(getActivity(), "flat_number"));
        sta_sub_total = (TextView) view.findViewById(R.id.sta_tq_sub_total);
        sta_sub_total.setText(Settings.getword(getActivity(), "sub_total"));
        sta_dc = (TextView) view.findViewById(R.id.sta_tq_dc);
        sta_dc.setText(Settings.getword(getActivity(), "delivery_charges"));
        sta_grand_total = (TextView) view.findViewById(R.id.sta_tq_grand_total);
        sta_grand_total.setText(Settings.getword(getActivity(), "grand_total"));
        sta_discount_oreder = (TextView) view.findViewById(R.id.sta_tq_discount);
        sta_discount_oreder.setText(Settings.getword(getActivity(), "discount"));
        sta_dara = (TextView) view.findViewById(R.id.sta_dara_invoice);
        sta_dara.setText(Settings.getword(getActivity(), "darabeel_charges"));
        home_tv = (TextView) view.findViewById(R.id.home_tv);
        home_tv.setText(Settings.getword(getActivity(), "home"));
        home_ll = (LinearLayout) view.findViewById(R.id.home_iv_ll);

        ord_date = (TextView) view.findViewById(R.id.tq_order_date);
        delivery_date = (TextView) view.findViewById(R.id.delivery_date);
        ord_id = (TextView) view.findViewById(R.id.tq_order_id);
        ord_name = (TextView) view.findViewById(R.id.tq_order_name);
        ord_mobile = (TextView) view.findViewById(R.id.tq_order_mobile);
        ord_area = (TextView) view.findViewById(R.id.tq_order_area);
        ord_block = (TextView) view.findViewById(R.id.tq_order_block);
        ord_street = (TextView) view.findViewById(R.id.tq_order_street);
        ord_building = (TextView) view.findViewById(R.id.tq_order_building);
        or_det_floor = (TextView) view.findViewById(R.id.tq_order_floor);
        or_det_flat = (TextView) view.findViewById(R.id.tq_order_flat);
        ord_sub_total = (TextView) view.findViewById(R.id.tq_sub_total);
        ord_dc = (TextView) view.findViewById(R.id.tq_dc);
        ord_grand_total = (TextView) view.findViewById(R.id.tq_grand_total);
        discount_order = (TextView) view.findViewById(R.id.tq_discount);
        dara = (TextView) view.findViewById(R.id.dara_invoice);

        ord_date.setText(orderses.date);
        String temp=orderses.deli_date+" "+orderses.deli_time;
        delivery_date.setText(temp);
        ord_id.setText(orderses.id);
        ord_name.setText(orderses.fname + " " + orderses.lname);
        ord_mobile.setText(orderses.phone);
        ord_area.setText(orderses.get_area(getActivity()));
        ord_block.setText(orderses.block);
        ord_street.setText(orderses.street);
        ord_building.setText(orderses.building);
        or_det_floor.setText(orderses.flor);
        or_det_flat.setText(orderses.flat);
        ord_sub_total.setText(orderses.pricee+" KD");
        ord_dc.setText(orderses.del_charges+" KD");
        ord_grand_total.setText(orderses.tot_price+" KD");
        discount_order.setText(orderses.dis_amount+" KD");
        dara.setText(orderses.dara + " KD");
        Log.e("size", String.valueOf(orderses.product.size()));

        invoiceDetailsAdapter = new InvoiceDetailsAdapter(getActivity(), orderses);
        ord_detail_list = (ListView) view.findViewById(R.id.tq_list);
        ord_detail_list.setAdapter(invoiceDetailsAdapter);
        setListViewHeightBasedOnItems(ord_detail_list);
        home_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.to_home();
            }
        });
        mCallBack.clear_cart();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    mCallBack.to_home();
                    loaded=true;
                    return true;
                }
                return false;
            }
        });
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
