package com.eminayar.myrecipes.base;

/**
 * Created by EminAyar on 21.06.2018.
 */
public interface BaseContract {
    interface View {
        void showLoading();

        void hideLoading();

        void setUI ();
    }

    interface Presenter<V extends View> {
        V getView();

        void attachView(V view);

        void detachView();

        boolean isEmpty(String editText);
    }
}
