package ru.bironix.super_food.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class WebRootController {


    @GetMapping("/")
    public String index(Model model) {
        return "view/index";
    }

}