package com.costar.talkwithidol.ui.activities.childcomments.dagger;


import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.activities.childcomments.ChildCommentActivity;

import dagger.Component;

@ChildCommentScope
@Component(modules = ChildCommentModule.class, dependencies = AppComponent.class)
public interface ChildCommentComponent {

    void inject(ChildCommentActivity childCommentActivity);
}
