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
import android.widget.EditText;

import com.xtel.basicsample.R;
import com.xtel.basicsample.presenter.HomePresenter;
import com.xtel.basicsample.view.activity.inf.IHome;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbackListenerActive;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.CallbackLisenerRegister;
import com.xtel.nipservicesdk.callback.CallbackListenerReactive;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_Reactive;
import com.xtel.nipservicesdk.model.entity.RESP_Register;
import com.xtel.nipservicesdk.utils.JsonHelper;


/**
 * Created by vivhp on 12/29/2016.
 */

public class HomeActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener, IHome {
    HomePresenter presenter;

    private EditText editText;
    private boolean isPhone = true;

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

        editText = (EditText) findViewById(R.id.phone);
    }

    public void RadioButton(View view) {
        if (view.getId() == R.id.rd_phone)
            isPhone = true;
        else
            isPhone = false;
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
    }

    public void Register(View view) {
        onRegisterNip();
    }

    public void Active(View view) {
        activeAccount();
    }

    public void ReActive(View view) {
        String username = editText.getText().toString();
        callbackManager.reactiveNipAccount(username, isPhone, new CallbackListenerReactive() {
            @Override
            public void onSuccess(RESP_Reactive reactive) {
                Log.e("reactive", JsonHelper.toJson(reactive));
            }

            @Override
            public void onError(Error error) {
                Log.e("reactive", JsonHelper.toJson(error));
            }
        });
    }

    public void LoginFace(View v) {
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
        String accessTokent = "EAAXEtiHaSw0BAMJ73gmpSMSBAVIAdlZCpY9YEzcd5T8j021otZC7sFuj5A0CcI6wKxmZAbOdNcOrKiMixvHGATq9PNQ1QKOLkrWSPEV21BuHtvOnnKy14r10G2dAoeT4JUk58s1ED89VrQXybGVWqkJ2gOJJUy86METvouu3e7eodjjBo6dl4vb6iZAJte6rbEdZA9kDYjobQijtXgpDw";
        callbackManager.LoginFaceook(accessTokent, new CallbacListener() {
            @Override
            public void onSuccess(RESP_Login success) {
                Log.e("login", success.getSession());
            }

            @Override
            public void onError(Error error) {
                Log.e("login", JsonHelper.toJson(error));
            }
        });

    }

    private void onLoginUser(){
        String user_name = "01673378303";
        String password = "123456";


        callbackManager.LoginNipAcc(user_name, password, new CallbacListener() {
            @Override
            public void onSuccess(RESP_Login success) {
                Log.e("Session nip", success.getSession());
//                Log.e("Time alive nip", String.valueOf(success.getTime_alive()));
                Log.e("Object ", success.toString());
            }

            @Override
            public void onError(Error error) {
                Log.e("Error nip: + ", error.getMessage());
                Log.e("Error code nip: + ", String.valueOf(error.getCode()));
                Log.e("Object Err", error.toString());
            }
        });
    }

    private void onLoginAccountKit(){
        String authorization_code = "YOUR_AUTHORIZATION_CODE";
        callbackManager.LoginAccountKit(authorization_code, new CallbacListener() {
            @Override
            public void onSuccess(RESP_Login success) {
                Log.e("Session acc", success.getSession());
            }

            @Override
            public void onError(Error error) {
                Log.e("Error acc: + ", error.getMessage());
                Log.e("Error code acc: + ", String.valueOf(error.getCode()));
            }
        });
    }

    private void onRegisterNip() {
        String user_name = editText.getText().toString();
        String password = "123456";
        String email = "";
        int sendmail;
        String type;

        if (isPhone) {
            sendmail = 0;
            type = "PHONE-NUMBER";
        } else {
            sendmail = 1;
            type = "EMAIL";
            email = editText.getText().toString();
        }

        callbackManager.registerNipService(user_name, password, email, sendmail, type, new CallbackLisenerRegister() {
            @Override
            public void onSuccess(RESP_Register register) {
                Log.e("Activation_code nip", JsonHelper.toJson(register));
            }

            @Override
            public void onError(Error error) {
                Log.e("Object reg Err", JsonHelper.toJson(error));
            }
        });
    }

    private void activeAccount() {
        String auth_code = "AQCHNVx3KKxbZvZsPW8uqt5rqjG6qAYpLLKs28EUWf8O8Tv0EOHVxAuukVURtIrBB-VGPsSPnrzcglGDVBgdMaDWSlmhOjCn7hU3qjcnCF_-_2M6UA9GuYlM-kEWsejXjOb-u2Gl9k-zQMZtqPUhnbierQ2eusRHye__c7enEr9yRM1K-P23Kpr1MAJgZJhQ_6_l9has4h4DApvpeORHGd0BCMwzb43IrPtqTOkSQd-HE15QZUE3cS01jiT-Ggqd7bHT64Q7UXEWSAqRqPprlO_O";
        String type = "PHONE-NUMBER";

        callbackManager.activeNipAccount(auth_code, type, new CallbackListenerActive() {
            @Override
            public void onSuccess() {
                Log.e("active", "ok");
            }

            @Override
            public void onError(Error error) {
                Log.e("active", JsonHelper.toJson(error));
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
