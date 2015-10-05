package com.antontulskih.persistence.Implementation;

import com.antontulskih.domain.Task;
import com.antontulskih.persistence.DAO.TaskDAO;
import com.antontulskih.util.MyLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Tulskih Anton
 * @{NAME} 30.08.2015
 */
@Repository
public class Task_DAO_Implementation implements TaskDAO {

    private static final MyLogger LOGGER =
            new MyLogger(Task_DAO_Implementation.class);
    private static final String ROLLBACK_ERROR_MESSAGE =
            "Couldn’t roll back transaction";

    @Autowired
    SessionFactory sf;

    @Override
    public boolean create(final Task task) {
        LOGGER.trace("Inside create()");
        LOGGER.debug("task is:\n" + task);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            task.setTask_id((Long) session.save(task));
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
        LOGGER.debug("Task saved, ID = " + task.getTask_id());
        return true;
    }

    @Override
    public boolean update(final Task task) {
        LOGGER.trace("Inside update()");
        LOGGER.debug("task is:\n" + task);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.update(task);
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
        LOGGER.debug("Task updated");
        return true;
    }

    @Override
    public boolean delete(final Task task) {
        LOGGER.trace("Inside delete()");
        LOGGER.debug("task is:\n" + task);
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.delete(task);
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
        LOGGER.debug("Task removed");
        return true;
    }

    @Override
    public Task getById(final Long id) {
        LOGGER.trace("Inside getById()");
        LOGGER.debug("id is:" + id);
        Session session = null;
        Transaction transaction = null;
        Task task;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Task where "
                    + "task_id = :task_id")
                    .setLong("task_id", id);
            task = (Task) query.uniqueResult();
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
        LOGGER.debug("Returning task:\n" + task);
        return task;
    }
}
