package allen.com.rsstest.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import allen.com.rsstest.R;

public class AboutActivity extends BasicActivity {

    //Toolbar toolbar;

    @Override
    protected void initVars() {
        super.initVars();
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about_activity);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void loadDatas() {
        super.loadDatas();
    }
}
