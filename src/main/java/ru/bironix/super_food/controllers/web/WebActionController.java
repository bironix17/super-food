package ru.bironix.super_food.controllers.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.action.ActionDto;
import ru.bironix.super_food.dtos.common.PicturePathsDto;
import ru.bironix.super_food.dtos.dish.DishDto;
import ru.bironix.super_food.dtos.dish.PortionDto;
import ru.bironix.super_food.dtos.dish.PriceDto;
import ru.bironix.super_food.services.ActionService;
import ru.bironix.super_food.services.DishService;
import ru.bironix.super_food.store.db.models.dish.Dish;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static ru.bironix.super_food.controllers.utils.ControllerUtils.toPrettyJsonForHtml;

@Controller
@RequestMapping("/view")
public class WebActionController {

    DishService dishService;
    ActionService actionService;
    Converter con;

    @Autowired
    public WebActionController(DishService dishService, ActionService actionService, Converter con) {
        this.dishService = dishService;
        this.actionService = actionService;
        this.con = con;
    }

    @GetMapping("/newAction")
    public String newAction(Model model) {
        initModelDefaultData(model);
        return "view/createAction/index";
    }


    @PostMapping("/createAction")
    public String createDish(@ModelAttribute("action") @Valid ActionDto.CreateUpdate action, BindingResult actionResult,
                             @RequestParam(name = "newPrice") Integer newPrice,
                             Model model) throws JsonProcessingException {

        if (newPrice < 0) return "view/createAction/index";
        if (actionResult.hasErrors()) return "view/createAction/index";

        var dishesDb = dishService.getDishes(action.getDishes().stream()
                .map(d -> d.getId())
                .collect(toSet()));

        action.setPortions(dishesDb.stream()
                .map(Dish::getBasePortion)
                .map(p -> PortionDto.CreateUpdateForAction.builder()
                        .id(p.getId())
                        .price(PriceDto.CreateUpdate.builder()
                                .price(newPrice)
                                .build())
                        .build())
                .collect(toList()));

        var createdAction = actionService.createAction(con.fromDto(action));
        var createdDishString = toPrettyJsonForHtml(con.toFullDto(createdAction));
        model.addAttribute("createdAction", createdDishString);

        initModelDefaultData(model);

        return "view/createAction/index";
    }

    private void initModelDefaultData(Model model) {
        var create = getMockFullActionDto();

        create.setDishes(dishService.getDishes().stream()
                .map(i -> con.toSmallDto(i))
                .collect(toList()));

        model.addAttribute("action", create);
        model.addAttribute("newPrice", 750);
    }

    private ActionDto.Base.Full getMockFullActionDto() {
        return ActionDto.Base.Full.builder()
                .picturePaths(new PicturePathsDto("https://sun9-34.userapi.com/impg/mKg7k8pedbc7XqJvUTG66AOMgSG-TzVSVLqORQ/2NCmTa9jO-c.jpg?size=604x515&quality=95&sign=d7134a8fda78aec413ad851f4ecd64f5&type=album"))
                .name("4 пиццы по цене двух роллов, по размерам такие же")
                .build();
    }
}
