package ru.bironix.super_food.dtos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.dish.DishCountDto;
import ru.bironix.super_food.dtos.interfaces.*;
import ru.bironix.super_food.dtos.interfaces.dish.CreateUpdateDishesCounts;
import ru.bironix.super_food.dtos.interfaces.dish.DishesCounts;
import ru.bironix.super_food.dtos.interfaces.person.Client;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;

import java.util.Date;
import java.util.List;


public abstract class OrderDto {


    @Schema(description = "Заказ. Создание, обновление", name = "OrderDto.CreateUpdate")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateUpdate implements DeliveryTime, Comment,
            WayToGet, TotalPrice, Address, CreateUpdateDishesCounts, PaymentMethod {

        @JsonFormat(pattern = "HH:mm")
        private Date deliveryTime;
        private WayToGetDto wayToGet;
        private Integer totalPrice;
        private List<DishCountDto.CreteUpdate> dishes;
        private AddressDto address;
        private String comment;
        private PaymentMethodDto paymentMethod;
    }

    public static class Base {
        @Schema(description = "Заказ. Базовая сжатая", name = "OrderDto.Base.Full")
        @Data
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Small implements Id, Created, Status,
                TotalPrice, DishesCounts, DeliveryTime, WayToGet {
            private Integer id;
            private Date created;
            private OrderStatusDto status;
            private Integer totalPrice;
            private List<DishCountDto.Base> dishes;
            @JsonFormat(pattern = "HH:mm")
            private Date deliveryTime;
            private WayToGetDto wayToGet;
        }

        @Schema(description = "Заказ. Базовая, полная", name = "OrderDto.Base.Full")
        @Data
        @EqualsAndHashCode(callSuper = true)
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Full extends Small implements
                Client, Address, Comment, PaymentMethod {
            private PersonDto.Base client;
            private AddressDto address;
            private String comment;
            private PaymentMethodDto paymentMethod;
        }
    }
}