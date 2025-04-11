package lk.ijse.task_manager.service.impl;

import lk.ijse.task_manager.repository.UserRepository;
import lk.ijse.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
