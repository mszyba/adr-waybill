package eu.michalszyba.adrwaybill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = {"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping(path = { "/index"})
    public String index() {
        return "index";
    }
}
