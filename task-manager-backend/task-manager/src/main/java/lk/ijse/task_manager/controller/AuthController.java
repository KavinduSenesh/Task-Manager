package lk.ijse.task_manager.controller;

import jakarta.validation.Valid;
import lk.ijse.task_manager.dto.UserDTO;
import lk.ijse.task_manager.jwtModels.JwtAuthResponse;
import lk.ijse.task_manager.jwtModels.SignIn;
import lk.ijse.task_manager.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody UserDTO userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        JwtAuthResponse jwtAuthResponse = authenticationService.signUp(userDto);
        System.out.println("Sign-up endpoint hit!");
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthResponse> signIn(@Valid @RequestBody UserDTO userDto){
        JwtAuthResponse jwtAuthResponse = authenticationService.signIn(new SignIn(userDto.getUsername(), userDto.getPassword()));
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.refreshToken(refreshToken));
    }

}
