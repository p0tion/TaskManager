package com.antontulskih.util;


import com.antontulskih.domain.User;
import com.antontulskih.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tulskih Anton
 * @{NAME} 30.08.2015
 */
public class UserValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        Pattern pattern = Pattern.compile("[A-Za-z]{2,15}");
        Matcher matcher = pattern.matcher(user.getFirstName());
        if (user.getFirstName().trim().equals("")) {
            errors.rejectValue("firstName", "validator.empty.firstName");
        } else if (!matcher.matches()) {
            errors.rejectValue("firstName", "validator.pattern.firstName");
        }

        pattern = Pattern.compile("[A-Za-z]{2,15}");
        matcher = pattern.matcher(user.getLastName());
        if (user.getLastName().trim().equals("")) {
            errors.rejectValue("lastName", "validator.empty.lastName");
        } else if (!matcher.matches()) {
            errors.rejectValue("lastName", "validator.pattern.lastName");
        }

        pattern =Pattern.compile(
                "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
        matcher = pattern.matcher(user.getEmail());
        if (user.getEmail().trim().equals("")) {
            errors.rejectValue("email", "validator.empty.email");
        } else if (!matcher.matches()) {
            errors.rejectValue("email", "validator.pattern.email");
        } else if (userService.retrieveByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "validator.alreadyExists.email");
        }

        pattern = Pattern.compile("[A-Za-z0-9_]{2,15}");
        matcher = pattern.matcher(user.getLogin());
        if (user.getLogin().trim().equals("")) {
            errors.rejectValue("login", "validator.empty.login");
        } else if (!matcher.matches()) {
            errors.rejectValue("login", "validator.pattern.login");
        } else if (userService.retrieveByLogin(user.getLogin()) != null) {
            errors.rejectValue("login", "validator.alreadyExists.login");
        }

        pattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}");
        matcher = pattern.matcher(user.getPassword());
        if (user.getPassword().trim().equals("")) {
            errors.rejectValue("password", "validator.empty.password");
        } else if (!matcher.matches()) {
            errors.rejectValue("password", "validator.pattern.password");
        }
    }
}
