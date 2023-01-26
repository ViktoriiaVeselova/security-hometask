package com.epam.hometasksecurity.controller;

import com.epam.hometasksecurity.security.brutforcedefence.LoginAttemptService;
import com.epam.hometasksecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ApplicationController {

    @Autowired
    LoginAttemptService loginAttemptService;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = {"/info"})
    @PreAuthorize("hasRole('VIEW_INFO')")
    public ModelAndView getInfoPage(Model model) {
        long count = userRepository.count();
        model.addAttribute("userCount", count);
        return new ModelAndView("index");
    }

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/logout-success")
    public String getLogoutPage(Model model) {
        return "logout";
    }

    @GetMapping(value = "/about")
    public String getAboutPage(Model model) {
        return "about";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('VIEW_ADMIN')")
    public String getAdminPage(Model model) {
        return "admin";
    }

    @GetMapping(value = "/blocked-users")
    @PreAuthorize("hasRole('VIEW_ADMIN')")
    public ModelAndView getBlockedUsers(Model model) {
        model.addAttribute("users", loginAttemptService.getBlockedUsers());
        return new ModelAndView("blocked-users");
    }
}
