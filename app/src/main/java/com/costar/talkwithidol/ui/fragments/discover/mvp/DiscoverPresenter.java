package com.costar.talkwithidol.ui.fragments.discover.mvp;


import java.util.LinkedHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Timed;

public class DiscoverPresenter {

    private final DiscoverView discoverView;
    private final DiscoverModel discoverModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;
    //private FitnessUtils fitnessUtils;


    public DiscoverPresenter(DiscoverView discoverView, DiscoverModel discoverModel) {
        this.discoverView = discoverView;
        this.discoverModel = discoverModel;

           }


    public void onCreateView() {

        discoverView.prepareDiscoverData();
    }


    public void onDestroyView() {
        compositeDisposable.clear();
    }



}
