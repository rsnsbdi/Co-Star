package com.costar.talkwithidol.ui.fragments.newslist.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.newslist.NewsListFragment;

import dagger.Component;

@NewsScope
@Component(modules = NewsModule.class, dependencies = AppComponent.class)
public interface NewsComponent {

    void inject(NewsListFragment newsListFragment);
}
