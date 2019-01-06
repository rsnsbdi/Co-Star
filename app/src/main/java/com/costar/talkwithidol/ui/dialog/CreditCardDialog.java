package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.PaydockCustomerResponse.CreditCardParams;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

import static com.costar.talkwithidol.R.id.cardName;

public class CreditCardDialog {


    Dialog dialog;
    Activity activity;
    public EditText cardname, cardno, ccv, month, year;
    public Button pay;
    PreferencesManager preferencesManager;

    public CreditCardDialog(Activity activity, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.preferencesManager = preferencesManager;
        dialog = new Dialog(activity);
//        dialog = new Dialog(getInstance(), R.style.DialogStyle);
        dialog.setContentView(R.layout.credit_card_dialog);
        cardname = dialog.findViewById(cardName);
        cardno = dialog.findViewById(R.id.cardNumber);
        ccv = dialog.findViewById(R.id.ccv);
        month = dialog.findViewById(R.id.expiry_month);
        year = dialog.findViewById(R.id.expiry_year);
        pay = dialog.findViewById(R.id.pay);





    }

    public Flowable<CharSequence> cardnameCharSequenceFlowable() {
        return RxTextView.textChanges(cardname).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<CharSequence> cardnoCharSequenceFlowable() {
        return RxTextView.textChanges(cardno).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }
    public Flowable<CharSequence> monthCharSequenceFlowable() {
        return RxTextView.textChanges(month).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }
    public Flowable<CharSequence> yearCharSequenceFlowable() {
        return RxTextView.textChanges(year).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }
    public Flowable<CharSequence> ccvCharSequenceFlowable() {
        return RxTextView.textChanges(ccv).skip(1).toFlowable(BackpressureStrategy.LATEST);
    }

    public void setCardNameError(String error) {
        cardname.setError(error);
    }

    public void setCarnoError(String error) {
        cardno.setError(error);
    }

    public void setYearError(String error) {
        year.setError(error);
    }
    public void setMonthError(String error) {
        month.setError(error);
    }
    public void setCCVError(String error) {
        ccv.setError(error);
    }


    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }


    public JsonObject payment_source() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("card_name", cardname.getText().toString().trim());
        jsonObject.addProperty("card_number", cardno.getText().toString().trim());
        jsonObject.addProperty("expire_month", month.getText().toString().trim());
        jsonObject.addProperty("expire_year", year.getText().toString().trim());
        jsonObject.addProperty("card_ccv", ccv.getText().toString().trim());
        jsonObject.addProperty("gateway_id", preferencesManager.get(Constants.GATEWAYID));

        return jsonObject;
    }


    public CreditCardParams creditCardParams() {
        return CreditCardParams.builder().first_name(preferencesManager.get(Constants.FIRSTNAME)).last_name("").email(preferencesManager.get(Constants.EMAIL)).phone(preferencesManager.get(Constants.MOBILE)).reference(preferencesManager.get(Constants.REFERENCE)).payment_source(payment_source()).build();
    }


    public Observable<Object> payButtonObservable() {
        return RxView.clicks(pay);
    }


}
