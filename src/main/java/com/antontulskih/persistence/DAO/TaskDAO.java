package com.antontulskih.persistence.DAO;

import com.antontulskih.domain.Task;

/**
 * @author Tulskih Anton
 * @{NAME} 25.08.2015
 */
public interface TaskDAO {

    public boolean create(final Task task);
    public boolean update(final Task task);
    public boolean delete(final Task task);
    public Task getById(final Long id);
}
