package ru.bironix.super_food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AddonDtoFormatter addonDtoFormatter;

    @Autowired
    private FullDishDtoFormatter fullDishDtoFormatter;

    @Autowired
    private PortionDtoFormatter portionDtoFormatter;
    @Autowired
    private SmallDishDtoFormatter smallDishDtoFormatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(addonDtoFormatter);
        registry.addFormatter(fullDishDtoFormatter);
        registry.addFormatter(portionDtoFormatter);
        registry.addFormatter(smallDishDtoFormatter);
    }
    
}
