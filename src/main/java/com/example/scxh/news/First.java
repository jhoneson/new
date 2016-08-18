package com.example.scxh.news;

import java.util.ArrayList;

/**
 * Created by scxh on 2016/8/11.
 */
public class First {
    private String title;
    private ArrayList<Imagextra> imgextra;
    private ArrayList<HeadNews> ads;
    private String imgsrc;
    private String ptime;
    private String url_3w;
    private String postid;

    public ArrayList<HeadNews> getAds() {
        return ads;
    }

    public void setAds(ArrayList<HeadNews> ads) {
        this.ads = ads;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public ArrayList getImgextra() {
        return imgextra;
    }


    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    private String digest;

    public void setImgextra(ArrayList<Imagextra> imgextra) {
        this.imgextra = imgextra;
    }

    public String getUrl_3w() {
        return url_3w;
    }

    public void setUrl_3w(String url_3w) {
        this.url_3w = url_3w;
    }

    private String url;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
