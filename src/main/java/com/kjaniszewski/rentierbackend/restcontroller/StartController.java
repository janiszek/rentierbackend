package com.kjaniszewski.rentierbackend.restcontroller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//To set the 'Access-Control-Allow-Origin' in the header
@CrossOrigin
@RestController
@AllArgsConstructor
public class StartController {

    @GetMapping("/")
    public String index() {
        return "Home page of Rentier Backend application. Check /swagger-ui.html#/ for the detailed REST documentation.";
    }
}
