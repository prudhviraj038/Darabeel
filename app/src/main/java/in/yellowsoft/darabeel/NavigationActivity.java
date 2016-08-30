package in.yellowsoft.darabeel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.ArrayList;


public class NavigationActivity extends FragmentActivity implements HomeFragment.FragmentTouchListner ,
        LoginFragment.FragmentTouchListner, CompanyListFragment.FragmentTouchListner, CompanyPageFragment.FragmentTouchListner,
         MenuCategoryfragment.FragmentTouchListner, ProductListFragment.FragmentTouchListner,
        ProductPageFragment.FragmentTouchListner, CartFragment.FragmentTouchListner, FinalFragment.FragmentTouchListner,
        NotificationFragment.FragmentTouchListner, AboutUsfragment.FragmentTouchListner, ContactUsFragment.FragmentTouchListner,
        TermsConditionsFragment.FragmentTouchListner, MyAccountFragment.FragmentTouchListner, Invoicefragment.FragmentTouchListner,
        OffersListFragment.FragmentTouchListner, PromotionsListFragment.FragmentTouchListner, SearchFragment.FragmentTouchListner,
        VerificationFragment.FragmentTouchListner, MyOrdersfragment.FragmentTouchListner, MyAddressfragment.FragmentTouchListner,
        ChangePasswordfragment.FragmentTouchListner, EditProfilefragment.FragmentTouchListner, Settingsfragment.FragmentTouchListner,
        AreaFragment.FragmentTouchListner, Reviewsfragment.FragmentTouchListner {
    ArrayList<Integer> prgmImages = new ArrayList<>();
    ArrayList<String> prgmTitles = new ArrayList<>();
    ArrayList<Integer> subprgmImages = new ArrayList<>();
    ArrayList<String> subprgmTitles = new ArrayList<>();
    DrawerLayout mDrawerLayout;
    private ListView mDrawerList1;
    private ListView mDrawerList2;
    FrameLayout container;
    int current_position=0;
    CompanyListFragment companyListFragment;
    FragmentManager fragmentManager;
    ProductPageFragment productPageFragment;
    ImageView menu,back,search,menu_back,cart_icon;
    TextView logout,nav_title,lang_tv,waste;
    LinearLayout lang_ll,filter;
    ArrayList<CartItem> cart_items=new ArrayList<>();
    public  static final long DURATION=300;
    boolean animation_direction=true;
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.navigation_screen);
        ArrayList<Integer> prgmImages = new ArrayList<>();
        ArrayList<String> prgmTitles = new ArrayList<>();
        ArrayList<Integer> subprgmImages = new ArrayList<>();
        ArrayList<String> subprgmTitles = new ArrayList<>();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
