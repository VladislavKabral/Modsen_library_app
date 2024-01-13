package by.modsen.library_app.model.book;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "lastname")
    @NotEmpty(message = "Author's lastname must be not empty")
    @Size(min = 2, max = 50, message = "Author's lastname must be between 2 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Author's lastname must contain only letters")
    private String lastname;

    @Column(name = "firstname")
    @NotEmpty(message = "Author's firstname must be not empty")
    @Size(min = 2, max = 50, message = "Author's firstname must be between 2 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Author's firstname must contain only letters")
    private String firstname;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference(value = "author_books")
    private List<Book> books;
}
