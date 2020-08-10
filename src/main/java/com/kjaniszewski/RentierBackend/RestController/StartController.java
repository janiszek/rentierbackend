package com.kjaniszewski.RentierBackend.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StartController {

    @GetMapping("/")
    public String index() {
        return "Home page of Rentier Backend application. Check /swagger-ui.html#/ for the detailed REST documentation.";
    }
}
