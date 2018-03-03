package com.example.divyang.carpark;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by divya on 22-01-2018.
 */

public class NavMain extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);



    }





    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                // bundle.putStringArray("key2",tok);
                 fragment = new MainActivity();
                // fragment.setArguments(bundle);
                title = "HOME";
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                fragment = new locateParking();
                title = "Locate";
                break;

            case 2:
                Bundle bundle3 = new Bundle();
                fragment = new bookingHistory();
                title = "booking";
                break;

            case 3:
                Bundle bundle4 = new Bundle();

                title = "profile";
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
//            getActionBar().setTitle(title);
        }
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
}
