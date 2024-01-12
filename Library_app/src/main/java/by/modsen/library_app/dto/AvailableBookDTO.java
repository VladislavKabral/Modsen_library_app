package by.modsen.library_app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity of an available book")
public class AvailableBookDTO {

    @NotNull(message = "Book must be not null")
    @Schema(description = "Entity of the book from the library")
    private BookDTO book;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Date, when a reader should return the book to the library", example = "2024-03-27")
    private LocalDate endDate;
}
