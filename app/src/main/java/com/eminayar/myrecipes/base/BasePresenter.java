package com.eminayar.myrecipes.base;

/**
 * Created by EminAyar on 21.06.2018.
 */

import android.text.TextUtils;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private V view;

    public BasePresenter() {
    }

    @Override
    final public V getView() {
        return view;
    }

    @Override
    public void attachView(V view) {
        this.view = view;
        this.view.setUI();
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public boolean isEmpty(String text) {
        if (TextUtils.isEmpty(text)) {
            return true;
        } else
            return false;
    }
}