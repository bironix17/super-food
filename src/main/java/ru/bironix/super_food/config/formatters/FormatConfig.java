package ru.bironix.super_food.config.formatters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.bironix.super_food.config.formatters.*;

@Configuration
public class FormatConfig extends WebMvcConfigurerAdapter {

    private final AddonDtoFormatter addonDtoFormatter;
    private final FullDishDtoFormatter fullDishDtoFormatter;
    private final PortionDtoFormatter portionDtoFormatter;
    private final SmallDishDtoFormatter smallDishDtoFormatter;
    private final AddressDtoFormatter addressDtoFormatter;


    @Autowired
    public FormatConfig(AddonDtoFormatter addonDtoFormatter,
                        FullDishDtoFormatter fullDishDtoFormatter,
                        PortionDtoFormatter portionDtoFormatter,
                        SmallDishDtoFormatter smallDishDtoFormatter,
                        AddressDtoFormatter addressDtoFormatter) {
        this.addonDtoFormatter = addonDtoFormatter;
        this.fullDishDtoFormatter = fullDishDtoFormatter;
        this.portionDtoFormatter = portionDtoFormatter;
        this.smallDishDtoFormatter = smallDishDtoFormatter;
        this.addressDtoFormatter = addressDtoFormatter;
    }

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
