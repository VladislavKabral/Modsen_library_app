package by.modsen.library_app.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginCredentials {

    @Email(message = "Field Email must have email format")
    @NotEmpty(message = "Email must be not empty")
    private String email;

    @NotEmpty(message = "Password must be not empty")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 symbols")
    private String password;
}
