package com.xtel.basicsample.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xtel.basicsample.R;
import com.xtel.basicsample.presenter.HomePresenter;
import com.xtel.basicsample.view.activity.inf.IHome;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;


/**
 * Created by vivhp on 12/29/2016.
 */

public class HomeActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener, IHome {
    HomePresenter presenter;


    private DrawerLayout drawer;
    private NavigationView navigationView;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        showShortToast(Demo.getData(this));
        callbackManager = CallbackManager.create(this);

        presenter = new HomePresenter(this);
        initView();
        initNavigation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        callbackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBarShort(v, "Replace Message....");
            }
        });
        onLogin();
    }

    @SuppressWarnings("deprecation")
    private void initNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void onLogin() {
        String accessTokent = "EAAXEtiHaSw0BANabsJ4B24YvNC3bKOZA4lmdBiDHvecx2myMNzNLZAJefqWR9vvFCmgrRr0ayzuN5779EISYKo3oDzyyye8bK0FKTU05CUFVqwR2nfpY5YycIvzOaloB38vKLFEAVHdLYZBoYOedXCCrrxjEhO5auav2MxDadzDkQNxTvSObkcsZALA2OEaCYnrsZATYUsEydILw8L0xN";
        callbackManager.LoginFaceook(accessTokent, new CallbacListener() {
            @Override
            public void onSuccess(RESP_Login success) {
                Log.e("Session", success.getSession());
            }

            @Override
            public void onError(Error error) {
                Log.e("Error", error.getType().toString());
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_exit) {
            exitApp();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
