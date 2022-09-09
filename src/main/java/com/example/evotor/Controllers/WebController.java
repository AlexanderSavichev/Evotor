package com.example.evotor.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/evotor")
    public String evotor() {return "evotor";}
}
