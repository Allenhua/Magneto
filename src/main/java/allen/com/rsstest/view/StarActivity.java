package allen.com.rsstest.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import allen.com.rsstest.R;

public class StarActivity extends BasicActivity {

    private void initToolbar(){

        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setTitle("收藏列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initVars() {
        super.initVars();
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_star);
        initToolbar();
    }

    @Override
    protected void loadDatas() {
        super.loadDatas();
    }
}
