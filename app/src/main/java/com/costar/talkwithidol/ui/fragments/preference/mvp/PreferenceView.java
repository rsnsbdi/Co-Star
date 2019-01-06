package com.costar.talkwithidol.ui.fragments.preference.mvp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jakewharton.rxbinding2.view.RxView;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.UserPreferences.UserPreferencesResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.dialog.ShowLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


@SuppressLint("ViewConstructor")
public class PreferenceView extends FrameLayout {

    @BindView(R.id.tv_event_notificaton)
    TextView eventNotification;
    @BindView(R.id.tv_email_notification)
    TextView emailNotification;
    @BindView(R.id.label1)
    TextView label1;
    @BindView(R.id.label2)
    TextView label2;
    @BindView(R.id.label3)
    TextView label3;
    @BindView(R.id.label4)
    TextView label4;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.switch1)
    ToggleButton switch1;
    @BindView(R.id.switch2)
    ToggleButton switch2;
    @BindView(R.id.switch3)
    ToggleButton switch3;
    @BindView(R.id.switch4)
    ToggleButton switch4;

    @BindView(R.id.tv_done)
    TextView tv_done;
    private AppCompatActivity activity;
    PreferencesManager preferencesManager;
    ShowLoadingDialog showLoadingDialog ;

    public PreferenceView(@NonNull AppCompatActivity activity, PreferencesManager preferencesManager) {
        super(activity);
        this.activity = activity;
        this.preferencesManager =preferencesManager;
        inflate(activity, R.layout.setting_preferences, this);
        ButterKnife.bind(this);
        showLoadingDialog = new ShowLoadingDialog(activity);

    }


    public void showLoadingDialog(boolean isLoading) {
        if (isLoading) {
            showLoadingDialog.showDialog(true);
        } else {
            showLoadingDialog.showDialog(false);
        }

    }
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Observable<Object> doneButtonObservable() {
        return RxView.clicks(tv_done);
    }

    public void setUserPreference(UserPreferencesResponse userPreference) {


        emailNotification.setText(userPreference.getData().getEmailNotification().getTopLevel());
        label1.setText(userPreference.getData().getEmailNotification().getSpecialOffers().getLabel());
        label2.setText(userPreference.getData().getEmailNotification().getWhatsOn().getLabel());
        text1.setText(userPreference.getData().getEmailNotification().getSpecialOffers().getText());
        text2.setText(userPreference.getData().getEmailNotification().getWhatsOn().getText());
        if (userPreference.getData().getEmailNotification().getSpecialOffers().getChecked()) {
            switch1.setChecked(true);
            preferencesManager.setBoolean(Constants.SWITCH1, true);
        } else {
            switch1.setChecked(false);
            preferencesManager.setBoolean(Constants.SWITCH1, false);
        }

        if (userPreference.getData().getEmailNotification().getWhatsOn().getChecked()) {
            switch2.setChecked(true);
            preferencesManager.setBoolean(Constants.SWITCH2, true);
        } else {
            switch2.setChecked(false);
            preferencesManager.setBoolean(Constants.SWITCH2, false);
        }


        eventNotification.setText(userPreference.getData().getEventNotification().getTopLevel());
        label3.setText(userPreference.getData().getEventNotification().getNewEvents().getLabel());
        label4.setText(userPreference.getData().getEventNotification().getClosingEvents().getLabel());
        text3.setText(userPreference.getData().getEventNotification().getNewEvents().getText());
        text4.setText(userPreference.getData().getEventNotification().getClosingEvents().getText());
        if (userPreference.getData().getEventNotification().getNewEvents().getChecked()) {
            preferencesManager.setBoolean(Constants.SWITCH3, true);
            switch3.setChecked(true);
        } else {
            switch3.setChecked(false);
            preferencesManager.setBoolean(Constants.SWITCH3, false);
        }

        if (userPreference.getData().getEventNotification().getClosingEvents().getChecked()) {
            switch4.setChecked(true);
            preferencesManager.setBoolean(Constants.SWITCH4, true);

        } else {
            switch4.setChecked(false);
            preferencesManager.setBoolean(Constants.SWITCH4, false);

        }


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (preferencesManager.getBoolean(Constants.SWITCH1)) {
                    switch1.setChecked(false);
                    preferencesManager.setBoolean(Constants.SWITCH1, false);

                } else {
                    switch1.setChecked(true);
                    preferencesManager.setBoolean(Constants.SWITCH1, true);
                }
            }
        });


        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (preferencesManager.getBoolean(Constants.SWITCH2)) {
                    switch2.setChecked(false);
                    preferencesManager.setBoolean(Constants.SWITCH2, false);
                } else {
                    switch2.setChecked(true);
                    preferencesManager.setBoolean(Constants.SWITCH2, true);
                }
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (preferencesManager.getBoolean(Constants.SWITCH3)) {
                    switch3.setChecked(false);
                    preferencesManager.setBoolean(Constants.SWITCH3, false);


                } else {
                    switch3.setChecked(true);
                    preferencesManager.setBoolean(Constants.SWITCH3, true);

                }
            }
        });

        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (preferencesManager.getBoolean(Constants.SWITCH4)) {
                    switch4.setChecked(false);
                    preferencesManager.setBoolean(Constants.SWITCH4, false);

                } else {
                    switch4.setChecked(true);
                    preferencesManager.setBoolean(Constants.SWITCH4, true);

                }
            }
        });


    }


}
