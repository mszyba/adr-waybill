package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.User;
import eu.michalszyba.adrwaybill.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
