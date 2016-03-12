package allen.com.rsstest.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import allen.com.rsstest.R;

public class MainActivity extends AppCompatActivity implements ResultFragment.OnFragmentInteractionListener{

    private ListView mlistview;
    private ArrayAdapter listArrayAdapter;
    private String[] itemString = new String[]{"磁力搜索","电影更新"};
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private long mLastTime = 0;
    private SearchIndexFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("intent","success");
        initView();

    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("首页");
        getSupportActionBar().setHomeButtonEnabled(true);

        mlistview = (ListView) findViewById(R.id.drawer_item);
        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer);


        mToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.draw_open,R.string.draw_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mToggle.syncState();
        //searchButton.setOnClickListener(this);
        listArrayAdapter = new ArrayAdapter(this,R.layout.drawer_list_item,R.id.drawer_item_text,itemString);
        mlistview.setAdapter(listArrayAdapter);
        mlistview.setOnItemClickListener(new DrawerItemClickListener());

        mDrawer.setDrawerListener(mToggle);

        fragment = SearchIndexFragment.getNewInstance();
        Log.d("Fragment","onCreateView");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame,fragment).commit();
        Log.d("Fragment","onCreateView");
        mlistview.setItemChecked(0,true);
        getSupportActionBar().setTitle("磁力搜索");


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

    private void itemClick(int position){
        if (position == 0){

            Log.d("Fragment","onCreateView");

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_frame,fragment).commit();
            Log.d("Fragment","onCreateView");
            mlistview.setItemChecked(position,true);
            getSupportActionBar().setTitle("磁力搜索");
            mDrawer.closeDrawer(mlistview);
        }else if (position == 1){
            ResultFragment fragment = ResultFragment.newInstance("",9);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_frame,fragment).commit();
            Log.d("Fragment","onCreateView");
            mlistview.setItemChecked(position,true);
            getSupportActionBar().setTitle("电影更新");
            mDrawer.closeDrawer(mlistview);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    private class DrawerItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            itemClick(position);
        }
    }

}

