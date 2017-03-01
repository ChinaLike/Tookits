package com.like.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.like.R;
import com.like.core.AbsActivity;
import com.like.databinding.ActivityMainBinding;

import butterknife.Bind;

public class MainActivity extends AbsActivity<ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        mToolbar.setTitle("项目简介");
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void dataCallback(int result, Object data) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity:
                /**AbsActivity的使用*/
                startActivity(new Intent(this,AbsActivityTest.class));
                break;
            case R.id.module:
                /**AbsFragment的使用*/
                break;
            case R.id.dialog:
                /**AbsDialog的使用*/
                break;
        }
        mDrawerLayout.closeDrawers();
        return false;
    }
}
