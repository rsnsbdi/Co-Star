package com.costar.talkwithidol.ui.fragments.home.mvp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ui.activities.eventdetail.EventDtailActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by dell on 1/14/2018.
 */

public class TalkWithFragment extends Fragment {

    private int imageResId;
    public  View viewChanged;
    public  View rootView;
    RelativeLayout linearLayout = null;
    public static Activity activity;
   public  String id, name,image;

    public static TalkWithFragment newInstance(Activity activity1,String eventId, String eventName, String imageUrl) {


        Bundle args = new Bundle();
        args.putString("eventId", eventId);
        args.putString("eventName", eventName);
        args.putString("imageUrl", imageUrl);

        TalkWithFragment fragment = new TalkWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ultra_item, container, false);
        rootView.setScaleY(0.8f);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            name = getArguments().getString("eventName");
            id = getArguments().getString("eventId");
            image = getArguments().getString("imageUrl");

            TextView eventtitle = rootView.findViewById(R.id.tv_channel_name);
            ImageView eventImage = rootView.findViewById(R.id.iv_channel);
            RelativeLayout rl_detail = rootView.findViewById(R.id.rl_detail);
            viewChanged = rootView.findViewById(R.id.overlay);


            try {
                eventtitle.setText(name);
                Picasso.with(getActivity()).load(image).into(eventImage);
                rl_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            Intent i = new Intent(getActivity(), EventDtailActivity.class);
                            i.putExtra("VIDEOID", id);
                            getActivity().startActivity(i);

                        } catch (Exception e) {

                        }

                    }
                });

            } catch (Exception e) {

            }


        }catch(Exception e){

        }
    }

    public void scaleImage(float scaleX)
    {
        try {
            rootView.setScaleY(scaleX);
            rootView.invalidate();
        }catch (Exception e){

        }
    }

    public void setSelected(boolean isSelected){
        try {
            if (!isSelected){
                viewChanged.setForeground(getResources().getDrawable(R.drawable.channel_image_gradient_blue));
            }else{
                viewChanged.setForeground(getResources().getDrawable(R.drawable.channel_image_gradient));
            }
        }catch (Exception e){

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }
}
