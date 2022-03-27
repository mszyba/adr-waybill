package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.Role;
import eu.michalszyba.adrwaybill.model.User;
import eu.michalszyba.adrwaybill.repository.RoleRepository;
import eu.michalszyba.adrwaybill.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        // set default role
        Role userRole = roleRepository.findRoleByRole(DEFAULT_ROLE);
        user.getRoles().add(userRole);

        // set hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // save
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
