package id.ac.ui.cs.advprog.udehnihh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomePageController {

    @GetMapping("/homepage")
    public String homePage() {
        return "HomePage";
    }
}