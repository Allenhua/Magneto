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
import allen.com.rsstest.util.OkhttpUtil;

public class MainActivity extends BasicActivity implements ResultFragment.OnFragmentInteractionListener{

    private NavigationView navigView;
    private String[] itemString = new String[]{"磁力搜索","电影更新","收藏列表"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawer;
    private long mLastTime = 0;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void initVars() {
        super.initVars();
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("磁力搜索");

        navigView = (NavigationView) findViewById(R.id.menu_view);
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

    }

    @Override
    protected void loadDatas() {
        super.loadDatas();
        initFragments();
        navigView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.magnet_search:
                        changeFragments(0,item);
                        break;
                    case R.id.movie_update:
                        changeFragments(1,item);
                        break;
                    case R.id.favorite_list:
                        Intent intent = new Intent(MainActivity.this,StarActivity.class);
                        startActivity(intent);
                        mDrawer.closeDrawer(navigView);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        mToggle.syncState();
        mDrawer.addDrawerListener(mToggle);
        if (this.savedInstanceState == null){
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

    private void changeFragments(int position,MenuItem item){
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
        item.setChecked(true);
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

    @Override
    public void finish() {
        super.finish();
        clearOkhttp();
        finishAffinity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

