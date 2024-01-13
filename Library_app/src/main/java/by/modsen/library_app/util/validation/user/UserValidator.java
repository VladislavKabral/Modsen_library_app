package by.modsen.library_app.util.validation.user;

import by.modsen.library_app.model.user.User;
import by.modsen.library_app.service.user.UserService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            if (userService.findByEmail(user.getEmail()) != null) {
                errors.rejectValue("email", "", "User with email '" + user.getEmail() +
                        "' already exists");
            }
        } catch (EntityNotFoundException e) {
            //TODO add log
        }
    }
}
