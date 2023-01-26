package com.epam.hometasksecurity.security;

import com.epam.hometasksecurity.exception.UserBlockedException;
import com.epam.hometasksecurity.security.brutforcedefence.LoginAttemptService;
import com.epam.hometasksecurity.model.User;
import com.epam.hometasksecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MyUserDetailsService implements UserDetailsService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new UserBlockedException("blocked");
        }

        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("cannot find username: " + username));
        return new UserPrincipal(user);
    }


    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
