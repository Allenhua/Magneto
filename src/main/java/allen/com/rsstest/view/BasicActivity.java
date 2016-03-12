package allen.com.rsstest.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.List;

import allen.com.rsstest.R;
import allen.com.rsstest.util.OkhttpUtil;

public class BasicActivity extends AppCompatActivity {

    private static OkhttpUtil okhttpUtil;
    private static List<Activity> activities;
    private Toolbar toolbar;

    public OkhttpUtil getOkhttpUtil() {
        if (okhttpUtil == null) {
            okhttpUtil = OkhttpUtil.getInstance();
        }
        return okhttpUtil;
    }

    /*public Toolbar getToolbar(){
        return toolbar;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_basic);
    }

    /*@Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }*/
}
