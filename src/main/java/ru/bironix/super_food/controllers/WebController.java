package ru.bironix.super_food.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bironix.super_food.converters.DishConverter;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.dish.PortionDto;
import ru.bironix.super_food.services.DishService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/view")
public class WebController {

    @Autowired
    DishController dishController;

    @Autowired
    DishService dishService;

    @Autowired
    DishConverter dishConverter;

    @GetMapping("/")
    public String index(Model model) {

        System.out.println(SpringVersion.getVersion());
        return "view/index";
    }


    @GetMapping("/newAddon")
    public String newAddon(Model model) {
        model.addAttribute("addon", new AddonDto());
        return "view/createAddon/index";
    }

    @PostMapping("/createAddon")
    public String createAddon(@ModelAttribute("addon") @Valid AddonDto addon,
                              BindingResult bindingResult, Model model) throws JsonProcessingException {

        if (!bindingResult.hasErrors()) {
            var createdAddon = dishController.createAddon(addon);
            var createdAddonString = toHumanReadablePage(createdAddon);
            model.addAttribute("createdAddon", createdAddonString);
        }

        return "view/createAddon/index";
    }


    @GetMapping("/newDish")
    public String newDish(Model model) {
        var create = new FullDishDto();

        create.setAddons(dishService.getAllAddons().stream()
                .map(i -> dishConverter.toDto(i))
                .collect(Collectors.toList()));

        create.setDishes(dishService.getAllDishes().stream()
                .map(i -> dishConverter.toSmallDishDto(i))
                .collect(Collectors.toList()));

        model.addAttribute("dish", create);
        model.addAttribute("newPortion", new PortionDto());
        return "view/createDish/index";
    }

    @PostMapping("/createDish")
    public String createDish(@ModelAttribute("dish") @Valid FullDishDto dish, BindingResult dishResult,
                             @ModelAttribute("newPortion") @Valid PortionDto newPortion, BindingResult newPortionResult,
                             Model model) throws JsonProcessingException {

        if (StringUtils.isNotBlank(newPortion.getSize())) {
            if (dish.getPortions() == null) dish.setPortions(new ArrayList<>());
            dish.getPortions().add(newPortion);
            model.addAttribute("newPortion", new PortionDto());
        } else if (!dishResult.hasErrors()) {

            var createdDish = dishService.createDish(dishConverter.fromFullDishDto(dish));
            var createdDishString = toHumanReadablePage(dishConverter.toFullDishDto(createdDish));
            model.addAttribute("createdDish", createdDishString);
        }
        return "view/createDish/index";
    }


    private String toHumanReadablePage(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                .replace("\n", "<br>");
    }
}