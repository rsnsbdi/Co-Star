package com.costar.talkwithidol.ui.fragments.account.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.account.AccountFragment;

import dagger.Component;

@AccountScope
@Component(modules = AccountModule.class, dependencies = AppComponent.class)
public interface AccountComponent {

    void inject(AccountFragment accountFragment);
}
