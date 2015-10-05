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
public class User implements Serializable {

    private static final MyLogger LOGGER = new MyLogger(User.class);

    @Expose
    private Long user_id;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String email;
    @Expose
    private String login;
    private String password;
    private List<Project> projects;

    public User() { }

    public User(final String firstName,
                final String lastName,
                final String email,
                final String login,
                final String password) {
        LOGGER.trace("Inside User constructor");
        LOGGER.debug(String.format("firstName is %s, lastName is %s, email is"
                        + " %s, login is %s, password is %s", firstName, lastName,
                email, login, password));
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = password;
        this.projects = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getUser_id() {
        LOGGER.trace("Inside getUser_id()");
        LOGGER.debug("Returning user_id = " + user_id);
        return user_id;
    }

    public void setUser_id(final Long user_id) {
        LOGGER.trace("Inside setUser_id()");
        LOGGER.debug("Setting user_id = " + user_id);
        this.user_id = user_id;
    }

    public String getFirstName() {
        LOGGER.trace("Inside getFirstName()");
        LOGGER.debug("Returning firstname = " + firstName);
        return firstName;
    }

    public void setFirstName(final String firstName) {
        LOGGER.trace("Inside setFirstName()");
        LOGGER.debug("Setting firstname = " + firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        LOGGER.trace("Inside getLastName()");
        LOGGER.debug("Returning lastname = " + lastName);
        return lastName;
    }

    public void setLastName(final String lastName) {
        LOGGER.trace("Inside setLastName()");
        LOGGER.debug("Setting lastname = " + lastName);
        this.lastName = lastName;
    }

    public String getEmail() {
        LOGGER.trace("Inside getEmail()");
        LOGGER.debug("Returning email = " + email);
        return email;
    }

    public void setEmail(final String email) {
        LOGGER.trace("Inside setEmail()");
        LOGGER.debug("Setting email = " + email);
        this.email = email;
    }

    public String getLogin() {
        LOGGER.trace("Inside getLogin()");
        LOGGER.debug("Returning login = " + login);
        return login;
    }

    public void setLogin(final String login) {
        LOGGER.trace("Inside setLogin()");
        LOGGER.debug("Setting login = " + login);
        this.login = login;
    }

    public String getPassword() {
        LOGGER.trace("Inside getPassword()");
        LOGGER.debug("Returning password = " + password);
        return password;
    }

    public void setPassword(final String password) {
        LOGGER.trace("Inside setPassword()");
        LOGGER.debug("Setting password = " + password);
        this.password = password;
    }

    @OneToMany(mappedBy = "user_of_project", fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH)
    public List<Project> getProjects() {
        LOGGER.trace("Inside getProjects()");
        return projects;
    }

    public void setProjects(final List<Project> projects) {
        LOGGER.trace("Inside setProjects()");
        this.projects = projects;
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
        System.out.println(user);

    }

}
