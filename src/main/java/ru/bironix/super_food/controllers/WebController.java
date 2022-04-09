package ru.bironix.super_food.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bironix.super_food.dtos.dish.AddonDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/view")
public class WebController {

    @Autowired
    DishController dishController;

    @GetMapping("/")
    public String index(Model model) {
        return "view/index";
    }


    @GetMapping("/newAddon")
    public String createAddon(Model model) {
        model.addAttribute("addon", new AddonDto());
        return "view/createAddon/index";
    }

    @PostMapping("/createAddon")
    public String create(@ModelAttribute("addon") @Valid AddonDto addon,
                         BindingResult bindingResult, Model model) throws JsonProcessingException {

        if (!bindingResult.hasErrors()) {
            var createdAddon = dishController.createAddon(addon);
            var createdAddonString = toHumanReadablePage(createdAddon);
            model.addAttribute("createdAddon", createdAddonString);
        }

        return "view/createAddon/index";
    }


    private String toHumanReadablePage(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                .replace("\n", "<br>");
    }
}