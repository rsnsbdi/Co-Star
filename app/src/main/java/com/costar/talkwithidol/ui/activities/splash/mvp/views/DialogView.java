package com.costar.talkwithidol.ui.activities.splash.mvp.views;


import android.app.AlertDialog;
import android.content.Context;

import io.reactivex.Observable;

public class DialogView {

    private final Context context;

    public DialogView(Context context) {
        this.context = context;
    }


    public Observable<Boolean> showDialog() {
        return Observable.create(subscriber -> {

            final AlertDialog ad = new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle("Download")
                    .setMessage("Please download the Google Fit app")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        subscriber.onNext(false);
                        subscriber.onComplete();
                        dialog.cancel();
                    })
                    .create();
            ad.show();
        });
    }


}
