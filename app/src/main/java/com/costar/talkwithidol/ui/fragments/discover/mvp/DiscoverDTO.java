package com.costar.talkwithidol.ui.fragments.discover.mvp;

/**
 * Created by dell on 8/28/2017.
 */

public class DiscoverDTO {

    private String title;
    private String desc;
    private int image;


    public DiscoverDTO(){

    }

    public  DiscoverDTO(String title,String desc, int image){
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }



}
