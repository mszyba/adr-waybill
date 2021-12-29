package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.User;
import eu.michalszyba.adrwaybill.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("users")
    public List<User> populateUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/list")
    public String listUsers() {
        return "user/user-list";
    }
}
