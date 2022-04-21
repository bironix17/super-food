package ru.bironix.super_food.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(description = "Адрес")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto implements Serializable {
    Integer id;

    @NotBlank
    String address;
}
