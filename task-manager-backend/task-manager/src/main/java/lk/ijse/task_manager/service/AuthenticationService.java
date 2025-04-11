package lk.ijse.task_manager.service;

import lk.ijse.task_manager.dto.UserDTO;
import lk.ijse.task_manager.jwtModels.JwtAuthResponse;
import lk.ijse.task_manager.jwtModels.SignIn;

public interface AuthenticationService {
    JwtAuthResponse signUp(UserDTO user);
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse refreshToken(String refreshToken);
}
