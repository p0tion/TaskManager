package com.antontulskih.service.Implementation;

import com.antontulskih.domain.Project;
import com.antontulskih.domain.Task;
import com.antontulskih.persistence.DAO.ProjectDAO;
import com.antontulskih.service.ProjectService;
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
public class ProjectServiceImplementation implements ProjectService {

    private static final MyLogger LOGGER =
            new MyLogger(TaskServiceImplementation.class);

    @Autowired
    ProjectDAO projectDAO;

    @Transactional
    public boolean create(final Project project) {
        LOGGER.trace("Inside create()");
        LOGGER.debug("project:\n" + project);
        return projectDAO.create(project);
    }

    @Transactional
    public boolean update(final Project project) {
        LOGGER.trace("Inside update()");
        LOGGER.debug("project:\n" + project);
        return projectDAO.update(project);
    }

    @Transactional
    public boolean delete(final Project project) {
        LOGGER.trace("Inside delete()");
        LOGGER.debug("project:\n" + project);
        return projectDAO.delete(project);
    }

    @Transactional
    public List<Task> getAllTasks(final Project project) {
        LOGGER.trace("Inside getAllTasks()");
        LOGGER.debug("task:\n" + project);
        return projectDAO.getAllTasks(project);
    }

    @Override
    public Project getById(final Long id) {
        LOGGER.trace("Inside getById()");
        LOGGER.debug("id:" + id);
        return projectDAO.getById(id);
    }
}
