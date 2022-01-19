package no.ntnu.hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST API controller which responds to HTTP requests for /hello
 */
@RestController
public class GreetingController {

    @GetMapping("/hello")
    public String greeting() {
        return "Hello, World";
    }

    @GetMapping("/hei")
    public ResponseEntity<String> heiGetResponse() {
        return new ResponseEntity<String>(HttpStatus.MOVED_PERMANENTLY);
    }

    @DeleteMapping("/hello")
    public ResponseEntity<String> helloDeleteResponse(){
        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }
}

