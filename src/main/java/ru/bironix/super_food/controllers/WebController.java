package ru.bironix.super_food.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.AddressDto;
import ru.bironix.super_food.dtos.PersonDto;
import ru.bironix.super_food.dtos.PicturePathsDto;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.services.ActionService;
import ru.bironix.super_food.services.DishService;
import ru.bironix.super_food.services.PersonService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//TODO подумать о выборе использования чего то одного. Контроллеров или сервисов.
@Controller
@RequestMapping("/view")
public class WebController {

    @Autowired
    DishController dishController;

    @Autowired
    DishService dishService;

    @Autowired
    ActionService actionService;

    @Autowired
    PersonService personService;

    @Autowired
    Converter con;

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
            var createdAddon = dishService.createAddon(con.fromDto(addon));
            var createdAddonString = toHumanReadablePage(con.toDto(createdAddon));
            model.addAttribute("createdAddon", createdAddonString);
        }

        return "view/createAddon/index";
    }


    @GetMapping("/newDish")
    public String newDish(Model model) {
        var create = FullDishDto.builder()
                .picturePaths(new PicturePathsDto("https://srokigodnosti.ru/wp-content/uploads/2022/01/191-e1620542426741.jpg"))
                .name("Маргарита")
                .composition("Тесто, куриная грудка, грибы, помидорки, болгарский перчик, соус кисло-сладкий")
                .description("Попробуйте легендарную супер-вкусную пиццу, названную в честь другой вкусной пиццы")
                .allergens("Перец, соус, помидорки")
                .category(CategoryType.PIZZA)
                .portions(new ArrayList<>(List.of(new PortionDto(null, "40 см", new PriceDto(null, 800), null))))
                .build();

        create.setAddons(dishService.getAllAddons().stream()
                .map(i -> con.toDto(i))
                .collect(Collectors.toList()));

        create.setDishes(dishService.getAllDishes().stream()
                .map(i -> con.toSmallDto(i))
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
            return "view/createDish/index";
        } else if (dishResult.hasErrors()) return "view/createDish/index";

        var createdDish = dishService.createDish(con.fromFullDto(dish));
        var createdDishString = toHumanReadablePage(con.toFullDto(createdDish));
        model.addAttribute("createdDish", createdDishString);

        //TODO вынести в отдельный метод
        dish = FullDishDto.builder()
                .picturePaths(new PicturePathsDto("https://srokigodnosti.ru/wp-content/uploads/2022/01/191-e1620542426741.jpg"))
                .name("Маргарита")
                .composition("Тесто, куриная грудка, грибы, помидорки, болгарский перчик, соус кисло-сладкий")
                .description("Попробуйте легендарную супер-вкусную пиццу, названную в честь другой вкусной пиццы")
                .allergens("Перец, соус, помидорки")
                .category(CategoryType.PIZZA)
                .portions(new ArrayList<>(List.of(new PortionDto(null, "40 см", new PriceDto(null, 800), null))))
                .build();

        dish.setAddons(dishService.getAllAddons().stream()
                .map(i -> con.toDto(i))
                .collect(Collectors.toList()));

        dish.setDishes(dishService.getAllDishes().stream()
                .map(i -> con.toSmallDto(i))
                .collect(Collectors.toList()));

        model.addAttribute("dish", dish);
        model.addAttribute("newPortion", new PortionDto());

        return "view/createDish/index";
    }




    @GetMapping("/newAction")
    public String newAction(Model model) {
        var create = FullActionDto.builder()
                .picturePaths(new PicturePathsDto("https://sun9-34.userapi.com/impg/mKg7k8pedbc7XqJvUTG66AOMgSG-TzVSVLqORQ/2NCmTa9jO-c.jpg?size=604x515&quality=95&sign=d7134a8fda78aec413ad851f4ecd64f5&type=album"))
                .name("4 пиццы по цене двух роллов, по размерам такие же")
                .build();

        create.setDishes(dishService.getAllDishes().stream()
                .map(i -> con.toSmallDto(i))
                .collect(Collectors.toList()));

        model.addAttribute("action", create);
        model.addAttribute("newPrice", 750);
        return "view/createAction/index";
    }


    @PostMapping("/createAction")
    public String createDish(@ModelAttribute("action") @Valid FullActionDto action, BindingResult actionResult,
                             @RequestParam(name = "newPrice") Integer newPrice,
                             Model model) throws JsonProcessingException {

        if (newPrice < 0) return "view/createAction/index";
        if (actionResult.hasErrors()) return "view/createAction/index";

        var createdAction = actionService.createAction(con.fromFullDto(action), newPrice);
        var createdDishString = toHumanReadablePage(con.toFullDto(createdAction));
        model.addAttribute("createdAction", createdDishString);

        //TODO вынести в отдельный метод
        var create = FullActionDto.builder()
                .picturePaths(new PicturePathsDto("https://sun9-34.userapi.com/impg/mKg7k8pedbc7XqJvUTG66AOMgSG-TzVSVLqORQ/2NCmTa9jO-c.jpg?size=604x515&quality=95&sign=d7134a8fda78aec413ad851f4ecd64f5&type=album"))
                .name("4 пиццы по цене двух роллов, по размерам такие же")
                .build();


        create.setDishes(dishService.getAllDishes().stream()
                .map(i -> con.toSmallDto(i))
                .collect(Collectors.toList()));

        model.addAttribute("action", create);
        model.addAttribute("newPrice", 750);


        return "view/createAction/index";
    }




    @GetMapping("/newPerson")
    public String newPerson(Model model) {
        var create = PersonDto.builder()
                .email("hello@rambler.ru")
                .password("Kotopes777")
                .name("Саня")
                .build();

        model.addAttribute("person", create);
        model.addAttribute("newAddress", new AddressDto());
        return "view/createPerson/index";
    }



    @PostMapping("/createPerson")
    public String createUser(@ModelAttribute("person") @Valid PersonDto person, BindingResult personResult,
                             @ModelAttribute("newAddress") @Valid AddressDto newAddress, BindingResult newAddressResult,
                             Model model) throws JsonProcessingException {

        if (StringUtils.isNotBlank(newAddress.getAddress())) {

            person.getAddresses().add(newAddress);
            model.addAttribute("newAddress", new AddressDto());
            return "view/createPerson/index";

        } else if (personResult.hasErrors()) return "view/createPerson/index";

        var createdPerson = personService.createPerson(con.fromDto(person));
        var createdPersonString = toHumanReadablePage(con.toDto(createdPerson));
        model.addAttribute("createdPerson", createdPersonString);

        //TODO вынести в отдельный метод
        var create = PersonDto.builder()
                .email("hello@rambler.ru")
                .password("Kotopes777")
                .name("Саня")
                .build();


        model.addAttribute("person", create);
        model.addAttribute("newAddress", new AddressDto());

        return "view/createPerson/index";
    }




    private String toHumanReadablePage(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                .replace("\n", "<br>");
    }

}