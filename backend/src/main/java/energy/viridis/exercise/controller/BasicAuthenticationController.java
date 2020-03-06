package energy.viridis.exercise.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import energy.viridis.exercise.dto.AuthenticationDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@CrossOrigin(origins={ "http://localhost:3000", "http://localhost:4200" })
@RestController
public class BasicAuthenticationController {

    @GetMapping(path = "/basicauth")
    public AuthenticationDTO basicauth() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return new AuthenticationDTO("You are authenticated");
    }   
}