package com.antontulskih.service;

import com.antontulskih.domain.Task;

/**
 * @author Tulskih Anton
 * @{NAME} 30.08.2015
 */
public interface TaskService {

    public boolean create(final Task task);
    public boolean update(final Task task);
    public boolean delete(final Task task);
    public Task getById(final Long id);

}
