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
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.services.PersonService;

import javax.validation.Valid;

import static ru.bironix.super_food.controllers.utils.ControllerUtils.toPrettyJsonForHtml;

@Controller
@RequestMapping("/view")
public class WebPersonController {

    PersonService personService;
    Converter con;

    @Autowired
    public WebPersonController(PersonService personService, Converter con) {
        this.personService = personService;
        this.con = con;
    }

    @GetMapping("/newPerson")
    public String newPerson(Model model) {
        initModelDefaultData(model);
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
        var createdPersonString = toPrettyJsonForHtml(con.toDto(createdPerson));
        model.addAttribute("createdPerson", createdPersonString);

        initModelDefaultData(model);

        return "view/createPerson/index";
    }

    private void initModelDefaultData(Model model) {
        var create = getMockPersonDto();
        model.addAttribute("person", create);
        model.addAttribute("newAddress", new AddressDto());
    }

    private PersonDto getMockPersonDto() {
        return PersonDto.builder()
                .email("hello@rambler.ru")
                .password("Kotopes777")
                .name("Саня")
                .build();
    }
}
