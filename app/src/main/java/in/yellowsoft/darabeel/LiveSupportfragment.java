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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class LiveSupportfragment extends Fragment {
    String head;
    FragmentTouchListner mCallBack;
    boolean loaded=false;
    ProgressBar progress;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
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
        return inflater.inflate(R.layout.live_support, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
//        loaded=true;
        MyTextView about_us=(MyTextView)v.findViewById(R.id.aboutus_descri);
        MyTextView title_about_us=(MyTextView)v.findViewById(R.id.tittle_aboutus);
            head=String.valueOf(Html.fromHtml(Settings.getword(getActivity(), "live_support")));
            mCallBack.text_back_butt(head);
            title_about_us.setText(Html.fromHtml( Settings.getword(getActivity(),"live_support")));
           // about_us.setText(Html.fromHtml(Settings.getSettings(getActivity(),"about"+Settings.get_lan(getActivity()))));
        WebView webView = (WebView) v.findViewById(R.id.webView2);
        progress = (ProgressBar) v.findViewById(R.id.progressBar7);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new MyWebViewClient());
        webView.loadUrl("https://www.darabeel.com/chat.php");
        progress.setMax(100);
        progress.setProgress(0);


    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

}
