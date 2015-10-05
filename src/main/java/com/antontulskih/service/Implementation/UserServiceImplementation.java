package com.antontulskih.service.Implementation;

import com.antontulskih.domain.Project;
import com.antontulskih.domain.User;
import com.antontulskih.persistence.DAO.UserDAO;
import com.antontulskih.service.UserService;
import com.antontulskih.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tulskih Anton
 * @{NAME} 30.08.2015
 */
@Service
public class UserServiceImplementation implements UserService {

    private static final MyLogger LOGGER =
            new MyLogger(UserServiceImplementation.class);

    @Autowired
    UserDAO userDAO;

    @Transactional
    public boolean create(final User user) {
        LOGGER.trace("Inside create()");
        LOGGER.debug("user:\n" + user);
        return userDAO.create(user);
    }

    @Override
    public User retrieveById(final Long id) {
        LOGGER.trace("Inside retrieveById()");
        LOGGER.debug("id is " + id);
        return userDAO.retrieveById(id);
    }

    @Transactional
    public User retrieveByLogin(final String login) {
        LOGGER.trace("Inside retrieveByLogin()");
        LOGGER.debug("login is " + login);
        return userDAO.retrieveByLogin(login);
    }

    @Transactional
    public User retrieveByEmail(final String email) {
        LOGGER.trace("Inside retrieveByEmail()");
        LOGGER.debug("email is " + email);
        return userDAO.retrieveByEmail(email);
    }

    @Transactional
    public User retrieveByLoginAndPassword(final String login,
                                           final String password) {
        LOGGER.trace("Inside retrieveByLoginAndPassword()");
        LOGGER.debug(String.format("login is %s, password is %s",
                login, password));
        return userDAO.retrieveByLoginAndPassword(login, password);
    }

    @Transactional
    public boolean update(final User user) {
        LOGGER.trace("Inside update()");
        LOGGER.debug("user:\n" + user);
        return userDAO.update(user);
    }

    @Transactional
    public boolean delete(final User user) {
        LOGGER.trace("Inside delete()");
        LOGGER.debug("user:\n" + user);
        return userDAO.delete(user);
    }

    @Transactional
    public List<Project> getAllProjects(final User user) {
        LOGGER.trace("Inside getAllProjects()");
        LOGGER.debug("user:\n" + user);
        return userDAO.getAllProjects(user);
    }
}
