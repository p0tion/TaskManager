package com.antontulskih.persistence.Implementation;

import com.antontulskih.domain.Project;
import com.antontulskih.domain.User;
import com.antontulskih.persistence.DAO.UserDAO;
import com.antontulskih.util.MyLogger;
import com.antontulskih.util.PasswordEncryptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tulskih Anton
 * @{NAME} 25.08.2015
 */
@Repository
public class User_DAO_Implementation implements UserDAO {

    private static final MyLogger LOGGER =
            new MyLogger(User_DAO_Implementation.class);
    private static final String ROLLBACK_ERROR_MESSAGE =
            "Couldn’t roll back transaction";

    @Autowired
    private SessionFactory sf;

    @Override
    public boolean create(final User user) {
        LOGGER.trace("Inside create()");
        LOGGER.debug("User is:\n" + user);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            user.setPassword(
                    PasswordEncryptor.getCryptString(user.getPassword()));
            user.setUser_id((Long) session.save(user));
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_ERROR_MESSAGE, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.debug("User saved, ID = " + user.getUser_id());
        return true;
    }

    @Override
    public User retrieveById(final Long id) {
        LOGGER.trace("Inside retrieveById()");
        LOGGER.debug("id is " + id);
        Session session = null;
        Transaction transaction = null;
        User user = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User where id = :id")
                    .setLong("id", id);
            user = (User) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re){
                LOGGER.error(ROLLBACK_ERROR_MESSAGE, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.debug("Returning user:\n" + user);
        return user;
    }

    @Override
    public User retrieveByLogin(final String login) {
        LOGGER.trace("Inside retrieveByLogin()");
        LOGGER.debug("login is " + login);
        Session session = null;
        Transaction transaction = null;
        User user = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User where login = :login")
                    .setString("login", login);
            user = (User) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re){
                LOGGER.error(ROLLBACK_ERROR_MESSAGE, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.debug("Returning user:\n" + user);
        return user;
    }

    @Override
    public User retrieveByEmail(final String email) {
        LOGGER.trace("Inside retrieveByEmail()");
        LOGGER.debug("email is " + email);
        Session session = null;
        Transaction transaction = null;
        User user = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User where email = "
                    + ":email")
                    .setString("email", email);
            user = (User) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re){
                LOGGER.error(ROLLBACK_ERROR_MESSAGE, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.debug("Returning user:\n" + user);
        return user;
    }

    @Override
    public User retrieveByLoginAndPassword(final String login,
                                           final String password) {
        LOGGER.trace("Inside retrieveByLoginAndPassword()");
        LOGGER.debug(String.format("login is %s, password is %s",
                login, password));
        Session session = null;
        Transaction transaction = null;
        User user = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User where login = "
                    + ":login and password = :password")
                    .setString("login", login)
                    .setString("password",
                            PasswordEncryptor.getCryptString(password));
            user = (User) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re){
                LOGGER.error(ROLLBACK_ERROR_MESSAGE, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.debug("Returning user:\n" + user);
        return user;
    }

    @Override
    public boolean update(final User user) {
        LOGGER.trace("Inside update()");
        LOGGER.debug("user is:\n" + user);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re){
                LOGGER.error(ROLLBACK_ERROR_MESSAGE, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.debug("User updated");
        return true;
    }

    @Override
    public boolean delete(final User user) {
        LOGGER.trace("Inside delete()");
        LOGGER.debug("user is:\n" + user);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re){
                LOGGER.error(ROLLBACK_ERROR_MESSAGE, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.debug("User removed");
        return true;
    }

    @Override
    public List<Project> getAllProjects(final User user) {
        LOGGER.trace("Inside getAllProjects()");
        LOGGER.debug("user is:\n" + user);
        Session session = null;
        Transaction transaction = null;
        List<Project> list;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Project where "
                    + "user_of_project.user_id = :user_id order by project_id "
                    + "desc").setLong("user_id", user.getUser_id());
            list = (List<Project>) query.list();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re){
                LOGGER.error(ROLLBACK_ERROR_MESSAGE, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.debug("Returning List of projects: \n" + list.toString());
        return list;
    }
}
