package com.costar.talkwithidol.ui.fragments.channelnews.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.channelnews.NewsFragment;

import dagger.Component;

@NewsScope
@Component(modules = NewsModule.class, dependencies = AppComponent.class)
public interface NewsComponent {

    void inject(NewsFragment newsFragment);
}
