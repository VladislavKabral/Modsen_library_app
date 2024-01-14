package by.modsen.library_app.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Credentials for registration or log in")
public class LoginCredentialsDTO {

    @Email(message = "Field Email must have email format")
    @NotEmpty(message = "Email must be not empty")
    @Schema(description = "User's email address", example = "mr.ivanov@mail.ru")
    private String email;

    @NotEmpty(message = "Password must be not empty")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 symbols")
    @Schema(description = "User's password", example = "Fdvsv5646svsvs678sv")
    private String password;
}
