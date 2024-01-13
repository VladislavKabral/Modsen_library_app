package by.modsen.library_app.service.user;

import by.modsen.library_app.model.user.User;
import by.modsen.library_app.security.LibraryUsersDetails;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LibraryUsersDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public LibraryUsersDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;

        try {
            user = userService.findByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("Could not find employee with email = " + email);
        }

        return new LibraryUsersDetails(user);
    }
}
