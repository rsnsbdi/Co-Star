package com.costar.talkwithidol.ui.activities.childcomments.mvp;

import android.support.v7.app.AppCompatActivity;

import com.costar.talkwithidol.app.network.PadloktNetwork;
import com.costar.talkwithidol.app.network.models.ChildCommentsResponse.ListChildCommentResponse;
import com.costar.talkwithidol.app.network.models.ChildReportParams;
import com.costar.talkwithidol.app.network.models.PostComment.AddCommentResponse;
import com.costar.talkwithidol.app.network.models.PostComment.PostCommentParams;
import com.costar.talkwithidol.app.network.models.reportResponse.ReportResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;

import io.reactivex.Observable;

/**
 * Created by shreedhar on 11/29/17.
 */

public class ChildCommentModel {

    private final PadloktNetwork padloktNetwork;
    private AppCompatActivity activity;
    private PreferencesManager preferencesManager;

    public ChildCommentModel(AppCompatActivity activity, PadloktNetwork padloktNetwork, PreferencesManager preferencesManager) {
        this.activity = activity;
        this.padloktNetwork = padloktNetwork;
        this.preferencesManager = preferencesManager;

    }

    public Observable<ListChildCommentResponse> getChildCommentsObservable(int page, String id) {
        String url = Constants.CHILD_COMMENTS + "/" + id + "/children" + "?page=" + page;
        return padloktNetwork.getChildComments(url, preferencesManager.get(Constants.COOKIE));
    }

    public Observable<ReportResponse> reportObservable(ChildReportParams reportParams) {
        return padloktNetwork.reportChild(reportParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

    public Observable<AddCommentResponse> postCommentObservable(PostCommentParams postCommentParams) {
        return padloktNetwork.postComment(postCommentParams, preferencesManager.get(Constants.TOKEN), preferencesManager.get(Constants.COOKIE));
    }

}
