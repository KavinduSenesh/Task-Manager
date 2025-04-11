package lk.ijse.task_manager.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @NotBlank
    @Size(min = 3, max = 20, message = "User name must be between 3 and 20 characters")
    private String username;
    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
}
