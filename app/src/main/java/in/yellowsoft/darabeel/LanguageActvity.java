package in.yellowsoft.darabeel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


public class LanguageActvity extends Activity {
  RelativeLayout english,arabic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_screen);
        english=(RelativeLayout)findViewById(R.id.relativeLayout_english);
        arabic=(RelativeLayout)findViewById(R.id.relativeLayout_arabic);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.set_user_language(LanguageActvity.this, "en");
                Settings.set_isfirsttime(LanguageActvity.this, "en");
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.set_user_language(LanguageActvity.this, "ar");
                Settings.set_isfirsttime(LanguageActvity.this, "ar");
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}

