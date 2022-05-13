package ru.bironix.super_food.dtos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.dish.DishCountDto;
import ru.bironix.super_food.dtos.interfaces.*;
import ru.bironix.super_food.dtos.interfaces.dish.DishesCountes;
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
    public static class CreateUpdate implements DeliveryTime,
            WayToGet, TotalPrice, Address, DishesCountes {

        @JsonFormat(pattern = "HH:mm")
        private Date deliveryTime;
        private WayToGetDto wayToGet;
        private Integer totalPrice;
        private List<DishCountDto> dishes;
        private AddressDto address;
    }

    public static class Base {
        @Schema(description = "Заказ. Базовая сжатая", name = "OrderDto.Base.Full")
        @Data
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Small implements Id, Created, Status,
                TotalPrice, DishesCountes {

            private Integer id;
            private Date created;
            private OrderStatusDto status;
            private Integer totalPrice;
            private List<DishCountDto> dishes;
        }

        @Schema(description = "Заказ. Базовая, полная", name = "OrderDto.Base.Full")
        @Data
        @EqualsAndHashCode(callSuper = true)
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Full extends Small implements DeliveryTime,
                WayToGet, Client, Address {

            @JsonFormat(pattern = "HH:mm")
            private Date deliveryTime;
            private WayToGetDto wayToGet;
            private PersonDto.Base client;
            private AddressDto address;
        }
    }
}