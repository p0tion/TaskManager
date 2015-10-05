package com.antontulskih.controllers;

import com.antontulskih.domain.Project;
import com.antontulskih.domain.Task;
import com.antontulskih.domain.User;
import com.antontulskih.service.ProjectService;
import com.antontulskih.service.TaskService;
import com.antontulskih.service.UserService;
import com.antontulskih.util.MyLogger;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Properties;

/**
 * @author Tulskih Anton
 * @{NAME} 09.08.2015
 */
@Controller
public class TasksController {

    private static final MyLogger LOGGER =
            new MyLogger(TasksController.class);

    User user;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public String showTasks(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:signIn";
        } else
            return "tasks";
    }

    @RequestMapping(value = "projects", method = RequestMethod.POST)
    public
    @ResponseBody
    String getAllProjects(HttpServletRequest request) {
        LOGGER.trace("Inside getAllProjects()");
        Long id = (Long) request.getSession().getAttribute("userId");
        User user = userService.retrieveById(id);
        List<Project> projects = userService.getAllProjects(user);
        String gs;
        gs = "{\"user\":" + user.toString() +
             ",\"items\":" + projects.toString() + "}";
        LOGGER.debug("User and list of projects:\n" + gs);
        return gs;
    }

    @RequestMapping(value = "addProject", method = RequestMethod.POST)
    public
    @ResponseBody
    String addProject(@RequestBody String name, HttpServletRequest request) {
        LOGGER.trace("Inside addProject()");
        LOGGER.debug("name is " + name);
        Gson gson = new Gson();
        Project project = gson.fromJson(name, Project.class);
        LOGGER.debug("project is\n" + project.toString());
        Long id = (Long) request.getSession().getAttribute("userId");
        User projectUser = userService.retrieveById(id);
        project.setUser_of_project(projectUser);
        projectService.create(project);
        return project.toString();
    }

    @RequestMapping(value = "deleteProject", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteProject(@RequestBody String projectData) {
        LOGGER.trace("Inside deleteProject()");
        LOGGER.debug("projectData is\n" + projectData);
        Properties data = new Gson().fromJson(projectData, Properties.class);
        Long id = Long.parseLong(data.getProperty("project_id"));
        Project project = projectService.getById(id);
        LOGGER.debug("project is\n" + project.toString());
        projectService.delete(project);
    }

    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    public
    @ResponseBody
    String addTask(@RequestBody String data) {
        LOGGER.trace("Inside addTask()");
        LOGGER.debug("data is\n" + data);
        Gson gson = new Gson();
        Task task = gson.fromJson(data, Task.class);
        LOGGER.debug("task is\n" + task.toString());
        taskService.create(task);
        return task.toString();
    }

    @RequestMapping(value = "deleteTask", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteTask(@RequestBody String taskData) {
        LOGGER.trace("Inside deleteTask()");
        LOGGER.debug("taskData is" + taskData);
        Properties data = new Gson().fromJson(taskData, Properties.class);
        Long id = Long.parseLong(data.getProperty("task_id"));
        Task task = taskService.getById(id);
        LOGGER.debug("task is\n" + task.toString());
        taskService.delete(task);
    }

    @RequestMapping(value = "changeTaskStatus", method = RequestMethod.POST)
    public
    @ResponseBody
    void changeTaskStatus(@RequestBody String id) {
        LOGGER.trace("Inside completeTask()");
        LOGGER.debug("id is " + id);
        Gson gson = new Gson();
        Task task = gson.fromJson(id, Task.class);
        task = taskService.getById(task.getTask_id());
        LOGGER.debug("task is\n" + task.toString());
        task.setIsDone(!task.getIsDone());
        taskService.update(task);
    }

    @RequestMapping(value = "editProject", method = RequestMethod.POST)
    public
    @ResponseBody
    void editProject(@RequestBody String projectData, HttpServletRequest req) {
        LOGGER.trace("Inside editProject()");
        Gson gson = new Gson();
        Project project = gson.fromJson(projectData, Project.class);
        LOGGER.debug("project is\n" + project.toString());
        Long id = (Long) req.getSession().getAttribute("userId");
        User user = userService.retrieveById(id);
        project.setUser_of_project(user);
        projectService.update(project);
    }

    @RequestMapping(value = "editTask", method = RequestMethod.POST)
    public
    @ResponseBody
    void editTask(@RequestBody String taskData) {
        LOGGER.trace("Inside editTask()");
        Properties data = new Gson().fromJson(taskData, Properties.class);
        Long id = Long.parseLong(data.getProperty("task_id"));
        String name = data.getProperty("task_name");
        Task task = taskService.getById(id);
        task.setTask_name(name);
        LOGGER.debug("task is\n" + task.toString());
        taskService.update(task);
    }

    @RequestMapping(value = "getTaskName", method = RequestMethod.POST)
    public
    @ResponseBody
    String getTaskName(@RequestBody String taskData) {
        LOGGER.trace("Inside getTaskName()");
        Properties data = new Gson().fromJson(taskData, Properties.class);
        Long id = Long.parseLong(data.getProperty("task_id"));
        Task task = taskService.getById(id);
        LOGGER.debug("task is\n" + task.toString());
        String qq = "{\"task\":" + task.toString() + "}";
        return qq;
    }

    @RequestMapping(value = "getProjectName", method = RequestMethod.POST)
    public
    @ResponseBody
    String getProjectName(@RequestBody String projectData) {
        LOGGER.trace("Inside getProjectName()");
        Properties data = new Gson().fromJson(projectData, Properties.class);
        Long id = Long.parseLong(data.getProperty("project_id"));
        Project project = projectService.getById(id);
        LOGGER.debug("project is\n" + project.toString());
        String qq = "{\"project\":" + project.toString() + "}";
        return qq;
    }

    @RequestMapping(value = "swapStatus", method = RequestMethod.POST)
    public
    @ResponseBody
    void swapStatus(@RequestBody String taskData) {
        LOGGER.trace("Inside swapStatus()");
        Properties data = new Gson().fromJson(taskData, Properties.class);
        Long taskId1 = Long.parseLong(data.getProperty("id1"));
        Long taskId2 = Long.parseLong(data.getProperty("id2"));
        Task task1 = taskService.getById(taskId1);
        Task task2 = taskService.getById(taskId2);
        int tmp = task1.getTask_priority();
        task1.setTask_priority(task2.getTask_priority());
        task2.setTask_priority(tmp);
        taskService.update(task1);
        taskService.update(task2);
    }
}
