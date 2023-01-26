package com.epam.hometasksecurity.controller;

import com.epam.hometasksecurity.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Random;

@Controller
public class SecretController {

    private static final String URL = "/one-time-link/";
    private static final Random random = new Random();

    @Autowired
    SecretService secretService;


    @GetMapping(value = "/secret")
    @PreAuthorize("hasRole('STANDARD')")
    public String getSecretPage(Model model) {
        return "secret";
    }

    @PostMapping(value = "/secret")
    @PreAuthorize("hasRole('STANDARD')")
    public ModelAndView provideSecret(HttpServletRequest request, Model model) {
        var secret = Optional.ofNullable(request.getParameter("secret")).orElseThrow();
        var hash = getHash(secret);
        var oneTimeUrl = getOneTimeLink(request, hash);

        secretService.addSecret(secret, hash);

        model.addAttribute("link", oneTimeUrl);
        return new ModelAndView("link");
    }

    @GetMapping(value = "/secret/one-time-link/{hash}")
    @PreAuthorize("hasRole('STANDARD')")
    public ModelAndView getSecretPage(Model model, @PathVariable String hash) {
        model.addAttribute("secret", secretService.getSecret(hash));
        return new ModelAndView("your-secret");
    }


    private String getHash(String secret) {
        int nextInt = random.nextInt(1, 10000000);
        String newString = secret + nextInt;
        return String.valueOf(newString.hashCode()).replace("-", "");
    }

    private String getOneTimeLink(HttpServletRequest request, String hash) {
        return request.getRequestURL().append(URL).append(hash).toString();
    }
}
