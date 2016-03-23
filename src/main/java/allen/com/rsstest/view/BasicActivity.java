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

    protected OkhttpUtil basicOkhttp = OkhttpUtil.getInstance();
    protected Toolbar toolbar;
    protected Bundle savedInstanceState;


    protected void clearOkhttp(){
        if (basicOkhttp != null){
            basicOkhttp.cancelAll();
        }
    }

    protected void cancelThis(Activity activity){
        basicOkhttp.cancelCall(activity);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        initViews();
        initVars();
        loadDatas();
    }


    protected void initViews(){

    }

    protected void initVars(){

    }

    protected void loadDatas(){

    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}
