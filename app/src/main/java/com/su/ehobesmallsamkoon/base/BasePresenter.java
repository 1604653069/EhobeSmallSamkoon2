package com.su.ehobesmallsamkoon.base;


import android.content.Context;

import com.su.ehobesmallsamkoon.network.ApiException;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 引入 IBaseDisplay mView  控制生命周期
 */
public class BasePresenter<T> implements IBasePresenter, IBaseDisplay {

    protected final String TAG = getClass().getSimpleName();

    protected IBaseDisplay mView;
    protected T mActivity;
//    protected T mFragment;
    @Override
    public void attachView(IBaseDisplay display) {
        this.mView = display;
        initialization();
    }

    public void initialization() {

    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mActivity = null;
    }

//    public T getFragment() {
//        return mFragment;
//    }
//
//    public void setFragment(T mFragment) {
//        this.mFragment = mFragment;
//        Log.e(TAG, "setFragment: "+mFragment );
//    }

    public T getActivity() {
        if (mActivity == null) {
            this.mActivity = ((T) getContext());
        }
        return mActivity;
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return mView.getBaseActivity();
    }

    @Override
    public void showProgressDialog() {
        mView.showProgressDialog();
    }

    @Override
    public void showProgressDialog(CharSequence message) {
        mView.showProgressDialog(message);
    }

    @Override
    public void hideProgressDialog() {
        mView.hideProgressDialog();
    }

    @Override
    public void showError(Throwable t) {
        mView.showError(t);
    }

    @Override
    public void onApiException(ApiException e) {
        mView.onApiException(e);
    }

    @Override
    public void onRequestFinish() {
        mView.onRequestFinish();
    }

    @Override
    public void changeDayNightMode(boolean isNightMode) {
        mView.changeDayNightMode(isNightMode);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return mView.bindToLifecycle();
    }

}
