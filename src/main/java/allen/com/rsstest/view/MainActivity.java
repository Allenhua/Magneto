package allen.com.rsstest.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

import allen.com.rsstest.R;

public class MainActivity extends AppCompatActivity implements ResultFragment.OnFragmentInteractionListener{

    private NavigationView navigView;
    private String[] itemString = new String[]{"磁力搜索","电影更新","收藏列表"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private long mLastTime = 0;



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("intent","success");
        initFragments();
        initView();
        if (savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_frame,fragments.get(0)).commit();
        }

    }


//解决fragment重叠问题
    private void hideFragments(FragmentTransaction transaction,FragmentManager fg){
        for (Fragment f:fg.getFragments()) {
            transaction.hide(f);
        }
    }

    private void initFragments(){
        fragments.add(SearchIndexFragment.getNewInstance());
        fragments.add(ResultFragment.newInstance("",9));

    }

    private void changeFragments(int position){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction,manager);
        //防止fragment重复create，消耗资源
        if (!fragments.get(position).isAdded()){
            transaction.add(R.id.main_frame,fragments.get(position)).commit();
        }else {transaction.show(fragments.get(position)).commit();}
        Log.d("Fragment","onCreateView");

        getSupportActionBar().setTitle(itemString[position]);
        mDrawer.closeDrawer(navigView);
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("磁力搜索");
        getSupportActionBar().setHomeButtonEnabled(true);

        navigView = (NavigationView) findViewById(R.id.menu_view);
        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer);

        navigView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.magnet_search:
                        changeFragments(0);
                        break;
                    case R.id.movie_update:
                        changeFragments(1);
                        break;
                    case R.id.favorite_list:
                        break;
                    default:
                        return false;
                }
                item.setChecked(true);
                return true;
            }
        });

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
        mDrawer.addDrawerListener(mToggle);
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

        if (mDrawer.isDrawerOpen(navigView)){
            mDrawer.closeDrawer(navigView);
            return;
        }
        long mNowTime = System.currentTimeMillis();
        if (mNowTime - mLastTime > 2000){
            Toast.makeText(getApplicationContext(),"再按一次将退出",Toast.LENGTH_SHORT).show();
            mLastTime = mNowTime;
        }else {
            finish();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }

}

