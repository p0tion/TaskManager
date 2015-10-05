package com.antontulskih.domain;

import com.antontulskih.util.MyLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tulskih Anton
 * @{NAME} 25.08.2015
 */
@Entity
public class Project implements Serializable {

    private static final MyLogger LOGGER = new MyLogger(Project.class);

    @Expose
    private Long project_id;
    @Expose
    private String project_name;
    @Expose
    private List<Task> tasks;
    private User user_of_project;

    public Project() { }

    public Project(final String project_name, final User user_of_project) {
        LOGGER.trace("Inside Project constructor");
        LOGGER.debug(String.format("project_name is %s, user_of_project is %s",
                project_name, user_of_project.getFirstName() + " " +
                        user_of_project.getLastName()));
        this.project_name = project_name;
        this.user_of_project = user_of_project;
        this.tasks = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getProject_id() {
        LOGGER.trace("Inside getUser_id()");
        LOGGER.debug("Returning project_id = " + project_id);
        return project_id;
    }

    public void setProject_id(final Long project_id) {
        LOGGER.trace("Inside setUser_id()");
        LOGGER.debug("Setting project_id = " + project_id);
        this.project_id = project_id;
    }

    public String getProject_name() {
        LOGGER.trace("Inside getProject_name()");
        LOGGER.debug("Returning project_name = " + project_name);
        return project_name;
    }

    public void setProject_name(final String project_name) {
        LOGGER.trace("Inside setProject_name()");
        LOGGER.debug("Setting project_name = " + project_name);
        this.project_name = project_name;
    }

    @OneToMany(mappedBy = "project_of_task", fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH)
    @OrderBy("task_priority")
    public List<Task> getTasks() {
        LOGGER.trace("Inside getTasks()");
        return tasks;
    }

    public void setTasks(final List<Task> tasks) {
        LOGGER.trace("Inside setTasks()");
        this.tasks = tasks;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser_of_project() {
        LOGGER.trace("Inside getUser_of_project()");
        LOGGER.debug("Returning user_of_project: \n" + user_of_project);
        return user_of_project;
    }

    public void setUser_of_project(final User user_of_project) {
        LOGGER.trace("Inside setUser_of_project()");
        LOGGER.debug("Setting user_of_project: \n" + user_of_project);
        this.user_of_project = user_of_project;
    }

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
        System.out.println(project);

    }

}
