package by.modsen.library_app.dto;

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

    @NotNull(message = "Book must be not null")
    private BookDTO book;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
