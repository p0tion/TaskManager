package com.antontulskih.service;

import com.antontulskih.domain.Project;
import com.antontulskih.domain.Task;

import java.util.List;

/**
 * @author Tulskih Anton
 * @{NAME} 30.08.2015
 */
public interface ProjectService {

    public boolean create(final Project project);
    public boolean update(final Project project);
    public boolean delete(final Project project);
    public List<Task> getAllTasks(final Project project);
    public Project getById(final Long id);

}
