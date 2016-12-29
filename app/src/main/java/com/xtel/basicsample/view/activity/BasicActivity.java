package com.xtel.basicsample.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtel.basicsample.R;
import com.xtel.sdk.callback.DialogListener;

import java.io.Serializable;

/**
 * Created by vivhp on 12/29/2016.
 */

public class BasicActivity extends AppCompatActivity {
    boolean isWaitingForExit = false;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    public BasicActivity() {
    }

    protected void initToolbar(int id, String title) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (title != null) {
            actionBar.setTitle(title);
        }
    }

    protected void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    protected void showSnackBarShort(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackBarLong(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }


    protected void showProgressBar(boolean isTouchOutside, boolean isCancel, String title, String message) {
        progressDialog = new ProgressDialog(BasicActivity.this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setCancelable(isCancel);
        progressDialog.setCanceledOnTouchOutside(isTouchOutside);

        if (title != null) {
            progressDialog.setTitle(title);
        }
        if (message != null) {
            progressDialog.setMessage(message);
        }

        progressDialog.show();
    }

    protected void closeProgressBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void showMaterialDialog(boolean isTouchOutside, boolean isCancel, String title, String message,
                                      final String negative, String positive, final DialogListener dialogListener) {
        dialog = new Dialog(BasicActivity.this, R.style.Theme_Transparent);
        dialog.setContentView(R.layout.dialog_material);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(isCancel);
        dialog.setCanceledOnTouchOutside(isTouchOutside);

        TextView tv_title = (TextView) dialog.findViewById(R.id.dialog_txt_title);
        TextView tv_message = (TextView) dialog.findViewById(R.id.dialog_txt_message);
        Button btn_negative = (Button) dialog.findViewById(R.id.dialog_btn_negative);
        Button btn_positive = (Button) dialog.findViewById(R.id.dialog_btn_positive);

        if (title == null) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }

        if (message == null) {
            tv_message.setVisibility(View.GONE);
        } else {
            tv_message.setText(message);
        }

        if (negative == null) {
            btn_negative.setVisibility(View.GONE);
        } else {
            btn_negative.setText(negative);
        }

        if (positive == null) {
            btn_positive.setVisibility(View.GONE);
        } else {
            btn_positive.setText(positive);
        }

        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogListener.onClicked(null);
            }
        });

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogListener.onCancel();
            }
        });

        if (dialog != null) {
            dialog.show();
        }
    }

    public void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    //Khởi chạy Fragment giao diện và add vào stack
    protected void replaceFragment(int id, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(id, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    protected void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    protected void startActivityFinish(Class clazz) {
        startActivity(new Intent(this, clazz));
        finish();
    }

    protected void startActivityForResult(Class clazz, int requestCode) {
        startActivityForResult(new Intent(this, clazz), requestCode);
    }

    protected void startActivityForResultWithInteger(Class clazz, String key, int data, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(key, data);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class clazz, String key, Object object, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(key, (Serializable) object);
        startActivityForResult(intent, requestCode);
    }

    protected void finishActivity() {
        finish();
    }

    protected void showConfirmExitApp() {
        if (isWaitingForExit) {
            System.exit(0);
        } else {
            new AsyncTask<Object, Object, Object>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    isWaitingForExit = true;
                    showShortToast(getString(R.string.text_back_press_to_exit));
                }

                @Override
                protected Object doInBackground(Object... params) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    isWaitingForExit = false;
                }
            }.execute();
        }
    }

    protected void exitApp() {
        System.exit(0);
    }

}
