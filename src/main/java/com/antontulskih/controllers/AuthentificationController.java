package com.antontulskih.controllers;

import com.antontulskih.domain.User;
import com.antontulskih.service.UserService;
import com.antontulskih.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static java.lang.String.format;

/**
 * @author Tulskih Anton
 * @{NAME} 09.08.2015
 */
@Controller
public class AuthentificationController {

    static final MyLogger logger =
            new MyLogger(AuthentificationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("customerValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        logger.trace("Inside initBinder()");
        binder.setValidator(validator);
    }

    @RequestMapping(value = {"", "signIn", "signOut"})
    public String showSignInPage(HttpSession session) {
        logger.trace("Inside showSignInPage()");
        if (session.getAttribute("userId") != null) {
            logger.trace("userId detected in the session. will be removed");
            session.removeAttribute("userId");
            logger.debug("userId now is " + session.getAttribute("userId"));
        }
        return "signIn";
    }

    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    public String signInRequestForm(@RequestParam String login,
                                    @RequestParam String password,
                                    HttpSession session,
                                    Model model) {
        logger.trace("Inside signInRequestForm()");
        logger.debug(format("%nInput login: %s%nInput password: %s",
                login, password));

        User user;
        if ((user = userService.retrieveByLoginAndPassword(login, password))
                != null) {
            logger.debug("User:\n" + user);
            session.setAttribute("userId", user.getUser_id());
            return "redirect:/tasks";
        }
        logger.trace("User with such credentials does not exist, should "
                + "display an error");
        model.addAttribute("errorMessage", "TRUE");
        model.addAttribute("login", login);
        model.addAttribute("password", password);
        return "signIn";
    }

    @RequestMapping(value = "signUp", method = RequestMethod.GET)
    public String signUpInitForm(Model model) {
        logger.trace("Inside signUpInitForm()");
        User user = new User();
        model.addAttribute("signUpForm", user);
        return "signUp";
    }

    @RequestMapping(value = "signUp", method = RequestMethod.POST)
    public String signUpFormValidation(@RequestParam String confirm_password,
                                       HttpSession session,
                                        @ModelAttribute("signUpForm")
                                           @Valid User user,
                                       BindingResult result,
                                       Model model
    ) {
        logger.trace("Inside signUpFormValidation()");
        logger.debug(format(
                        "%nuser.getFirstName(): %s%n"
                        + "user.getLastName(): %s%n"
                        + "user.getEmail(): %s%n"
                        + "user.getLogin(): %s%n"
                        + "user.getPassword(): %s%n"
                        + "confirm_password: %s%n",
                user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getLogin(), user.getPassword(), confirm_password));

        if (result.hasErrors()) {
            logger.trace("Inside result.hasErrors()");
            if (!user.getPassword().equals(confirm_password)) {
                logger.trace("confirm_password doesn't match to the password,"
                        + " produce an error message");
                model.addAttribute("confirmPasswordErrMsg","TRUE");
            }
            return "signUp";
        } else if (!user.getPassword().equals(confirm_password)) {
            logger.trace("confirm_password doesn't match to the password, "
                    + "produce an error message");
            model.addAttribute("confirmPasswordErrMsg","TRUE");
            return "signUp";
        } else {
            logger.trace("User signed up. Redirect to /tables");
            userService.create(user);
            logger.debug(format("User saved in DB. ID - %d",
                    user.getUser_id()));
            session.setAttribute("userId", user.getUser_id());
            logger.debug(format("User ID saved in session. ID - %s",
                    session.getAttribute("userId")));
            return "redirect:/tasks";
        }
    }
}
