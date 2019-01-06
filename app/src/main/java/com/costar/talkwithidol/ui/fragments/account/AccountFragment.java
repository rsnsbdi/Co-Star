package com.costar.talkwithidol.ui.fragments.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.ui.fragments.account.dagger.AccountModule;
import com.costar.talkwithidol.ui.fragments.account.dagger.DaggerAccountComponent;
import com.costar.talkwithidol.ui.fragments.account.mvp.AccountPresenter;
import com.costar.talkwithidol.ui.fragments.account.mvp.AccountView;
import com.costar.talkwithidol.ui.fragments.setting.SettingFragment;


import javax.inject.Inject;


public class AccountFragment extends Fragment {

    @Inject
    AccountView accountView;

    @Inject
    AccountPresenter accountPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerAccountComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .accountModule(new AccountModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);

        SettingFragment.type = "account";
        accountPresenter.onCreateView();
        return accountView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();

        accountPresenter.onCreateView();
    }
}
