package ru.bironix.super_food.config.formatters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.bironix.super_food.dtos.order.OrderStatusDto;

@Configuration
public class FormatConfig extends WebMvcConfigurerAdapter {

    private final BindAddonDtoFormatter bindAddonDtoFormatter;
    private final FullDishDtoFormatter fullDishDtoFormatter;
    private final PortionDtoFormatter portionDtoFormatter;
    private final SmallDishDtoFormatter smallDishDtoFormatter;
    private final AddressDtoFormatter addressDtoFormatter;
    private final BaseAddonDtoFormatter baseAddonDtoFormatter;
    private final BindDishDtoFormatter bindDishDtoFormatter;
    private final CreateUpdatePortionDtoFormatter createUpdatePortionDtoFormatter;
    private final OrderStatusDtoFormatter orderStatusDtoFormatter;

    @Autowired
    public FormatConfig(BindAddonDtoFormatter bindAddonDtoFormatter,
                        FullDishDtoFormatter fullDishDtoFormatter,
                        PortionDtoFormatter portionDtoFormatter,
                        SmallDishDtoFormatter smallDishDtoFormatter,
                        AddressDtoFormatter addressDtoFormatter,
                        BaseAddonDtoFormatter baseAddonDtoFormatter,
                        BindDishDtoFormatter bindDishDtoFormatter,
                        CreateUpdatePortionDtoFormatter createUpdatePortionDtoFormatter,
                        OrderStatusDtoFormatter orderStatusDtoFormatter) {
        this.bindAddonDtoFormatter = bindAddonDtoFormatter;
        this.fullDishDtoFormatter = fullDishDtoFormatter;
        this.portionDtoFormatter = portionDtoFormatter;
        this.smallDishDtoFormatter = smallDishDtoFormatter;
        this.addressDtoFormatter = addressDtoFormatter;
        this.baseAddonDtoFormatter = baseAddonDtoFormatter;
        this.bindDishDtoFormatter = bindDishDtoFormatter;
        this.createUpdatePortionDtoFormatter = createUpdatePortionDtoFormatter;
        this.orderStatusDtoFormatter = orderStatusDtoFormatter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(bindAddonDtoFormatter);
        registry.addFormatter(fullDishDtoFormatter);
        registry.addFormatter(portionDtoFormatter);
        registry.addFormatter(smallDishDtoFormatter);
        registry.addFormatter(addressDtoFormatter);
        registry.addFormatter(baseAddonDtoFormatter);
        registry.addFormatter(bindDishDtoFormatter);
        registry.addFormatter(createUpdatePortionDtoFormatter);
        registry.addFormatter(orderStatusDtoFormatter);
    }
    
}
