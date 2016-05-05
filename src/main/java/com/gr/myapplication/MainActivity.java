package com.gr.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Toolbar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends FragmentActivity {
    private ViewPager viewPager;
    private Fragment[] tabs = new Fragment[4];
    private String[] titles = new String[]{"第1个Fragment", "第2个Fragment", "第3个Fragment", "第4个Fragment"};
    private FragmentPagerAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        for (int i=0;i<titles.length;i++) {
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE, titles[i]);
            tabFragment.setArguments(bundle);
            tabs[i]=tabFragment;
        }

        adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabs[position];
            }

            @Override
            public int getCount() {
                return tabs.length;
            }
        };
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setOptionalIconsVisible(menu,true);
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setOptionalIconsVisible(Menu menu,boolean visible){
        try {
            Class<?> menuBuileder=Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method method=menuBuileder.getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
            method.setAccessible(true);
            method.invoke(menu,visible);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
