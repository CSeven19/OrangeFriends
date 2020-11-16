package com.as.seven.orangefriends;

/**
 * Created by sf Zhang on 2016/12/20.
 */
public class OneMemo {
    private String id;
    private int tag;
    private String textDate;
    private String textTime;
    private boolean alarm;
    private String mainText;

    public OneMemo(String id, int tag, String textDate, String textTime, boolean alarm, String mainText) {
        this.id = id;
        this.tag = tag;
        this.textDate = textDate;
        this.textTime = textTime;
        this.alarm = alarm;
        this.mainText = mainText;
    }

    //getter
    public int getTag() {
        return tag;
    }

    public String getId() {
        return id;
    }

    public String getTextDate() {
        return textDate;
    }

    public String getTextTime() {
        return textTime;
    }

    public boolean getAlarm() {
        return alarm;
    }

    public String getMainText() {
        return mainText;
    }

    //setter
    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setTag(String id) {
        this.id = id;
    }

    public void setTextDate(String textDate) {
        this.textDate = textDate;
    }

    public void setTextTime(String textTime) {
        this.textTime = textTime;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }
}
