package ru.bironix.super_food.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto implements Serializable {
    Integer id;

    @NotBlank
    String address;
}
