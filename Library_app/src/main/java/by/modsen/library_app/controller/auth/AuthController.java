package by.modsen.library_app.controller.auth;

import by.modsen.library_app.controller.Url;
import by.modsen.library_app.dto.ErrorResponseDTO;
import by.modsen.library_app.dto.user.UserDTO;
import by.modsen.library_app.model.user.LoginCredentials;
import by.modsen.library_app.model.user.User;
import by.modsen.library_app.security.JWTUtil;
import by.modsen.library_app.service.user.UserService;
import by.modsen.library_app.util.exception.EntityValidateException;
import by.modsen.library_app.util.exception.InvalidParamException;
import by.modsen.library_app.util.validation.user.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(Url.Auth.PATH)
@Tag(name = "AuthController", description = "Allows to login and register in the service")
public class AuthController {

    private static final String SECURITY_REQUIREMENT_NAME = "JWT";

    private final UserService userService;

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authManager;

    private final UserValidator userValidator;

    @Autowired
    public AuthController(UserService userService,
                          JWTUtil jwtUtil,
                          AuthenticationManager authManager, UserValidator userValidator) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userValidator = userValidator;
    }

    @PostMapping(Url.Auth.REGISTER)
    @Operation(
            summary = "Registration in the service",
            description = "Allows to register in the service by email and password"
    )
    @SecurityRequirement(name = SECURITY_REQUIREMENT_NAME)
    public ResponseEntity<UserDTO> registerHandler(@RequestBody @Valid @Parameter(description = "Register credentials",
            required = true) LoginCredentials loginCredentials, BindingResult bindingResult)
            throws EntityValidateException {

        User user = new User(loginCredentials.getEmail(), loginCredentials.getPassword());
        userValidator.validate(user, bindingResult);
        handleBindingResult(bindingResult);
        userService.save(user);

        String token = jwtUtil.generateToken(loginCredentials.getEmail());
        return new ResponseEntity<>(new UserDTO(token), HttpStatus.OK);
    }

    @PostMapping(Url.Auth.LOGIN)
    @Operation(
            summary = "Log in to the service",
            description = "Allows to log in to the service by email and password"
    )
    @SecurityRequirement(name = SECURITY_REQUIREMENT_NAME)
    public ResponseEntity<UserDTO> loginHandler(@RequestBody @Valid @Parameter(description = "Login credentials",
            required = true) LoginCredentials body, BindingResult bindingResult)
            throws InvalidParamException, EntityValidateException {

        handleBindingResult(bindingResult);

        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

        } catch (BadCredentialsException e){
            throw new InvalidParamException("Invalid credentials.");
        }

        String token = jwtUtil.generateToken(body.getEmail());

        return new ResponseEntity<>(new UserDTO(token), HttpStatus.OK);
    }

    private void handleBindingResult(BindingResult bindingResult) throws EntityValidateException {
        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new EntityValidateException(message.toString());
        }
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }
}
