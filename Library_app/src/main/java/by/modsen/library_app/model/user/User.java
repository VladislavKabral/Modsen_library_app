package by.modsen.library_app.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    @Email(message = "Field Email must have email format")
    @NotEmpty(message = "Email must be not empty")
    @NonNull
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password must be not empty")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 symbols")
    @NonNull
    private String password;
}