//        ArrayList<CartItem> cart_items=new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        HomeFragment fragment = new HomeFragment();
        fragmentManager.beginTransaction().add(R.id.container_main, fragment).commit();
        nav_title=(TextView)findViewById(R.id.nav_title);
        waste=(TextView)findViewById(R.id.nav_waste);
        lang_tv=(TextView)findViewById(R.id.lang_nav_tv);
        if(Settings.get_user_language(this).equals("ar")){
            lang_tv.setText("EN");
        }else{
            lang_tv.setText("AR");
        }
        logout=(TextView)findViewById(R.id.logout);
        logout.setText(Settings.getword(this, "logout"));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setUserid(NavigationActivity.this, "-1", "name");
                FragmentManager fragmentManager = getSupportFragmentManager();
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, homeFragment).addToBackStack(null).commit();
            }
        });
        menu=(ImageView)findViewById(R.id.menu_buttion);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menu_back=(ImageView)findViewById(R.id.menu_back);
        cart_icon=(ImageView)findViewById(R.id.cart_icon);
        menu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                NavigationActivity.this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            }
        });
        lang_ll=(LinearLayout)findViewById(R.id.lang_nav_ll);
        lang_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NavigationActivity.this,LanguageActvity.class);
                startActivity(intent);
            }
        });
        filter=(LinearLayout)findViewById(R.id.nav_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        search=(ImageView)findViewById(R.id.search_icon);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment fragment4 = new SearchFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).addToBackStack(null).commit();
            }
        });
        prgmImages.add(R.drawable.home_icon);
        prgmImages.add(R.drawable.cart_icon_white);
        prgmImages.add(R.drawable.my_account_icon);
        prgmImages.add(R.drawable.my_account_icon);
        prgmImages.add(R.drawable.home_icon);
        prgmImages.add(R.drawable.cart_icon);
        prgmImages.add(R.drawable.my_account_icon);
        prgmImages.add(R.drawable.notification_icon);
        prgmImages.add(R.drawable.home_icon);
        prgmImages.add(R.drawable.cart_icon);
        prgmImages.add(R.drawable.my_account_icon);
        prgmImages.add(R.drawable.notification_icon);
        prgmImages.add(R.drawable.home_icon);
        prgmImages.add(R.drawable.cart_icon);
        prgmImages.add(R.drawable.my_account_icon);
        prgmImages.add(R.drawable.notification_icon);
        prgmImages.add(R.drawable.notification_icon);
        prgmTitles.add(Settings.getword(this,"home"));
        prgmTitles.add(Settings.getword(this,"cart")+"("+cart_items.size()+")");
        prgmTitles.add(Settings.getword(this, "live_support"));
        prgmTitles.add(Settings.getword(this,"my_account"));
        prgmTitles.add(Settings.getword(this, "my_orders"));
        prgmTitles.add(Settings.getword(this, "rate_my_order"));
        prgmTitles.add(Settings.getword(this, "most_selling"));
        prgmTitles.add(Settings.getword(this, "new_arrivals"));
        prgmTitles.add(Settings.getword(this, "promotions"));
        prgmTitles.add(Settings.getword(this,"offers"));
        prgmTitles.add(Settings.getword(this,"title_trending"));
        prgmTitles.add(Settings.getword(this,"twitter"));
        prgmTitles.add(Settings.getword(this,"instagram"));
        prgmTitles.add(Settings.getword(this,"about_us"));
        prgmTitles.add(Settings.getword(this,"menu_terms_and_conditions"));
        prgmTitles.add(Settings.getword(this,"menu_share"));
        prgmTitles.add(Settings.getword(this,"contact_us"));
        subprgmImages.add(R.drawable.logo);
        subprgmImages.add(R.drawable.what_we_do_icon);
        subprgmImages.add(R.drawable.contact_us_icon);
        subprgmImages.add(R.drawable.book);
        mDrawerList1 = (ListView) findViewById(R.id.mdrawerlist1);
        mDrawerList2 = (ListView) findViewById(R.id.mdrawerlist2);
        mDrawerList1.setAdapter(new NavigationListAdapter(this,prgmTitles,prgmImages));
//        mDrawerList2.setAdapter(new NavigationListAdapter(this, subprgmImages,subprgmTitles));
        container = (FrameLayout) findViewById(R.id.container_main);
        back.setVisibility(View.VISIBLE);
        mDrawerList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
