package com.antontulskih.persistence.Implementation;

import com.antontulskih.domain.Project;
import com.antontulskih.domain.Task;
import com.antontulskih.persistence.DAO.ProjectDAO;
import com.antontulskih.util.MyLogger;
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
public class Project_DAO_Implementation implements ProjectDAO {

    private static final MyLogger LOGGER =
            new MyLogger(Project_DAO_Implementation.class);
    private static final String ROLLBACK_ERROR_MESSAGE =
            "Couldn’t roll back transaction";

    @Autowired
    private SessionFactory sf;

    @Override
    public boolean create(final Project project) {
        LOGGER.trace("Inside create()");
        LOGGER.debug("project is:\n" + project);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            project.setProject_id((Long) session.save(project));
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
        LOGGER.debug("Project saved, ID = " + project.getProject_id());
        return true;
    }

    @Override
    public boolean delete(final Project project) {
        LOGGER.trace("Inside delete()");
        LOGGER.debug("project is:\n" + project);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Task where "
                    + "project_of_task = :project")
                    .setParameter("project", project);
            query.executeUpdate();
            query = session.createQuery("delete from Project where "
                    + "project_id = :project_id")
                    .setLong("project_id", project.getProject_id());
            query.executeUpdate();
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
        LOGGER.debug("Project removed");
        return true;
    }

    @Override
    public boolean update(final Project project) {
        LOGGER.trace("Inside update()");
        LOGGER.debug("project is:\n" + project);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.update(project);
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
        LOGGER.debug("Project updated");
        return true;
    }

    @Override
    public List<Task> getAllTasks(final Project project) {
        LOGGER.trace("Inside getAllTasks()");
        LOGGER.debug("project is:\n" + project);
        Session session = null;
        Transaction transaction = null;
        List<Task> list;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Task where "
                    + "project_of_task.project_id = "
                    + ":project_id order by task_priority")
                    .setLong("project_id", project.getProject_id());
            list = (List<Task>) query.list();
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

    @Override
    public Project getById(final Long id) {
        LOGGER.trace("Inside getById()");
        LOGGER.debug("id is:" + id);
        Session session = null;
        Transaction transaction = null;
        Project project;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Project where "
                    + "project_id = :project_id")
                    .setLong("project_id", id);
            project = (Project) query.uniqueResult();
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
        LOGGER.debug("Returning project:\n" + project);
        return project;
    }
}
