package ru.bironix.super_food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.bironix.super_food.config.formatters.*;

@Configuration
public class FormatConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AddonDtoFormatter addonDtoFormatter;

    @Autowired
    private FullDishDtoFormatter fullDishDtoFormatter;

    @Autowired
    private PortionDtoFormatter portionDtoFormatter;

    @Autowired
    private SmallDishDtoFormatter smallDishDtoFormatter;

    @Autowired
    private AddressDtoFormatter addressDtoFormatter;


    //TODO Изучить как это сделать через DI
    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(addonDtoFormatter);
        registry.addFormatter(fullDishDtoFormatter);
        registry.addFormatter(portionDtoFormatter);
        registry.addFormatter(smallDishDtoFormatter);
        registry.addFormatter(addressDtoFormatter);
    }
    
}
