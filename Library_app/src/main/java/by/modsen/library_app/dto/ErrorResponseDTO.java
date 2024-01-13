package by.modsen.library_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Entity of a response with an error")
public class ErrorResponseDTO {

    @Schema(description = "Message with description of the error", example = "Book's ISBN must be not empty")
    private String message;

    @Schema(description = "The time, when the error occurred", example = "2024-03-27T13:15:42.411")
    private LocalDateTime time;
}
