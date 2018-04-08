package com.example.divyang.carpark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by divya on 22-01-2018.
 */

public class NavMain extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    Intent i = getIntent();
    String locationName;
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
        Intent i = getIntent();
        locationName = i.getStringExtra("locationName");
        displayView(0);
        // opens zeroth fragment
    }





    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                 bundle.putString("locationName",locationName);
                 fragment = new MainActivity();
                 fragment.setArguments(bundle);
                title = "HOME";
                break;
            case 1:
                Bundle bundle2 = new Bundle();
                fragment = new locateParking();
                title = "locate";
                break;

            case 2:
                Bundle bundle3 = new Bundle();
                //profile here!
                fragment = new Profile();
                title = "profile";
                break;

            case 3:
                Bundle bundle4 = new Bundle();
                fragment = new ServicePage();
                title = "Service";
                break;
            case 4:
            Bundle bundle5 = new Bundle();

            break;


            case 5:
                Bundle bundle6 = new Bundle();
                fragment = new MapsActivity();
                title = "profile";
                break;
            case 6:
                title = "Logout";
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(NavMain.this,loginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case 7:
                title = "GetMyQr";
                Intent i = new Intent(NavMain.this,GetMyQr.class);
                startActivity(i);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        Toast.makeText(this, "here", Toast.LENGTH_LONG).show();
        if(result != null) {
            Toast.makeText(this, "else", Toast.LENGTH_LONG).show();
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "else", Toast.LENGTH_LONG).show();
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                final String[] scannedString = result.getContents().toString().split("/");
                //id,location,slot
                Intent i = new Intent(this,qrGenerationPark.class );
                i.putExtra("locationName",result.getContents().toString());
                startActivity(i);
            }


        }
        else {
            Toast.makeText(this, "insuper else", Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }








}