//                if(current_position!=position)
                switch (position) {
                    case 0:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        animation_direction=true;
                        HomeFragment fragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.container_main, fragment).addToBackStack(null).commit();
                        break;
                    case 1:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        if(cart_items.size()==0){
                            alert.showAlertDialog(NavigationActivity.this, "Info", Settings.getword(getApplicationContext(),"no_items_in_cart"), false);
//                                Toast.makeText(NavigationActivity.this,Settings.getword(getApplicationContext(),"no_items_in_cart") , Toast.LENGTH_SHORT).show();
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("cart_items", cart_items);
                            animation_direction=true;
                            FragmentManager fragmentManager1 = getSupportFragmentManager();
                            CartFragment cartFragment = new CartFragment();
                            cartFragment.setArguments(bundle);
                            fragmentManager1.beginTransaction().replace(R.id.container_main, cartFragment).addToBackStack(null).commit();
                        }
                        break;

                    case 2:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        if(Settings.getUserid(getApplicationContext()).equals("-1")){
                            animation_direction=true;
                            LoginFragment loginFragment=new LoginFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, loginFragment).addToBackStack(null).commit();
                        }else {
                            animation_direction=true;
                            MyAccountFragment fragment4 = new MyAccountFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).addToBackStack(null).commit();
                        }
                        break;

                    case 4:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        if(Settings.getUserid(getApplicationContext()).equals("-1")){
                            animation_direction=true;
                            LoginFragment loginFragment=new LoginFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, loginFragment).addToBackStack(null).commit();
                        }else {
                            animation_direction=true;
                            MyAccountFragment fragment4 = new MyAccountFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).addToBackStack(null).commit();
                        }
                        break;
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        if(Settings.getUserid(getApplicationContext()).equals("-1")){
//                            animation_direction=true;
//                            LoginFragment loginFragment=new LoginFragment();
//                            fragmentManager.beginTransaction().replace(R.id.container_main, loginFragment).addToBackStack(null).commit();
//                        }else {
//                            animation_direction=true;
//                            NotificationFragment fragment5 = new NotificationFragment();
//                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment5).addToBackStack(null).commit();
//                        }
//                        break;
                    case 5:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        if(Settings.getUserid(getApplicationContext()).equals("-1")){
                            animation_direction=true;
                            LoginFragment loginFragment=new LoginFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, loginFragment).addToBackStack(null).commit();
                        }else {
                            animation_direction=true;
                            MyAccountFragment fragment4 = new MyAccountFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).addToBackStack(null).commit();
                        }
                        break;
                    case 6:
                        animation_direction=true;
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        Settings.setArea_id(NavigationActivity.this, "-1", "", "");
                        companyffragment("most_selling");
                        break;
                    case 7:
                        animation_direction=true;
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        Settings.setArea_id(NavigationActivity.this, "-1", "", "");
                        companyffragment("new");
                        break;
                    case 8:
                        animation_direction=true;
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        Settings.setArea_id(NavigationActivity.this, "-1", "", "");
                        PromotionsListFragment fragment1 = new PromotionsListFragment();
                        fragmentManager.beginTransaction().replace(R.id.container_main, fragment1).addToBackStack(null).commit();
                        break;
                    case 9:
                        animation_direction=true;
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        Settings.setArea_id(NavigationActivity.this, "-1", "", "");
                        companyffragment("offers");
                        break;
                    case 10:
                        animation_direction=true;
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        Settings.setArea_id(NavigationActivity.this, "-1", "", "");
                        companyffragment("trending");
                        break;
                    case 11:
                        animation_direction=true;
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        Picasso.with(getActivity()).load(news.image).into(new Target() {
//                            @Override
//                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                                Intent i = new Intent(Intent.ACTION_SEND);
//                                i.setType("text/plain");
//                                //     i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
//                                //     i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
//                                i.putExtra(Intent.EXTRA_TEXT, html2text(news.link));
//                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                i.setPackage("com.twitter.android");
//                                try {
//                                    startActivity(i);
//                                }catch (Exception e){
//
//                                    try {
//                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.twitter.android")));
//                                    } catch (android.content.ActivityNotFoundException anfe) {
//                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.twitter.android")));
//                                    }
//                                }
//
//
//
//                            }
//
//                            @Override
//                            public void onBitmapFailed(Drawable errorDrawable) {
//                            }
//
//                            @Override
//                            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                            }
//                        });
                        break;
                    case 12:
                        animation_direction=true;
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        Picasso.with(NavigationActivity.this).load(news.image).into(new Target() {
//                            @Override
//                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                                Intent i = new Intent(Intent.ACTION_SEND);
//                                i.setType("image/*");
//                                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
//                                i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
//                                i.putExtra(Intent.EXTRA_TEXT, html2text(news.link));
//                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                i.setPackage("com.instagram.android");
//                                try {
//                                    startActivity(i);
//                                }catch (Exception e){
//
//                                    try {
//                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.instagram.android")));
//                                    } catch (android.content.ActivityNotFoundException anfe) {
//                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
//                                    }
//                                }
//
//
//
//                            }
//
//                            @Override
//                            public void onBitmapFailed(Drawable errorDrawable) {
//                            }
//
//                            @Override
//                            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                            }
//                        });
                        break;
                    case 13:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        animation_direction=true;
                        AboutUsfragment fragment2 = new AboutUsfragment();
                        fragmentManager.beginTransaction().replace(R.id.container_main, fragment2).addToBackStack(null).commit();
                        break;
                    case 14:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        animation_direction=true;
                        TermsConditionsFragment fragment3 = new TermsConditionsFragment();
                        fragmentManager.beginTransaction().replace(R.id.container_main, fragment3).addToBackStack(null).commit();
                        break;
                    case 15:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        shareTextUrl();
                        break;
                    case 16:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        animation_direction=true;
                        ContactUsFragment fragment4 = new ContactUsFragment();
                        fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).addToBackStack(null).commit();
                        break;
                }
//                current_position=position;
            }
        });
    }
    @Override
    public void onBackPressed() {
        animation_direction=false;
        super.onBackPressed();
    }
    @Override
    public   Animation get_animation(Boolean enter,Boolean loaded){
//            return MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION);
        if(Settings.get_user_language(this).equals("ar")){
            if(animation_direction)
                return MoveAnimation.create(MoveAnimation.LEFT, enter, DURATION);
            else
                return MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION);
        }else{
            if(animation_direction)
                return MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION);
            else
                return MoveAnimation.create(MoveAnimation.LEFT, enter, DURATION);
        }
    }
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_TEXT, Settings.getSettings(this, "playstore_link"));
        startActivity(Intent.createChooser(share, "Share link!"));
    }

    @Override
    public void after_login() {
        onBackPressed();
    }
    @Override
    public void language_set(String head) {
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void product_selected(Products products) {
        animation_direction=true;
        productPageFragment=new ProductPageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product",products);
        productPageFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, productPageFragment).addToBackStack(null).commit();
    }

    @Override
    public void cart_icon_dis(String head) {
        nav_title.setVisibility(View.VISIBLE);
        nav_title.setText(head);
        lang_ll.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        waste.setVisibility(View.GONE);
        menu_back.setVisibility(View.VISIBLE);
        filter.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        cart_icon.setVisibility(View.VISIBLE);
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productPageFragment.add_to_cart_ll.performClick();
            }
        });
    }
    @Override
    public void text_back_butt(String header) {
        nav_title.setVisibility(View.VISIBLE);
        nav_title.setText(header);
        lang_ll.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        waste.setVisibility(View.GONE);
        menu_back.setVisibility(View.VISIBLE);
        filter.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        cart_icon.setVisibility(View.GONE);
    }
    @Override
    public void text_back_butt_final_add(String header) {
        nav_title.setVisibility(View.VISIBLE);
        nav_title.setText(header);
        lang_ll.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        waste.setVisibility(View.GONE);
        menu_back.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        cart_icon.setVisibility(View.GONE);
    }
    @Override
    public void review_page_head(String head) {
        nav_title.setVisibility(View.VISIBLE);
        nav_title.setText(head);
        lang_ll.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        waste.setVisibility(View.GONE);
        menu_back.setVisibility(View.VISIBLE);
        cart_icon.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
    }
    int count=0;
    @Override
    public void filter(String head) {
        nav_title.setVisibility(View.VISIBLE);
        nav_title.setText(head);
        lang_ll.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        waste.setVisibility(View.GONE);
        menu_back.setVisibility(View.VISIBLE);
        cart_icon.setVisibility(View.GONE);
        filter.setVisibility(View.VISIBLE);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyListFragment.filter();
            }
        });
        menu.setVisibility(View.GONE);
    }
    @Override
    public void reviews_page(Restaurants restaurants){
        Reviewsfragment fragment4 = new Reviewsfragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant",restaurants);
        fragment4.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).addToBackStack(null).commit();
    }
    @Override
    public void gotoinvoicepage(JsonOrders invoice) {
        Invoicefragment fragment4 = new Invoicefragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("invoice", invoice);
        fragment4.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).commit();
    }
    @Override
    public void back_to_product(){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        MenuSlidingFragment menuListFragment = new MenuSlidingFragment();
//        fragmentManager.beginTransaction().replace(R.id.container_main, menuListFragment).addToBackStack(null).commit();
        onBackPressed();
    }
    @Override
    public void to_login() {
        animation_direction=true;
//        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, loginFragment).addToBackStack(null).commit();
    }

    @Override
    public void register(String code,String mem_id) {
//            onBackPressed();
        animation_direction=true;
//        FragmentManager fragmentManager = getSupportFragmentManager();
        VerificationFragment verificationFragment = new VerificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code",code);
        bundle.putString("id",mem_id);
        verificationFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, verificationFragment).commit();
    }

    @Override
    public void area_list(String id) {

    }

    @Override
    public void showmenu(Restaurants restaurants) {
        animation_direction=true;
        MenuCategoryfragment fragment = new MenuCategoryfragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant", restaurants);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment).addToBackStack(null).commit();
    }
    @Override
    public void final_page(ArrayList<CartItem> cart_items) {
        animation_direction=true;
        Bundle bundle = new Bundle();
        bundle.putSerializable("cart_items", cart_items);
        FinalFragment finalFragment = new FinalFragment();
        finalFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, finalFragment).addToBackStack(null).commit();
    }
    @Override
    public void clear_cart() {
        cart_items.clear();
    }
    @Override
    public void to_home() {
        animation_direction=true;
//        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        HomeFragment fragment4 = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).commit();
    }

    @Override
    public void logout() {
        animation_direction=true;
//        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, homeFragment).addToBackStack(null).commit();
    }

    @Override
    public void language() {
        Intent mainIntent = new Intent(getApplicationContext(), LanguageActvity.class);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public void area_list(String id,Restaurants restaurants) {
        animation_direction=true;
//        FragmentManager fragmentManager = getSupportFragmentManager();
        AreaFragment areaFragment = new AreaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putSerializable("restaurants", restaurants);
        areaFragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.container_main, areaFragment).addToBackStack(null).commit();
    }

    @Override
    public void text_back_butt_logout(String head) {
        nav_title.setVisibility(View.VISIBLE);
        nav_title.setText(head);
        lang_ll.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        logout.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        waste.setVisibility(View.GONE);
        menu_back.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        menu.setVisibility(View.VISIBLE);
        cart_icon.setVisibility(View.GONE);
    }
    @Override
    public void to_promotions(Restaurants restaurants) {
        animation_direction=true;
        Bundle bundle=new Bundle();
        bundle.putSerializable("restaurants", restaurants);
//        FragmentManager fragmentManager = getSupportFragmentManager();
        OffersListFragment offersListFragment = new OffersListFragment();
        offersListFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, offersListFragment).addToBackStack(null).commit();
    }
    @Override
    public void five_items() {

    }

    @Override
    public void songselected(Restaurants restaurants) {
        animation_direction=true;
        CompanyPageFragment fragment = new CompanyPageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant",restaurants);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment).addToBackStack(null).commit();
    }

    @Override
    public void gotoordrerspage() {
        animation_direction=true;
        MyAccountFragment fragment4 = new MyAccountFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).addToBackStack(null).commit();
    }

    @Override
    public void companyfragment(String cat) {
        animation_direction=true;
        companyListFragment=new CompanyListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("url", "cat");
        bundle.putSerializable("cat", cat);
        companyListFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, companyListFragment).addToBackStack(null).commit();
    }

    public void companyffragment(String type) {
        animation_direction=true;
        companyListFragment=new CompanyListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("url", "type");
        bundle.putSerializable("cat", type);
        companyListFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, companyListFragment).addToBackStack(null).commit();
    }
    @Override
    public void home_head(String head) {
        lang_ll.setVisibility(View.VISIBLE);
        nav_title.setVisibility(View.VISIBLE);
        nav_title.setText(head);
        search.setVisibility(View.VISIBLE);
        logout.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        waste.setVisibility(View.INVISIBLE);
        menu_back.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        menu.setVisibility(View.VISIBLE);
        cart_icon.setVisibility(View.GONE);
    }
    @Override
    public void product_list(String rest_id,String cat_id,String cat_name) {
        animation_direction=true;
        ProductListFragment fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("rest_id", rest_id);
        bundle.putSerializable("cat_id", cat_id);
        bundle.putSerializable("cat_name", cat_name);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment).addToBackStack(null).commit();
    }
//    CompanyPageFragment companyPageFragment = new CompanyPageFragment();
//    @Override
//    public void area_selected() {
////        cart_items.clear();
////        companyPageFragment.dis_area();
//        onBackPressed();
//    }


    @Override
    public void product_page() {

    }

    @Override
    public void add_to_cart(Products products,String quantity,String spl_req) {
        animation_direction=true;
        CartItem item=new CartItem(quantity,products,spl_req);
        cart_items.add(item);
        Bundle bundle=new Bundle();
        bundle.putSerializable("cart_items", cart_items);
//        FragmentManager fragmentManager = getSupportFragmentManager();
        CartFragment cartFragment = new CartFragment();
        cartFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, cartFragment).addToBackStack(null).commit();
    }
}
