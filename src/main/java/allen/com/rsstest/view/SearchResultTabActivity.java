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

public class SearchResultTabActivity extends AppCompatActivity implements ResultFragment.OnFragmentInteractionListener {

    private SimpleFragmentPagerAdapter sAdapter;
    private ViewPager vPager;
    private ResultFragment rFragment;
    private String keywords;
    private TabLayout tabLayout;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_tab);

        Intent intent = getIntent();
        keywords = intent.getStringExtra("key");
        Log.d("keywords",keywords);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        //toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle(keywords);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),keywords);
        Log.d("SearchResult","success");
        vPager = (ViewPager) findViewById(R.id.pager);
        vPager.setAdapter(sAdapter);
        vPager.setOffscreenPageLimit(3);
        Log.d("SearchResult","success");
        tabLayout = (TabLayout) findViewById(R.id.sliding_layout);
        tabLayout.setupWithViewPager(vPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("String","fragment");
    }
}
