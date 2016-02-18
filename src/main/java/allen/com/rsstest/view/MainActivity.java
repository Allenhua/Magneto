package allen.com.rsstest.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import allen.com.rsstest.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton searchButton = null;
    private EditText keyWords = null;

    private Toolbar toolbar;
    private long mLastTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("intent","success");
        initView();

    }

    private void initView(){

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //toolbar.setTitle("首页");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("首页");
        getSupportActionBar().setHomeButtonEnabled(true);


        searchButton = (ImageButton) findViewById(R.id.search_button);
        keyWords = (EditText) findViewById(R.id.search_text);



        searchButton.setOnClickListener(this);


        //监听回车键
        keyWords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    onClick(null);
                }
                return false;
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
            Intent intentAbout = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        long mNowTime = System.currentTimeMillis();
        if (mNowTime - mLastTime > 2000){
            Toast.makeText(getApplicationContext(),"再按一次将退出",Toast.LENGTH_SHORT).show();
            mLastTime = mNowTime;
        }else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(MainActivity.this,SearchResultTabActivity.class);
        intent.putExtra("key",keyWords.getText().toString());

        Log.d("intent",keyWords.getText().toString());
        startActivity(intent);


    }


}

