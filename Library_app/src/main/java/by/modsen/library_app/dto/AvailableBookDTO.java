package by.modsen.library_app.dto;

import by.modsen.library_app.model.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableBookDTO {

    @JsonBackReference(value = "available_books")
    @NotNull(message = "Book must be not null")
    private Book book;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
