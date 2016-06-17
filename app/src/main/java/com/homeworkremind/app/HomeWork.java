package com.homeworkremind.app;

/**
 * Created by Clixin on 2016/6/16.
 */
public class HomeWork {

    private String deadline;
    private String wayOfHandOn;
    private String course;
    private String content;

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getWayOfHandOn() {
        return wayOfHandOn;
    }

    public void setWayOfHandOn(String wayOfHandOn) {
        this.wayOfHandOn = wayOfHandOn;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HomeWork(String deadline, String course, String wayOfHandOn, String content) {
        this.deadline = deadline;
        this.course = course;
        this.wayOfHandOn = wayOfHandOn;
        this.content = content;
    }
}
