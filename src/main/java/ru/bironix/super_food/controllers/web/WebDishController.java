package ru.bironix.super_food.controllers.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bironix.super_food.controllers.DishController;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.common.PicturePathsDto;
import ru.bironix.super_food.dtos.dish.DishDto;
import ru.bironix.super_food.dtos.dish.PortionDto;
import ru.bironix.super_food.dtos.dish.PriceDto;
import ru.bironix.super_food.services.DishService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.bironix.super_food.controllers.utils.ControllerUtils.toPrettyJsonForHtml;

@Controller
@RequestMapping("/view")
public class WebDishController {

    DishController dishController;
    DishService dishService;
    Converter con;

    @Autowired
    public WebDishController(DishController dishController, DishService dishService, Converter con) {
        this.dishController = dishController;
        this.dishService = dishService;
        this.con = con;
    }

    @GetMapping("/newDish")
    public String newDish(Model model) {
        initModelDefaultData(model);
        return "view/createDish/index";
    }

    @PostMapping("/createDish")
    public String createDish(@ModelAttribute("dish") @Valid DishDto.Create dish, BindingResult dishResult,
                             @ModelAttribute("newPortion") @Valid PortionDto.CreateUpdate newPortion, BindingResult newPortionResult,
                             Model model) throws JsonProcessingException {

        if (StringUtils.isNotBlank(newPortion.getSize())) {
            if (dish.getPortions() == null) dish.setPortions(new ArrayList<>());
            dish.getPortions().add(newPortion);
            model.addAttribute("newPortion", PortionDto.Base.builder().build());
            return "view/createDish/index";
        } else if (dishResult.hasErrors()) return "view/createDish/index";

        var createdDish = dishService.createDish(con.fromDto(dish));
        var createdDishString = toPrettyJsonForHtml(con.toFullDto(createdDish));
        model.addAttribute("createdDish", createdDishString);

        initModelDefaultData(model);
        return "view/createDish/index";
    }


    private void initModelDefaultData(Model model) {
        var create = getMockDishDto();
        create.setAddons(dishService.getAddons().stream()
                .map(i -> con.toDto(i))
                .collect(Collectors.toList()));
        create.setDishes(dishService.getDishes().stream()
                .map(i -> con.toSmallDto(i))
                .collect(Collectors.toList()));

        model.addAttribute("dish", create);
        model.addAttribute("newPortion", PortionDto.Base.builder().build());
    }

    private DishDto.Base.Full getMockDishDto() {
        return DishDto.Base.Full.builder()
                .picturePaths(new PicturePathsDto("https://srokigodnosti.ru/wp-content/uploads/2022/01/191-e1620542426741.jpg"))
                .name("Маргарита")
                .composition("Тесто, куриная грудка, грибы, помидорки, болгарский перчик, соус кисло-сладкий")
                .description("Попробуйте легендарную супер-вкусную пиццу, названную в честь другой вкусной пиццы")
                .allergens("Перец, соус, помидорки")
//                .category(CategoryTypeDto.PIZZA)
                .portions(new ArrayList<>(List.of(PortionDto.Base.builder()
                        .id(null)
                        .size("40 см")
                        .priceNow(PriceDto.Base.builder()
                                .id(null)
                                .price(800)
                                .build())
                        .oldPrice(null)
                        .build()
                )))
                .build();
    }

}
