package com.antontulskih.app;

import com.antontulskih.domain.Project;
import com.antontulskih.domain.Task;
import com.antontulskih.domain.User;
import com.antontulskih.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Tulskih Anton
 * @{NAME} 30.08.2015
 */
@Component
public class App {

    @Autowired
    static UserService userService;

    public static void main(String[] args) {

        User user = new User("Anton", "Tulskih", "toulskikh@gmail.com",
                "qwerty", "12345");
        user.setUser_id(1L);
        System.out.println(user);

        Project project = new Project("Home", user);
        project.setProject_id(1L);
        System.out.println(project);

        Task task = new Task("Wash dishes", project);
        task.setTask_id(1L);
        task.setIsDone(false);
        System.out.println(task);

        userService.create(user);

    }

}
