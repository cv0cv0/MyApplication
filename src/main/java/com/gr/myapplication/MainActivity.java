package com.gr.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.Toolbar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private Fragment[] tabs = new Fragment[4];
    private String[] titles = new String[]{"第1个Fragment", "第2个Fragment", "第3个Fragment", "第4个Fragment"};
    private FragmentPagerAdapter adapter;
    private Toolbar toolbar;

    private TabBottom[] tabIndicators = new TabBottom[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        TabBottom chat = (TabBottom) findViewById(R.id.chat);
        TabBottom contacts = (TabBottom) findViewById(R.id.contacts);
        TabBottom find = (TabBottom) findViewById(R.id.find);
        TabBottom me = (TabBottom) findViewById(R.id.me);
        chat.setOnClickListener(this);
        contacts.setOnClickListener(this);
        find.setOnClickListener(this);
        me.setOnClickListener(this);
        tabIndicators[0] = chat;
        tabIndicators[1] = contacts;
        tabIndicators[2] = find;
        tabIndicators[3] = me;

        for (int i = 0; i < titles.length; i++) {
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE, titles[i]);
            tabFragment.setArguments(bundle);
            tabs[i] = tabFragment;
        }

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        viewPager.addOnPageChangeListener(this);

        chat.setIconAlpha(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setOptionalIconsVisible(menu, true);
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setOptionalIconsVisible(Menu menu, boolean visible) {
        try {
            Class<?> menuBuileder = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method method = menuBuileder.getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
            method.setAccessible(true);
            method.invoke(menu, visible);
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

    @Override
    public void onClick(View view) {
        for (int i = 0; i < tabIndicators.length; i++) {
            tabIndicators[i].setIconAlpha(0);
        }
        switch (view.getId()) {
            case R.id.chat:
                tabIndicators[0].setIconAlpha(1);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.contacts:
                tabIndicators[1].setIconAlpha(1);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.find:
                tabIndicators[2].setIconAlpha(1);
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.me:
                tabIndicators[3].setIconAlpha(1);
                viewPager.setCurrentItem(3, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset>0) {
            tabIndicators[position].setIconAlpha(1 - positionOffset);
            tabIndicators[position + 1].setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
