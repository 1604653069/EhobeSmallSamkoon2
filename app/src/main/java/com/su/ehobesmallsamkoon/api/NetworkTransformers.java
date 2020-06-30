package com.su.ehobesmallsamkoon.api;


import android.util.Log;

import com.su.ehobesmallsamkoon.base.IBaseDisplay;
import com.su.ehobesmallsamkoon.bean.respond.BaseRespond;
import com.su.ehobesmallsamkoon.network.ApiException;
import com.su.ehobesmallsamkoon.network.RxJava2NullException;
import com.su.ehobesmallsamkoon.util.NetworkUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava2 转换器 用于网络加载数据 已实现功能有：
 * <p>
 * 1.检测有无网络
 * 2.加载网络时显示加载框 结束是隐藏
 * 3.控制RxJava生命周期，防止内存泄漏
 */
public class NetworkTransformers<T> implements ObservableTransformer<BaseRespond<T>, T> {
    private IBaseDisplay mView;
    private boolean showLoading;

    public NetworkTransformers(IBaseDisplay mView) {
        this(mView, true);
    }

    public NetworkTransformers(IBaseDisplay mView, boolean showLoading) {
        if (mView == null) throw new RuntimeException("IBaseDisplay is not NULL");
        this.mView = mView;
        this.showLoading = showLoading;
    }

    @Override
    public ObservableSource<T> apply(Observable<BaseRespond<T>> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    //请求前检测网络
                    if (!NetworkUtil.isConnected(mView.getContext())) {
                        NetworkUtil.showNoNetWorkDialog(mView.getContext());
                        mView.onRequestFinish();
                        disposable.dispose();
                    } else {
                        if (showLoading) mView.showProgressDialog();
                    }
                })
                .doFinally(() -> {
                    if (showLoading) mView.hideProgressDialog();
                    mView.onRequestFinish();
                })
                .map(filterData())
                .map(checkInnerData())
                .doOnError(throwable -> {
                    //RxJava2NullException 交给RxCallback处理
                    if (!(throwable instanceof RxJava2NullException)) {
                        mView.showError(throwable);
                    }
                })
                .compose(mView.bindToLifecycle());

    }

    /**
     * 过滤异常
     *
     * @return
     */
    public Function<? super BaseRespond<T>, BaseRespond<T>> filterData() {
        return (Function<BaseRespond<T>, BaseRespond<T>>) baseBean -> {
            Log.e("--", "filterData: "+baseBean.toString() );
            if (baseBean.getCode() == 0) {
                return baseBean;
            } else {
//                if (baseBean.status == -1) {
//                    mView.showReLoginDialog();
//                }
                throw new ApiException(baseBean.getCode(), baseBean.getMsg());
            }
        };
    }

    /**
     * 提取内部真正数据，并检测数据是否为空
     *
     * @return
     */
    public Function<? super BaseRespond<T>, T> checkInnerData() {
        return (Function<BaseRespond<T>, T>) baseBean -> {
            if (baseBean.getData() != null) return baseBean.getData();
            //返回空数据时 抛出一个异常让CallBack处理
            throw new RxJava2NullException();
        };
    }

}