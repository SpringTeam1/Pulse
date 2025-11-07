package com.test.pulse.controller.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {

    @GetMapping("/example")
    public String example() {
        // Tiles의 definition name = "example"
    	
        return "example.example";
    }
    
    @GetMapping("/boardExample")
    public String boardExample() {
        // Tiles의 definition name = "example"
    	
        return "example.boardExample";
    }
    
}