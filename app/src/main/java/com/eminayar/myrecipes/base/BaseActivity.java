package com.eminayar.myrecipes.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by EminAyar on 21.06.2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {

    private KProgressHUD progressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progressHUD = KProgressHUD.create(BaseActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        progressHUD.show();
    }

    @Override
    public void hideLoading() {
        progressHUD.dismiss();
    }

}
