package ru.bironix.super_food.dtos.request.createOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "Адрес. Версия для указания в заказе")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDto implements Serializable {

    @Schema(description = "id адреса. Указывается если адрес ранее уже был создан", nullable = true)
    Integer id;

    @Schema(description = "Значение нового адреса. Указывается если нужно создать новый адрес", nullable = true)
    String address;
}
