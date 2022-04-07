package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.exception.UserAlreadyExistException;
import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.User;
import eu.michalszyba.adrwaybill.service.CompanyService;
import eu.michalszyba.adrwaybill.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {

    private final UserService userService;
    private final CompanyService companyService;

    public LoginController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @ModelAttribute("companies")
    public List<Company> populateCompanies() {
        return companyService.getAllCompany();
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login/login-form";
    }

    @GetMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login/login-form";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "login/register-form";
    }

    @PostMapping("/register")
    public String registerForm(@Valid User user, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "login/register-form";
            }
            userService.registerNewUserAccount(user);
            return "redirect:/login";
        } catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("emailExist", uaeEx.getMessage());
            return "login/register-form";
        }
    }
}
