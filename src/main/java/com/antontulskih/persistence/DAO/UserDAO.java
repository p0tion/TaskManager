package com.antontulskih.persistence.DAO;

import com.antontulskih.domain.Project;
import com.antontulskih.domain.User;

import java.util.List;

/**
 * @author Tulskih Anton
 * @{NAME} 25.08.2015
 */
public interface UserDAO {

    public boolean create(final User user);
    public User retrieveById(final Long id);
    public User retrieveByLogin(final String login);
    public User retrieveByEmail(final String email);
    public User retrieveByLoginAndPassword(final String login,
                                           final String password);
    public boolean update(final User user);
    public boolean delete(final User user);
    public List<Project> getAllProjects(final User user);
}
