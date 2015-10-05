package com.antontulskih.service.Implementation;

import com.antontulskih.domain.Task;
import com.antontulskih.persistence.DAO.TaskDAO;
import com.antontulskih.service.TaskService;
import com.antontulskih.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Tulskih Anton
 * @{NAME} 30.08.2015
 */
@Service
public class TaskServiceImplementation implements TaskService {

    private static final MyLogger LOGGER =
            new MyLogger(TaskServiceImplementation.class);

    @Autowired
    TaskDAO taskDAO;

    @Transactional
    public boolean create(final Task task) {
        LOGGER.trace("Inside create()");
        LOGGER.debug("task:\n" + task);
        return taskDAO.create(task);
    }

    @Transactional
    public boolean update(final Task task) {
        LOGGER.trace("Inside update()");
        LOGGER.debug("task:\n" + task);
        return taskDAO.update(task);
    }

    @Transactional
    public boolean delete(final Task task) {
        LOGGER.trace("Inside delete()");
        LOGGER.debug("task:\n" + task);
        return taskDAO.delete(task);
    }

    @Override
    public Task getById(final Long id) {
        LOGGER.trace("Inside getById()");
        LOGGER.debug("id is:" + id);
        return taskDAO.getById(id);
    }
}
