package com.homeworkremind.app;

/**
 * 作业类，包装了作业的属性
 * Created by Clixin on 2016/6/16.
 */
public class Homework {

    private static int id = 10000;
    /**
     * 截止日期
     */
    private String deadline;
    /**
     * 提交方式
     */
    private String wayOfHandOn;
    /**
     * 课程名称
     */
    private String course;
    /**
     * 作业要求
     */
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Homework(int id, String deadline, String course, String wayOfHandOn, String content) {
        this.id = id;
        this.deadline = deadline;
        this.course = course;
        this.wayOfHandOn = wayOfHandOn;
        this.content = content;

    }

    public Homework(String deadline, String course, String wayOfHandOn, String content) {
        this.deadline = deadline;
        this.course = course;
        this.wayOfHandOn = wayOfHandOn;
        this.content = content;
        id ++;
    }

    public Homework() {
        id ++;
    }
}
