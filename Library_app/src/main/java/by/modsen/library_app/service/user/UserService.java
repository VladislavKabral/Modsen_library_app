package by.modsen.library_app.service.user;

import by.modsen.library_app.model.user.User;
import by.modsen.library_app.repository.user.UserRepository;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        return user.orElseThrow(EntityNotFoundException.entityNotFoundException("User with email '" +
                email + "' not found"));
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}