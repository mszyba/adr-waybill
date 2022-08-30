package eu.michalszyba.adrwaybill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/states")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public ResponseEntity<String> customerList(@RequestParam(value = "q", required = false) String term) {
        List<Customer> strings = customerService.getAutocomplete(term);

        ObjectMapper objectMapper = new ObjectMapper();
        String resp = "";

        try {
            resp = objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("==================+++++");
        System.out.println(strings);
        System.out.println("==================+++++====");
        System.out.println(resp);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
