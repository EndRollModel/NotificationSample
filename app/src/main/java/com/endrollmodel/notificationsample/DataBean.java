package com.endrollmodel.notificationsample;

import android.graphics.Bitmap;

public class DataBean{
    private String channelId;
    private String groupId;
    private String title;
    private String content;
    private String depiction;
    private Bitmap bigPic;

    public DataBean(String channelId, String groupId, String title, String content) {
        this.channelId = channelId;
        this.groupId = groupId;
        this.title = title;
        this.content = content;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDepiction() {
        return depiction;
    }

    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    public Bitmap getBigPic() {
        return bigPic;
    }

    public void setBigPic(Bitmap bigPic) {
        this.bigPic = bigPic;
    }
}
