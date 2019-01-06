package com.costar.talkwithidol.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.network.models.ChangePassword.ChangePasswordParams;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public class ChangePasswordDialog {

    LikeClickInterface likeClickInterface;
    Dialog dialog;
    Activity activity;
    public Button send;
    public  EditText oldPassword, newPassword, confirmPassword;
    ImageView close;

    public ChangePasswordDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.change_password);
        send = dialog.findViewById(R.id.submit);
        oldPassword = dialog.findViewById(R.id.old_password);
        newPassword = dialog.findViewById(R.id.new_password);
        confirmPassword = dialog.findViewById(R.id.confirm_password);
        close = dialog.findViewById(R.id.iv_close);
        close.setOnClickListener(v->{
            dialog.dismiss();
        });

        send.setOnClickListener(v->{
            if (isChangePasswordFormFieldsValid()){
                likeClickInterface.changePasswordClicked();
            }
        });


    }

    public interface LikeClickInterface {
        void changePasswordClicked();
    }

    public void getPasswordClicked(LikeClickInterface likeClickInterface) {
        this.likeClickInterface = likeClickInterface;
    }

    public void showDialog(boolean doShow) {
        if (doShow) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
            dialog.dismiss();
    }


    public ChangePasswordParams changePasswordParams() {
        return ChangePasswordParams.builder().currentPassword(oldPassword.getText().toString().trim()).password(newPassword.getText().toString().trim()).build();

    }

    private boolean isChangePasswordFormFieldsValid() {
        boolean isFormFieldsValid = true;
        if (oldPassword.getText().toString().trim().length() == 0) {
            oldPassword.setError("This field is required");
            isFormFieldsValid = false;
        }
        if (newPassword.getText().toString().trim().length() == 0) {
            isFormFieldsValid = false;
            newPassword.setError("This field is required");
        } else if (newPassword.getText().toString().trim().length() < 8) {
            isFormFieldsValid = false;
            newPassword.setError("Please have a minimum of eight characters.");
        }
        else if (!newPassword.getText().toString().trim().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})")){
            isFormFieldsValid = false;
            newPassword.setError("Please have at least one capital letter.");
        }

        if (confirmPassword.getText().toString().trim().length() == 0) {
            isFormFieldsValid = false;
            confirmPassword.setError("This field is required");
        } else if (confirmPassword.getText().toString().trim().length() < 8) {
            isFormFieldsValid = false;
            confirmPassword.setError("Please have a minimum of eight characters.");
        }else if (!confirmPassword.getText().toString().trim().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})")){
            isFormFieldsValid = false;
            confirmPassword.setError("Please have at least one capital letter.");
        }
        if (!newPassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
            isFormFieldsValid = false;
            confirmPassword.setError("Passwords do not match");
        }

        return isFormFieldsValid;

    }

//    public Flowable<CharSequence> oldpasswwordCharSequenceFlowable() {
//        return RxTextView.textChanges(oldPassword).skip(1).toFlowable(BackpressureStrategy.LATEST);
//    }
//
//    public Flowable<CharSequence> newpasswordCharSequenceFlowable() {
//        return RxTextView.textChanges(newPassword).skip(1).toFlowable(BackpressureStrategy.LATEST);
//    }
//
//    public Flowable<CharSequence> confirmPasswordCharSequenceFlowable() {
//        return RxTextView.textChanges(confirmPassword).skip(1).toFlowable(BackpressureStrategy.LATEST);
//    }

    public void setOldPasswordError(String error) {
        oldPassword.setError(error);
    }

    public void setNewPasswordError(String error) {
        newPassword.setError(error);
    }



    public void setConfirmPasswordError(String error) {
        confirmPassword.setError(error);
    }

    public Observable<Object> changePasswordObservable() {
        return RxView.clicks(send);
    }


}
