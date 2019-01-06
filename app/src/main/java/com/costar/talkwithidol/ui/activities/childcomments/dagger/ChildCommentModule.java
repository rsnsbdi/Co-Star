package com.costar.talkwithidol.ui.activities.childcomments.dagger;


import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.childcomments.mvp.ChildCommentModel;
import com.costar.talkwithidol.ui.activities.childcomments.mvp.ChildCommentPresenter;
import com.costar.talkwithidol.ui.activities.childcomments.mvp.ChildCommentsView;
import com.costar.talkwithidol.ui.activities.communitydetail.mvp.ListChildCommentAdapter;
import com.costar.talkwithidol.ui.dialog.ReportDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class ChildCommentModule {

    private final AppCompatActivity activity;

    public ChildCommentModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @ChildCommentScope
    @Provides
    public ChildCommentsView childCommentView(ListChildCommentAdapter listChildCommentAdapter, PreferencesManager preferencesManager, ReportDialog reportDialog) {
        return new ChildCommentsView(activity, listChildCommentAdapter, preferencesManager, reportDialog);
    }

    @ChildCommentScope
    @Provides
    public ChildCommentModel childCommentModel(PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        return new ChildCommentModel(activity, padloktNetwork, preferencesManager);
    }

    @ChildCommentScope
    @Provides
    public ChildCommentPresenter childCommentPresenter(ChildCommentsView childCommentsView, ChildCommentModel childCommentModel) {
        return new ChildCommentPresenter(childCommentsView, childCommentModel);
    }

    @ChildCommentScope
    @Provides
    public ListChildCommentAdapter listChildCommentAdapter() {
        return new ListChildCommentAdapter();
    }

    @ChildCommentScope
    @Provides
    public ReportDialog reportDialog() {
        return new ReportDialog(activity);
    }


}
