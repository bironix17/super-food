package ru.bironix.super_food.controllers.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.dish.PriceDto;
import ru.bironix.super_food.services.DishService;

import static ru.bironix.super_food.controllers.utils.ControllerUtils.toPrettyJsonForHtml;

@Controller
@RequestMapping("/view")
public class WebAddonController {

    Converter con;
    DishService dishService;

    @Autowired
    public WebAddonController(Converter con, DishService dishService) {
        this.con = con;
        this.dishService = dishService;
    }

    @GetMapping("/newAddon")
    public String newAddon(Model model) {
        initModelDefaultData(model);
        return "view/createAddon/index";
    }

    @PostMapping("/createAddon")
    public String createAddon(@ModelAttribute("addon") AddonDto.CreateUpdate addon,
                              BindingResult bindingResult, Model model) throws JsonProcessingException {

        if (!bindingResult.hasErrors()) {
            var createdAddon = dishService.createAddon(con.fromDto(addon));
            var createdAddonString = toPrettyJsonForHtml(con.toDto(createdAddon));
            model.addAttribute("createdAddon", createdAddonString);
        }

        initModelDefaultData(model);

        return "view/createAddon/index";
    }

    private void initModelDefaultData(Model model) {
        var create = getMockAddonDto();
        model.addAttribute("addon", create);
    }

    private AddonDto.Base getMockAddonDto() {
        return AddonDto.Base.builder()
                .picturePath("https://s1.eda.ru/StaticContent/Photos/120131111936/140715165635/p_O.jpg")
                .name("Картошка Фри")
                .price(PriceDto.Base.builder()
                        .id(null)
                        .price(20)
                        .build()
                )
                .build();
    }
}
