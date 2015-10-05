package com.antontulskih.domain;

import com.antontulskih.util.MyLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Tulskih Anton
 * @{NAME} 25.08.2015
 */
@Entity
public class Task implements Serializable {

    private static final MyLogger LOGGER = new MyLogger(Task.class);

    @Expose
    private Long task_id;
    @Expose
    private String task_name;
    @Expose
    private boolean isDone;
    @Expose
    private int task_priority;
    private Project project_of_task;

    public Task() { }

    public Task(final String task_name, final Project project_of_task) {
        LOGGER.trace("Inside Task constructor");
         LOGGER.debug(String.format("task_name is %s, project_of_task is %s",
                 task_name, project_of_task.getProject_name()));
        this.task_name = task_name;
        this.project_of_task = project_of_task;
        this.isDone = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getTask_id() {
        LOGGER.trace("Inside getUser_id()");
        LOGGER.debug("Returning task_id = " + task_id);
        return task_id;
    }

    public void setTask_id(final Long task_id) {
        LOGGER.trace("Inside setUser_id()");
        LOGGER.debug("Setting task_id = " + task_id);
        this.task_id = task_id;
    }

    public String getTask_name() {
        LOGGER.trace("Inside getTask_name()");
        LOGGER.debug("Returning task_name = " + task_name);
        return task_name;
    }

    public void setTask_name(final String task_name) {
        LOGGER.trace("Inside setTask_name()");
        LOGGER.debug("Setting task_name = " + task_name);
        this.task_name = task_name;
    }

    public int getTask_priority() {
        LOGGER.trace("Inside getTask_priority()");
        LOGGER.debug("Returning task_priority = " + task_priority);
        return task_priority;
    }

    public void setTask_priority(final int task_priority) {
        LOGGER.trace("Inside setTask_priority()");
        LOGGER.debug("Setting task_priority = " + task_priority);
        this.task_priority = task_priority;
    }

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject_of_task() {
        LOGGER.trace("Inside getProject_of_task()");
        LOGGER.debug("Returning project_of_task:\n" + project_of_task);
        return project_of_task;
    }

    public void setProject_of_task(final Project project_of_task) {
        LOGGER.trace("Inside setProject_of_task()");
        LOGGER.debug("Setting project_of_task = "
                + project_of_task.getProject_name());
        this.project_of_task = project_of_task;
    }

    public boolean getIsDone() {
        LOGGER.trace("Inside getTask_status()");
        LOGGER.debug("Returning isDone = " + isDone);
        return isDone;
    }

    public void setIsDone(final boolean isDone) {
        LOGGER.trace("Inside setTask_status()");
        LOGGER.debug("Setting isDone = " + isDone);
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public static void main(String[] args) {

        User user = new User("Anton", "Tulskih", "toulskikh@gmail.com",
                "qwerty", "12345");
        user.setUser_id(1L);

        Project project = new Project("Home", user);
        project.setProject_id(1L);

        Task task = new Task("Wash dishes", project);
        task.setTask_id(1L);
        task.setIsDone(false);
        System.out.println(task);

    }

}
