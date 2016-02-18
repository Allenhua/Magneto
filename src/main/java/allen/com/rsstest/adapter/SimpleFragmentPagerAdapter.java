package allen.com.rsstest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import allen.com.rsstest.view.ResultFragment;

/**
 * Created by Allen on 2016/2/12.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;
    private String strings[] = new String[]{"引擎1","引擎2","引擎3","引擎4","引擎5"};
    String keywords = "";

    public SimpleFragmentPagerAdapter(FragmentManager fm,String s) {
        super(fm);
        keywords = s;
    }

    @Override
    public Fragment getItem(int position) {
        return ResultFragment.newInstance(keywords,position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }
}
