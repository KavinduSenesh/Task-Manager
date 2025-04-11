package lk.ijse.task_manager.service.impl;

import lk.ijse.task_manager.dto.UserDTO;
import lk.ijse.task_manager.entity.User;
import lk.ijse.task_manager.jwtModels.JwtAuthResponse;
import lk.ijse.task_manager.jwtModels.SignIn;
import lk.ijse.task_manager.mapper.ModelMapper;
import lk.ijse.task_manager.repository.UserRepository;
import lk.ijse.task_manager.service.AuthenticationService;
import lk.ijse.task_manager.service.JWTService;
import lk.ijse.task_manager.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.yaml.snakeyaml.nodes.NodeId.mapping;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final Mapping mapping;

    @Override
    public JwtAuthResponse signUp(UserDTO userDTO) {
        var user = mapping.map(userDTO, User.class);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        userRepository.save(user);
        var generatedToken = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword()));
        var user = userRepository.findByUsername(signIn.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        var generatedToken = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JwtAuthResponse refreshToken(String refreshToken) {
        var userName = jwtService.extractUserName(refreshToken);
        var userEntity =
                userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var newToken = jwtService.refreshToken(userEntity);
        return JwtAuthResponse.builder().token(newToken).build();
    }
}
