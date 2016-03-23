package allen.com.rsstest.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import allen.com.rsstest.R;
import allen.com.rsstest.adapter.SimpleFragmentPagerAdapter;
import allen.com.rsstest.util.OkhttpUtil;

public class SearchResultTabActivity extends BasicActivity implements ResultFragment.OnFragmentInteractionListener {

    private SimpleFragmentPagerAdapter sAdapter;
    private ViewPager vPager;
    private String keywords;
    private TabLayout tabLayout;

    @Override
    protected void initVars() {
        super.initVars();
        Intent intent = getIntent();
        keywords = intent.getStringExtra("key");
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_search_result_tab);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        vPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_layout);

    }

    @Override
    protected void loadDatas() {
        super.loadDatas();

        getSupportActionBar().setTitle(keywords);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),keywords);
        Log.d("SearchResult","success");
        vPager.setAdapter(sAdapter);
        vPager.setOffscreenPageLimit(3);
        Log.d("SearchResult","success");
        tabLayout.setupWithViewPager(vPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("String","fragment");
    }
}
