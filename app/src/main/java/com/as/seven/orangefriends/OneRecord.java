package com.as.seven.orangefriends;

/**
 * Created by sf Zhang on 2016/12/20.
 */
public class OneRecord {
    private String id;
    private String point;
    private String content;
    private String milestone;
    private String createtime;
    private String updatetime;

    public OneRecord(String id, String point, String content, String milestone, String createtime, String updatetime) {
        this.id = id;
        this.point = point;
        this.content = content;
        this.milestone = milestone;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
