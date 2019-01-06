package com.costar.talkwithidol.ui.activities.childcomments.mvp;

import com.costar.talkwithidol.app.network.models.ChildCommentsResponse.Datum1;
import com.costar.talkwithidol.app.network.models.ChildCommentsResponse.ListChildCommentResponse;
import com.costar.talkwithidol.app.network.models.ChildReportParams;
import com.costar.talkwithidol.app.network.models.ChildReportid;
import com.costar.talkwithidol.app.network.models.PostComment.AddCommentResponse;
import com.costar.talkwithidol.app.network.models.reportResponse.ReportResponse;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.bus.RxBus;
import com.costar.talkwithidol.ui.activities.childcomments.ChildCommentActivity;
import com.costar.talkwithidol.ui.activities.homepage.HomePageActivity;
import com.costar.talkwithidol.ui.fragments.newslist.EndlessRecyclerViewScrollListener;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import okhttp3.ResponseBody;

/**
 * Created by shreedhar on 11/29/17.
 */

public class ChildCommentPresenter {

    private final ChildCommentsView childCommentsView;
    private final ChildCommentModel childCommentModel;
    ArrayList<Datum1> childCommentList = new ArrayList<>();
    Boolean isFirst = true;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinkedHashMap<String, String> hashMap;
    private DisposableObserver<Timed<Long>> disposableObserver;

    public ChildCommentPresenter(ChildCommentsView childCommentsView, ChildCommentModel childCommentModel) {
        this.childCommentsView = childCommentsView;
        this.childCommentModel = childCommentModel;

    }

    public void onCreate() {
        getChildCommentList();
        childPostReplyClicked();

        RxBus.getInstance().toObservable().subscribe(o -> {
            if (o instanceof ChildReportParams) {
                sendReport((ChildReportParams) o);
            } else if (o instanceof ChildReportid) {
                if (!childCommentsView.activity.isFinishing()) {
                    childCommentsView.showReportDialog((ChildReportid) o);
                }
            }
        });
        isFirst = false;
    }

    private void getChildCommentList() {
        if (isFirst)
            childCommentsView.showLoadingDialog(true);


        DisposableObserver<ListChildCommentResponse> disposableObserver = new DisposableObserver<ListChildCommentResponse>() {
            @Override
            public void onNext(ListChildCommentResponse listChildCommentResponse) {


                if (listChildCommentResponse.getSuccess() && listChildCommentResponse.getData() != null) {

                    childCommentList.clear();
                    childCommentList.addAll(listChildCommentResponse.getData());
                    childCommentsView.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(childCommentsView.layoutManager()) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            getChildCommentList(page);
                        }
                    });
                    childCommentsView.setChildComments(childCommentList);

                } else {

                    if (!listChildCommentResponse.getEmpty())
                        childCommentsView.showMessage(listChildCommentResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    childCommentsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    childCommentsView.showMessage("Please check your network connection");

                } else {
                    childCommentsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                childCommentsView.showLoadingDialog(false);
            }
        };

        childCommentModel.getChildCommentsObservable(0, ChildCommentActivity.videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void getChildCommentList(int page) {


        DisposableObserver<ListChildCommentResponse> disposableObserver = new DisposableObserver<ListChildCommentResponse>() {
            @Override
            public void onNext(ListChildCommentResponse listChildCommentResponse) {


                if (listChildCommentResponse.getSuccess() && listChildCommentResponse.getData() != null && listChildCommentResponse.getData().size() != 0) {

                    childCommentList.addAll(listChildCommentResponse.getData());
                    childCommentsView.setChildComments(childCommentList);
                } else {
                    if (!listChildCommentResponse.getEmpty())
                        childCommentsView.showMessage(listChildCommentResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    childCommentsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    // communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    childCommentsView.showMessage("Please check your network connection");

                } else {
                    childCommentsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
            }
        };

        childCommentModel.getChildCommentsObservable(page, ChildCommentActivity.videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    private void childPostReplyClicked() {

        DisposableObserver<AddCommentResponse> disposableObserver = new DisposableObserver<AddCommentResponse>() {

            @Override
            public void onNext(AddCommentResponse addCommentResponse) {
                if (addCommentResponse.getSuccess()) {

                    childCommentsView.replyPost.getText().clear();
                    childCommentsView.reply.setClickable(true);
                    getChildCommentList();


                } else {
                    childCommentsView.showMessage(addCommentResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    childCommentsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");


                } else if (e instanceof IOException) {
                    childCommentsView.showMessage("Please check your network connection");

                } else {
                    childCommentsView.showMessage(e.getMessage());
                }
                childPostReplyClicked();
            }

            @Override
            public void onComplete() {

            }
        };
        childCommentsView.postReplyCLickObservable()
                .doOnNext(__ -> childCommentsView.showProgressDialog(true))
                .map(__ -> childCommentsView.postCommentParams())
                .observeOn(Schedulers.io())
                .switchMap(childCommentModel::postCommentObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> childCommentsView.showProgressDialog(false))
                .subscribe(disposableObserver);
    }

    //error message return
    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void sendReport(ChildReportParams reportParams) {


        DisposableObserver<ReportResponse> disposableObserver = new DisposableObserver<ReportResponse>() {

            @Override
            public void onNext(ReportResponse reportResponse) {


                if (reportResponse.getSuccess()) {
                    childCommentsView.showMessage(reportResponse.getData().getMessage());
                    getChildCommentList();

                } else {

                    if (reportResponse.getMessage().contains(Constants.AccessDenied)) {
                        HomePageActivity.startLogout(childCommentsView.activity);
                    }
                    childCommentsView.showMessage(reportResponse.getMessage());
                }


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    childCommentsView.showMessage(getErrorMessage(responseBody));

                } else if (e instanceof SocketTimeoutException) {
                    //communityDtailView.showMessage("Time Out");

                } else if (e instanceof IOException) {
                    childCommentsView.showMessage("Please check your network connection");

                } else {
                    childCommentsView.showMessage(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
            }
        };

        childCommentModel.reportObservable(reportParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }


    public void onDestroyView() {
        compositeDisposable.clear();
    }

}
