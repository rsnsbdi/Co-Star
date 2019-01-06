package com.costar.talkwithidol.ui.fragments.notification.dagger;



import com.costar.talkwithidol.app.dagger.AppComponent;
import com.costar.talkwithidol.ui.fragments.notification.NotificationFragment;

import dagger.Component;

@NotificationScope
@Component(modules = NotificationModule.class, dependencies = AppComponent.class)
public interface NotificationComponent {

    void inject(NotificationFragment notificationFragment);
}
